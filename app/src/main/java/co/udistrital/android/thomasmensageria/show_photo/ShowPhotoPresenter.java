package co.udistrital.android.thomasmensageria.show_photo;

import co.udistrital.android.thomasmensageria.entities.Photo;
import co.udistrital.android.thomasmensageria.show_photo.events.ShowPhotoEvent;


public interface ShowPhotoPresenter {

    void onCreate();
    void OnDestroy();

    void subscribe(String id);
    void unsubscribe();

    void removePhoto(Photo photo);
    void onEvent(ShowPhotoEvent event);

}
