package co.udistrital.android.thomasmensageria.services_returned;

import com.google.firebase.database.ChildEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.domain.api.ApiUpdateRouteLogImpl;
import co.udistrital.android.thomasmensageria.entities.Manifest;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.services_returned.events.ServiceReturnedEvent;

import static co.udistrital.android.thomasmensageria.entities.Route.V_ESTADO;
import static co.udistrital.android.thomasmensageria.entities.Route.A_NOENTREGADO;

import static co.udistrital.android.thomasmensageria.entities.Manifest.V_NOVEDAD;

public class ServicesReturnedRepositoryImpl implements ServicesReturnedRepository{
    private FirebaseHelper helper;
    private ChildEventListener routeEventListener;
    private EventBus eventBus;


    public ServicesReturnedRepositoryImpl() {
        helper = FirebaseHelper.getInstance();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void updateRoute(String idRoute, String novedad) {
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put(V_ESTADO, A_NOENTREGADO);
        updates.put(V_NOVEDAD, novedad);
        helper.getOneRouteReference(idRoute).updateChildren(updates);
        updateRouteLog(idRoute, novedad);
        post(ServiceReturnedEvent.onRouteDeleteInSuccess);
    }

    private void updateRouteLog(String idRoute, String novedad) {
        final String newPhotoId = helper.create();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Manifest manifest = new Manifest();
        manifest.setId(newPhotoId);
        manifest.setIdRuta(idRoute);
        manifest.setEstado(A_NOENTREGADO);
        manifest.setNovedad(novedad);
        manifest.setFecha(timeStamp);
        helper.update(manifest);

        ApiUpdateRouteLogImpl api = new ApiUpdateRouteLogImpl();
        api.updateRouteLog(timeStamp, idRoute, A_NOENTREGADO, novedad);
    }

    private void post(int type) {
        ServiceReturnedEvent event = new ServiceReturnedEvent();
        event.setEventType(type);
        eventBus.post(event);
    }
}
