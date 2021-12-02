package co.udistrital.android.thomasmensageria.add_photo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import co.udistrital.android.thomasmensageria.add_photo.events.AddPhotoEvent;
import co.udistrital.android.thomasmensageria.add_photo.ui.AddPhotoView;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;


public class AddPhotoPresenterImpl implements AddPhotoPresenter {

    private AddPhotoView view;
    private EventBus eventBus;
    private AddPhotoInteractor interactor;

    public AddPhotoPresenterImpl(AddPhotoView view) {
        this.view = view;
        eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new AddPhotoInteractorImpl();
    }


    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        //view = null;
         eventBus.unregister(this);
    }



    @Override
    public void uploadPhoto(String idRoute, String path) {
        interactor.execute(idRoute,path);
    }

    @Subscribe
    public void onEventMainThread(AddPhotoEvent event) {
        if (this.view != null){
            switch (event.getType()){
                case AddPhotoEvent.UPLOAD_INIT:
                    view.onUploadInit();
                    break;
                case AddPhotoEvent.UPLOAD_COMPLETE:
                    view.onUploadCompled();
                    break;
                case AddPhotoEvent.UPLOAD_ERROR:
                    view.onUploadError(event.getError());
                    break;
            }
        }
    }

}
