package co.udistrital.android.thomasmensageria.add_route;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import co.udistrital.android.thomasmensageria.add_route.events.AddRouteEvent;
import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.domain.api.ApiUpdateRouteLogImpl;
import co.udistrital.android.thomasmensageria.entities.Manifest;
import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;


import static co.udistrital.android.thomasmensageria.entities.Manifest.V_NOVEDAD;
import static co.udistrital.android.thomasmensageria.entities.Manifest.A_ADD;

import static co.udistrital.android.thomasmensageria.entities.Messenger.V_IDMENSAJERO;

import static co.udistrital.android.thomasmensageria.entities.Route.V_ESTADO;

import static co.udistrital.android.thomasmensageria.entities.Route.A_ACTIVO;
import static co.udistrital.android.thomasmensageria.entities.Route.A_ENTREGADO;
import static co.udistrital.android.thomasmensageria.entities.Route.A_NOENTREGADO;
import static co.udistrital.android.thomasmensageria.entities.Route.A_VALIDATE;

import static co.udistrital.android.thomasmensageria.entities.Route.V_VALIDADO;

import static co.udistrital.android.thomasmensageria.entities.Route.A_TRUE;
import static co.udistrital.android.thomasmensageria.entities.Route.A_FALSE;




public class AddRouteRepositoryImpl implements AddRouteRepository {


    private FirebaseHelper helper;
    private ChildEventListener routeEventListener;
    private EventBus eventBus;

    String codeMessenger;

    public AddRouteRepositoryImpl() {
        helper = FirebaseHelper.getInstance();
        eventBus = GreenRobotEventBus.getInstance();
        codeMessenger = helper.getAuthUserEmail().split("@")[0].toString();
    }

    @Override
    public void addRoute(final String idRoute, final String novedad, final String idAdmin) {

        final Query route = helper.getOneRouteReference(idRoute);

        route.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Route route = dataSnapshot.getValue(Route.class);
                if(route!=null) {
                    route.setId(dataSnapshot.getKey().toString());
                    if (route.getIdmensajero().equals(codeMessenger.toString()) && route.getEstado().toString().equals(A_ACTIVO)) {
                        if(route.getValidado())
                            postEvent(AddRouteEvent.onRouteAddInError, "Ruta ya se encuentra asignada");
                        else{
                            approveRoute(route.getId(),A_TRUE);
                            postEvent(AddRouteEvent.onRouteAddInSuccess);
                        }
                    }else {
                        if (!route.getEstado().toString().equals(A_ENTREGADO))
                            if (!route.getEstado().toString().equals(A_NOENTREGADO))
                                if (route.getId() == idRoute) {
                                    if (route.getAutorizacion().equals(idAdmin.toString()))
                                        updateRoute(idRoute, novedad);
                                    else postEvent(AddRouteEvent.onRouteAddInError, "Clave incorrecta");
                                } else postEvent(AddRouteEvent.onRouteAddInError, "Numero de guia no registrada");
                            else postEvent(AddRouteEvent.onRouteAddInError, "Imposible agregar ruta reportada como NO entregada");
                        else postEvent(AddRouteEvent.onRouteAddInError, "Imposible agregar ruta entregada al cliente");
                    }
                }else postEvent(AddRouteEvent.onRouteAddInError, "Numero de guia no existe");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                postEvent(AddRouteEvent.onRouteAddInError, databaseError.toString());
            }
        });
    }

    @Override
    public void approveRoute(String id_route, boolean approve) {
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put(V_VALIDADO, approve);
        updates.put(V_ESTADO, A_VALIDATE);
        updateRouteLog(id_route, A_VALIDATE, A_ACTIVO);
        helper.getOneRouteReference(id_route).updateChildren(updates);
    }

    public void updateRoute(String id_route, String novedad){

        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put(V_ESTADO, A_ACTIVO);
        updates.put(V_NOVEDAD, A_ADD+": "+novedad);
        updates.put(V_VALIDADO, A_FALSE);
        updates.put(V_IDMENSAJERO, codeMessenger);
        helper.getOneRouteReference(id_route).updateChildren(updates);
        updateRouteLog(id_route, A_ADD+": "+novedad, A_ACTIVO);
        postEvent(AddRouteEvent.onRouteAddInSuccess);
    }

    private void updateRouteLog(String idRoute, String novedad, String estado) {
        final String newPhotoId = helper.create();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Manifest manifest = new Manifest();
        manifest.setId(newPhotoId);
        manifest.setIdRuta(idRoute);
        manifest.setEstado(estado);
        manifest.setNovedad(novedad);
        manifest.setFecha(timeStamp);
        helper.update(manifest);

        ApiUpdateRouteLogImpl api = new ApiUpdateRouteLogImpl();
        if (novedad.equals(A_VALIDATE))
           api.updateRouteLog(timeStamp, idRoute, A_VALIDATE, novedad);
        else
            api.updateRouteLog(timeStamp, idRoute, A_ACTIVO, novedad);

    }

    private void postEvent(int type){
        postEvent(type, null);
    }

    private void postEvent(int type, String errorMessage){
        AddRouteEvent addRouteEvent = new AddRouteEvent();
        addRouteEvent.setEventType(type);
        if (errorMessage != null ){
            addRouteEvent.setErrorMessage(errorMessage);
        }
        eventBus.post(addRouteEvent);
    }

}
