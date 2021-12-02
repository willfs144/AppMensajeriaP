package co.udistrital.android.thomasmensageria.services_returned;

import org.greenrobot.eventbus.Subscribe;

import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.services_returned.events.ServiceReturnedEvent;
import co.udistrital.android.thomasmensageria.services_returned.ui.ServicesReturnedFragmentDialog;
import co.udistrital.android.thomasmensageria.services_returned.ui.ServicesReturnedView;

public class ServicesReturnedPresenterImpl implements ServicesReturnedPresenter{

    private ServicesReturnedView view;
    private EventBus eventBus;
    private ServicesReturnedInteractor interactor;


    public ServicesReturnedPresenterImpl(ServicesReturnedFragmentDialog servicesReturnedFragmentDialog) {
        this.view = servicesReturnedFragmentDialog;
        eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new ServicesReturnedInteractorImpl();
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
    public void updateRoute(String idRoute, String novedad) {
        if (view != null)
            interactor.updateRoute(idRoute,novedad);
    }

    @Override
    @Subscribe
    public void onEventMaininThread(ServiceReturnedEvent event) {
        if (event.getEventType()==ServiceReturnedEvent.onRouteDeleteInSuccess)
            view.showMsg();
    }
}
