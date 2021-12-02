package co.udistrital.android.thomasmensageria.summary_route;

import co.udistrital.android.thomasmensageria.summary_route.events.SummaryRouteEvent;


public interface SummaryRoutePresenter {

    void onCreate();
    void onDestroy();

    void consultRoutes();

    void onEventMainThread(SummaryRouteEvent event);

    void closeRoutes();
}
