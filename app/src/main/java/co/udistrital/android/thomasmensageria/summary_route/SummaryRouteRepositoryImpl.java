package co.udistrital.android.thomasmensageria.summary_route;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.summary_route.events.SummaryRouteEvent;


public class SummaryRouteRepositoryImpl implements SummaryRouteRepository {

    private FirebaseHelper firebaseAPI;
    private EventBus eventBus;

    public SummaryRouteRepositoryImpl() {
        firebaseAPI = FirebaseHelper.getInstance();
        eventBus = GreenRobotEventBus.getInstance();

    }

    @Override
    public void consultRouts() {
        firebaseAPI.getRouteReferents().addListenerForSingleValueEvent(new ValueEventListener() {
            List<Route> routeList = new ArrayList<Route>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Route route = child.getValue(Route.class);
                    route.setId(child.getKey());
                    routeList.add(route);
                }

                post(null, routeList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                post(databaseError.toString(),null);
            }
        });
    }

    @Override
    public void updateLoad() {
        String cedula = firebaseAPI.getAuthUserEmail().split("@")[0].toString();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("load", false);
        updates.put("fecha",timeStamp);
        firebaseAPI.updateCargueInformation(cedula, updates);
        remoteRouts();
    }

    public void remoteRouts() {
        firebaseAPI.getRouteReferents().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    firebaseAPI.deleteRoutesMessager(child.getKey());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                post(databaseError.toString(),null);
            }
        });
    }



    private void post(String error, List<Route> routeList) {
        SummaryRouteEvent event = new  SummaryRouteEvent();
        event.setErrorMessage(error);
        event.setRouteList(routeList);
        eventBus.post(event);
    }
}
