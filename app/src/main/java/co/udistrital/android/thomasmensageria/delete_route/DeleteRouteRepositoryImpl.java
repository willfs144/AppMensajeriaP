package co.udistrital.android.thomasmensageria.delete_route;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import co.udistrital.android.thomasmensageria.delete_route.events.DeleteRouteEvent;
import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.domain.api.ApiUpdateRouteLogImpl;
import co.udistrital.android.thomasmensageria.entities.Manifest;
import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;

import static co.udistrital.android.thomasmensageria.entities.Route.V_ESTADO;
import static co.udistrital.android.thomasmensageria.entities.Route.A_NOENTREGADO;
import static co.udistrital.android.thomasmensageria.entities.Route.A_INACTIVO;

import static co.udistrital.android.thomasmensageria.entities.Manifest.V_NOVEDAD;
import static co.udistrital.android.thomasmensageria.entities.Manifest.A_DELETE;



public class DeleteRouteRepositoryImpl implements DeleteRouteRepository{



    private FirebaseHelper helper;
    private EventBus eventBus;

    public DeleteRouteRepositoryImpl() {
        helper = FirebaseHelper.getInstance();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void removeRoute(String id_route, String novedad) {

        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put(V_ESTADO, A_INACTIVO);
        updates.put(V_NOVEDAD, A_DELETE+": "+novedad);
        helper.getOneRouteReference(id_route).updateChildren(updates);
        updateRouteLog(id_route, A_DELETE+": "+novedad, A_INACTIVO);
        post(DeleteRouteEvent.onRouteDeleteInSuccess);
    }

    private void updateRouteLog(String idRoute, String novedad, String estado) {
        final String newPhotoId = helper.create();
        String timeStamp = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(new Date());
        Manifest manifest = new Manifest();
        manifest.setId(newPhotoId);
        manifest.setIdRuta(idRoute);
        manifest.setEstado(estado);
        manifest.setNovedad(novedad);
        manifest.setFecha(timeStamp);
        helper.update(manifest);

        ApiUpdateRouteLogImpl api = new ApiUpdateRouteLogImpl();
        api.updateRouteLog(timeStamp, idRoute, A_NOENTREGADO, novedad);
    }


    private void post(int type) {
        DeleteRouteEvent event = new DeleteRouteEvent();
        event.setEventType(type);
        eventBus.post(event);
    }

}
