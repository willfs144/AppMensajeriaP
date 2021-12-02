package co.udistrital.android.thomasmensageria.show_messenger.ui;

import co.udistrital.android.thomasmensageria.entities.Messenger;
import co.udistrital.android.thomasmensageria.lib.ImageLoader;
import co.udistrital.android.thomasmensageria.show_messenger.ShowMessengerPresenter;
import co.udistrital.android.thomasmensageria.show_messenger.ShowMessengerPresenterImpl;


public class ShowMessenger implements ShowMessengerView{


    private Messenger messenger;
    private ImageLoader imageLoader;
    private String error;


    private ShowMessengerPresenter presenter;

    public ShowMessenger(String idmensajero) {
        this.presenter = new ShowMessengerPresenterImpl(this);
        presenter.onCreate();
        presenter.ProfileShow(idmensajero);
    }


    @Override
    public void setUser(Messenger messenger) {
        this.messenger= messenger;
    }

    @Override
    public void onGetUserError(String error) {
        this.error= error;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public String getError() {
        return error;
    }


}
