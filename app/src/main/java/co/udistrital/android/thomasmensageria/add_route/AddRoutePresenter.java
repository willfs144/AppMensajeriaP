package co.udistrital.android.thomasmensageria.add_route;

import co.udistrital.android.thomasmensageria.add_route.events.AddRouteEvent;


public interface AddRoutePresenter {
    void onShow();
    void onDestroy();

    void addRoute(String idRoute, String novedad, String idAdmin);
    void onEventMaininThread(AddRouteEvent event);


}
