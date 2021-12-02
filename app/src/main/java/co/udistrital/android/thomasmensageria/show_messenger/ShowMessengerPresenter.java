package co.udistrital.android.thomasmensageria.show_messenger;

import co.udistrital.android.thomasmensageria.show_messenger.events.ShowMessengerEvent;


public interface ShowMessengerPresenter {

    void onCreate();
    void onDestroy();
    void ProfileShow(String idmensajero);
    void onEventMainThread(ShowMessengerEvent event);
}
