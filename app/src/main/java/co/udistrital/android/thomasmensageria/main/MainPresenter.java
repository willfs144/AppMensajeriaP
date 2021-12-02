package co.udistrital.android.thomasmensageria.main;

import co.udistrital.android.thomasmensageria.main.events.MainEvent;


public interface MainPresenter {

    void onCreate();
    void onDestroy();
    void signOff();
    void updateProfileShow();
    void onEventMainThread(MainEvent event);
}
