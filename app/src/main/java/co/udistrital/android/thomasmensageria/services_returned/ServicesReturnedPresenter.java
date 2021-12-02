package co.udistrital.android.thomasmensageria.services_returned;

import co.udistrital.android.thomasmensageria.services_returned.events.ServiceReturnedEvent;


public interface ServicesReturnedPresenter {

    void onCreate();
    void onDestroy();

    void updateRoute(String idRoute, String observacion);
    void onEventMaininThread(ServiceReturnedEvent event);
}
