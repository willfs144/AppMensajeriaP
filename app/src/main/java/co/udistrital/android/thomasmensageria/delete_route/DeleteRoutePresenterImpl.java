package co.udistrital.android.thomasmensageria.delete_route;

import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import co.udistrital.android.thomasmensageria.delete_route.events.DeleteRouteEvent;
import co.udistrital.android.thomasmensageria.delete_route.ui.DeleteRouteView;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;



public class DeleteRoutePresenterImpl implements DeleteRoutePresenter {

    private DeleteRouteView view;
    private EventBus eventBus;
    private DeleteRouteInteractor interactor;

    public DeleteRoutePresenterImpl(DeleteRouteView routeView) {
        view = routeView;
        eventBus = GreenRobotEventBus.getInstance();
        interactor = new DeleteRouteInteractorImpl();
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
    public void deleteRoute(String idRoute, String observacion) {
        interactor.removeRoute(idRoute, observacion);

    }

    @Override
    @Subscribe
    public void onEventMaininThread(DeleteRouteEvent event) {
        if (event.getEventType()==DeleteRouteEvent.onRouteDeleteInSuccess)
            view.showMsg();
    }


}
