package co.udistrital.android.thomasmensageria.show_messenger.ui;

import co.udistrital.android.thomasmensageria.entities.Messenger;


public interface ShowMessengerView {
    void setUser(Messenger messenger);
    void onGetUserError(String error);
}
