package co.udistrital.android.thomasmensageria.description_route;

import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import co.udistrital.android.thomasmensageria.description_route.events.DetailRouteEvent;
import co.udistrital.android.thomasmensageria.description_route.ui.DescriptionRouteView;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;

public class DescriptionRoutePresenterImpl  implements DescriptionRoutePresenter{

    private DescriptionRouteView view;
    private EventBus eventBus;
    public DescriptionRouteInteractor interactor;

    public DescriptionRoutePresenterImpl(DescriptionRouteView view){
        this. view = view;
        eventBus = GreenRobotEventBus.getInstance();
        interactor = new DescriptionRouteInterartorImpl();
    }

    public DescriptionRoutePresenterImpl(DescriptionRouteView view, EventBus eventBus, DescriptionRouteInteractor interactor) {
        this.view = view;
        this.eventBus = eventBus;
        this.interactor = interactor;
    }

    @Override
    public void onResume() {
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void getDeltailRoute(int idproducto) {
        if (view != null){
            view.hideElements();
            view.showProgressBar();
        }
        this.interactor.execute(idproducto);
    }



    @Override
    @Subscribe
    public void onEventMainThread(DetailRouteEvent event) {
        String errorMsg = event.getError();
        if (view != null){
            view.showElements();
            view.hideProgressBar();
        }
        if (errorMsg != null){
            view.onError(event.getError());
        }else{
            view.setContentProduct(event.getProduct());
            view.setContentClient(event.getClient());
        }

    }
}
