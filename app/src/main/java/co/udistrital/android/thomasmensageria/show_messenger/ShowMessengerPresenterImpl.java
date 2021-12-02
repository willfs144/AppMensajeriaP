package co.udistrital.android.thomasmensageria.show_messenger;

import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.show_messenger.events.ShowMessengerEvent;
import co.udistrital.android.thomasmensageria.show_messenger.ui.ShowMessenger;


public class ShowMessengerPresenterImpl implements ShowMessengerPresenter {

    private ShowMessengerInteractor interactor;
    private ShowMessenger showMessengerView;
    private EventBus eventBus;

    public ShowMessengerPresenterImpl(ShowMessenger showMessengerView) {
        this.showMessengerView = showMessengerView;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new ShowMessengerInteractorImpl();
    }


    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void ProfileShow(String idmensajero) {
        interactor.execute(idmensajero);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ShowMessengerEvent event) {
        String error = event.getError();
        if (error != null){
            if (error.isEmpty()){
                showMessengerView.onGetUserError("Usuario no Existe");
            } else {
                showMessengerView.onGetUserError(error);
            }
        }else {
            Log.e("ShowPhoto:  ",event.getUser().getNombre().toString());
            showMessengerView.setUser(event.getUser());
        }
    }
}
