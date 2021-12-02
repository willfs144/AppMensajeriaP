package co.udistrital.android.thomasmensageria.add_firm;

import co.udistrital.android.thomasmensageria.add_firm.events.AddFirmEvent;

public interface AddFirmPresenter {

    void onCreate();
    void onDestroy();

    void uploadPhoto(String idRoute, String path);
    void onEventMainThread(AddFirmEvent event);


}
