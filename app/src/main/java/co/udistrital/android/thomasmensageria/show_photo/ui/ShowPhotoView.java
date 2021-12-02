package co.udistrital.android.thomasmensageria.show_photo.ui;

import co.udistrital.android.thomasmensageria.entities.Photo;


public interface ShowPhotoView {
    void showList();
    void hideList();
    void showProgress();
    void hideProgress();

    void addPhoto(Photo photo);
    void removePhoto(Photo photo);
    void onPhotosError(String error);



}
