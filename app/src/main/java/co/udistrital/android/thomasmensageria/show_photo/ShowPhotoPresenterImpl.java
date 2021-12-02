package co.udistrital.android.thomasmensageria.show_photo;

import org.greenrobot.eventbus.Subscribe;

import co.udistrital.android.thomasmensageria.entities.Photo;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.show_photo.events.ShowPhotoEvent;
import co.udistrital.android.thomasmensageria.show_photo.ui.ShowPhoto;


public class ShowPhotoPresenterImpl implements ShowPhotoPresenter{


    public static final String EMPTY_LIST = "Listado";
    private EventBus eventBus;
    private ShowPhoto view;
    private ShowPhotoInteractor interactor;

    public ShowPhotoPresenterImpl(ShowPhoto showPhoto) {
        view= showPhoto;
        eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new ShowPhotoInteractorImpl();
    }


    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void OnDestroy() {
        this.view = null;
        eventBus.unregister(this);

    }

    @Override
    public void subscribe(String idRoute) {
        if (view != null){
            view.hideList();
            view.showProgress();
        }
        interactor.subscribe(idRoute);

    }

    @Override
    public void unsubscribe() {
        interactor.unsubscribe();
    }

    @Override
    public void removePhoto(Photo photo) {
        interactor.removePhoto(photo);
    }

    @Override
    @Subscribe
    public void onEvent(ShowPhotoEvent event) {

        if (view != null){
            view.hideProgress();
            view.showList();
        }
        String error = event.getError();
        if (error != null){
            if (error.isEmpty()){

            } else {
                view.onPhotosError(error);
            }
        }else {
            if (event.getType() == ShowPhotoEvent.READ_EVENT){
                view.addPhoto(event.getPhoto());
            }
            else if (event.getType() == ShowPhotoEvent.DELETE_EVENT){
                view.removePhoto(event.getPhoto());
            }

        }
    }
}
