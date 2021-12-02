package co.udistrital.android.thomasmensageria.add_firm;

import org.greenrobot.eventbus.Subscribe;

import co.udistrital.android.thomasmensageria.add_firm.events.AddFirmEvent;
import co.udistrital.android.thomasmensageria.add_firm.ui.AddFirmView;
import co.udistrital.android.thomasmensageria.add_route.AddRouteInteractor;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;


public class AddFirmPresenterImpl implements AddFirmPresenter {

    private AddFirmView view;
    private EventBus eventBus;
    private AddFirmInteractor interactor;


    public AddFirmPresenterImpl(AddFirmFragment addFirmFragment) {
        this.view = addFirmFragment;
        eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new AddFirmInteractorImpl(view.getContext());
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
    public void uploadPhoto(String idRoute, String path) {
        interactor.execute(idRoute,path);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddFirmEvent event) {
        if (this.view != null) {
            switch (event.getType()) {
                case AddFirmEvent.UPLOAD_INIT:
                    view.onUploadInit();
                    break;
                case AddFirmEvent.UPLOAD_COMPLETE:
                    view.onUploadCompled();
                    break;
                case AddFirmEvent.UPLOAD_ERROR:
                    view.onUploadError(event.getError());
                    break;
            }
        }
    }
}
