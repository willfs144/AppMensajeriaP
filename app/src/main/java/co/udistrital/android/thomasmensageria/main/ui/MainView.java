package co.udistrital.android.thomasmensageria.main.ui;

import co.udistrital.android.thomasmensageria.entities.Messenger;



public interface MainView {


    void setUser(Messenger messenger);
    void onGetUserError(String error);

}
