package co.udistrital.android.thomasmensageria.add_route;

import org.greenrobot.eventbus.Subscribe;

import co.udistrital.android.thomasmensageria.add_route.events.AddRouteEvent;
import co.udistrital.android.thomasmensageria.add_route.ui.AddRouteFragmentDialog;
import co.udistrital.android.thomasmensageria.add_route.ui.AddRouteView;
import co.udistrital.android.thomasmensageria.delete_route.ui.DeleteRouteFragmentDialog;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;


public class AddRoutePresenterImpl implements AddRoutePresenter {

    private AddRouteView view;
    private EventBus eventBus;
    private AddRouteInteractor interactor;

    public AddRoutePresenterImpl(AddRouteView addRouteView) {
        view = addRouteView;
        eventBus = GreenRobotEventBus.getInstance();
        interactor = new AddRouteInteractorImpl();
    }



    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void addRoute(String idRoute, String novedad, String idAdmin) {
        interactor.execute(idRoute,novedad,idAdmin);
    }

    @Override
    @Subscribe
    public void onEventMaininThread(AddRouteEvent event) {
        switch (event.getEventType()){
            case AddRouteEvent.onRouteAddInSuccess:
                view.showMsg("Ruta agregada exitosamente");
                view.closeDialog();
                break;
            case AddRouteEvent.onRouteAddInError:
                view.showMsg(event.getErrorMessage());
                break;
        }
    }
}
