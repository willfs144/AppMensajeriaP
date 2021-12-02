package co.udistrital.android.thomasmensageria.services_route;

import co.udistrital.android.thomasmensageria.services_route.events.ServicesRouteEvent;


public interface ServicesRoutePresenter {

    void onCreate();
    void onDestroy();
    void findRoute(String id);

    void onEventMainThread(ServicesRouteEvent event);

}
