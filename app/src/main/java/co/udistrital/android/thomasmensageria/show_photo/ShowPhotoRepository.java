package co.udistrital.android.thomasmensageria.show_photo;

import co.udistrital.android.thomasmensageria.entities.Photo;


public interface ShowPhotoRepository {

    void subscribe(String idRoute);
    void unsubscribe();

    void removePhoto(Photo photo);
}
