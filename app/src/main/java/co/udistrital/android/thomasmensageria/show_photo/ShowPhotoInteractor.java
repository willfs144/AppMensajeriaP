package co.udistrital.android.thomasmensageria.show_photo;

import co.udistrital.android.thomasmensageria.entities.Photo;


public interface ShowPhotoInteractor {

    void subscribe(String idRoute);
    void unsubscribe();

    void removePhoto(Photo photo);
}
