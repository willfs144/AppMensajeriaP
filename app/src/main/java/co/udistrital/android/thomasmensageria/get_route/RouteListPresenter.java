package co.udistrital.android.thomasmensageria.get_route;

import android.content.Intent;

import com.google.zxing.integration.android.IntentResult;

import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.get_route.events.RouteListEvent;


public interface RouteListPresenter {


    void onCreate();
    void onDestroy();
    void onPause();
    void onResume();

    void removeRoute(String idRoute);
    void onEventMainThread(RouteListEvent event);


    void validateGuia(String id_route, IntentResult scanResult);
}
