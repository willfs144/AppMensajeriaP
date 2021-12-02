package co.udistrital.android.thomasmensageria.get_route;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.domain.api.ApiUpdateRouteLogImpl;
import co.udistrital.android.thomasmensageria.entities.Manifest;
import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.get_route.events.RouteListEvent;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;

import static co.udistrital.android.thomasmensageria.entities.Route.V_VALIDADO;

import static co.udistrital.android.thomasmensageria.entities.Route.V_ESTADO;
import static co.udistrital.android.thomasmensageria.entities.Route.A_VALIDATE;
import static co.udistrital.android.thomasmensageria.entities.Route.A_ACTIVO;


public class RouteListRepositoryImpl implements RouteListRepository {
    private FirebaseHelper helper;
    private ChildEventListener routeEventListener;
    private EventBus eventBus;

    public RouteListRepositoryImpl() {
        helper = FirebaseHelper.getInstance();
        eventBus = GreenRobotEventBus.getInstance();
    }



    @Override
    public void approveRoute(String id_route, boolean approve) {
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put(V_VALIDADO, approve);
        updates.put(V_ESTADO, A_VALIDATE);
        updateRouteLog(id_route, A_VALIDATE, A_ACTIVO);
        helper.getOneRouteReference(id_route).updateChildren(updates);
        helper.getOneRouteReference(id_route).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                handleRoute(dataSnapshot, RouteListEvent.onRouteChanged);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        api.updateRouteLog(timeStamp, idRoute, A_VALIDATE, novedad);
    }


    @Override
    public void removeRoute(String idRoute) {
        helper.getOneRouteReference(idRoute).removeValue();
        helper.getRouteReferents().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                handleRoute(dataSnapshot,RouteListEvent.onRouteRemoved);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void destroyListener() {
        routeEventListener = null;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void unSubscribeToRouteListEvents() {
        try {
            if(routeEventListener != null) {
                helper.getRouteReferents().removeEventListener(routeEventListener);
            }
        }catch (Exception e){
         Log.e("Error RouteListRepositoryImpl: ", e.getMessage());
        }


    }

    @Override
    public void subscribeToRouteListEvents() {

        if (routeEventListener == null){
            routeEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    handleRoute(dataSnapshot, RouteListEvent.onRouteAdded);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    handleRoute(dataSnapshot, RouteListEvent.onRouteChanged);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    handleRoute(dataSnapshot,RouteListEvent.onRouteRemoved);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
        }

        helper.getRouteReferents().addChildEventListener(routeEventListener);

    }

    private void handleRoute(DataSnapshot dataSnapshot, int type) {
        Route route = dataSnapshot.getValue(Route.class);
        route.setId(dataSnapshot.getKey());
        post(type, route);
    }

    private void post(int type, Route route) {
            RouteListEvent event = new RouteListEvent();
            event.setEventType(type);
            event.setRoute(route);
            eventBus.post(event);
    }
}
