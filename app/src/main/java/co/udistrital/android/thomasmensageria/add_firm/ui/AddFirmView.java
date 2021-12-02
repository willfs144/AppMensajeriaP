package co.udistrital.android.thomasmensageria.add_firm.ui;

import android.content.Context;


public interface AddFirmView {

    void onUploadInit();
    void onUploadCompled();
    void onUploadError(String error);

    void refresh();

    void showSnackbar(String error);

    void showBottoms();
    void hideBottoms();

    Context getContext();
}
