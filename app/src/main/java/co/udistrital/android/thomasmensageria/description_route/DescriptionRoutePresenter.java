package co.udistrital.android.thomasmensageria.description_route;

import co.udistrital.android.thomasmensageria.description_route.events.DetailRouteEvent;


public interface DescriptionRoutePresenter {
    void onResume();
    void onPause();
    void onDestroy();
    void getDeltailRoute(int idproducto);
    void onEventMainThread(DetailRouteEvent event);

}
