package co.udistrital.android.thomasmensageria.add_photo;

import co.udistrital.android.thomasmensageria.add_photo.events.AddPhotoEvent;


public interface AddPhotoPresenter {
    void onCreate();
    void onDestroy();

    void uploadPhoto(String idRoute, String path);
    void onEventMainThread(AddPhotoEvent event);
}
