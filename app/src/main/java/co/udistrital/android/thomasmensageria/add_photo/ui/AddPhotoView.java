package co.udistrital.android.thomasmensageria.add_photo.ui;

import android.content.Context;


public interface AddPhotoView {
    void onUploadInit();
    void onUploadCompled();
    void onUploadError(String error);
    Context getContext();

    void refresh();

    void showSnackbar(String error);

    void showBottoms();
    void hideBottoms();

    

}
