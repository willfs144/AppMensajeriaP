package co.udistrital.android.thomasmensageria.services_route;

import org.greenrobot.eventbus.Subscribe;

import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.services_route.events.ServicesRouteEvent;
import co.udistrital.android.thomasmensageria.services_route.ui.ServicesRouteFragment;


public class ServicesRoutePresenterImpl  implements ServicesRoutePresenter{

    private ServicesRouteFragment view;
    private ServicesRouteInteractor interactor;
    private EventBus eventBus;


    public ServicesRoutePresenterImpl(ServicesRouteFragment view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this. interactor = new ServicesRouteInteractorImpl();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }



    @Override
    public void findRoute(String id) {
        if (view != null){
            view.hideForm();
            view.showProgress();
        }
        interactor.execute(id);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ServicesRouteEvent event) {
        String error = event.getError();

        if (this.view != null){
            view.hideProgress();
            view.hideIconSearch();
            view.hideIconBar();
            view.showIconClear();
        }

        if (error != null){
            view.onError(error);
        }
        else{
            view.setContentClient(event.getClient());
            view.setContentRoute(event.getRoute());
            view.showForm();
        }

    }
}
