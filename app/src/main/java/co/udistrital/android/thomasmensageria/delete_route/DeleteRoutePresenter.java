package co.udistrital.android.thomasmensageria.delete_route;

import co.udistrital.android.thomasmensageria.delete_route.events.DeleteRouteEvent;



public interface DeleteRoutePresenter {

    void onCreate();
    void onDestroy();

    void deleteRoute(String idRoute, String observacion);
    void onEventMaininThread(DeleteRouteEvent event);


}
