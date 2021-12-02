package co.udistrital.android.thomasmensageria.add_photo;

import android.content.Context;


import java.io.File;

import co.udistrital.android.thomasmensageria.ThomasMensageriaApplication;
import co.udistrital.android.thomasmensageria.add_photo.events.AddPhotoEvent;
import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.entities.Photo;
import co.udistrital.android.thomasmensageria.lib.CloudinaryImageStorage;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.lib.base.ImageStorage;
import co.udistrital.android.thomasmensageria.lib.base.ImageStorageFinishedListener;


public class AddPhotoRepositoryImpl implements AddPhotoRepository{

    private FirebaseHelper firebaseAPI;
    private EventBus eventBus;
    private ImageStorage imageStorage;
    private Context context;


    public AddPhotoRepositoryImpl() {
        firebaseAPI = FirebaseHelper.getInstance();
        eventBus = GreenRobotEventBus.getInstance();
        context = ThomasMensageriaApplication.getAppContext();
        imageStorage = new CloudinaryImageStorage(context);
    }

    @Override
    public void uploadPhoto(String idRoute, String path) {

        final String newPhotoId = firebaseAPI.create();
        final Photo photo = new Photo();
        photo.setId(newPhotoId);
        photo.setGuia(idRoute);

        post(AddPhotoEvent.UPLOAD_INIT);
        ImageStorageFinishedListener listener = new ImageStorageFinishedListener() {
            @Override
            public void onSuccess() {
                String url = imageStorage.getImageUrl(newPhotoId);
                photo.setUrl(url);
                firebaseAPI.update(photo);
                post(AddPhotoEvent.UPLOAD_COMPLETE);

            }

            @Override
            public void onError(String error) {
                post(AddPhotoEvent.UPLOAD_ERROR, error);
            }
        };
        imageStorage.upload(new File(path), newPhotoId, listener);
    }


    private void post(int type) {
        post(type, null);
    }

    private void post(int type, String error) {
        AddPhotoEvent event = new AddPhotoEvent();
        event.setError(error);
        event.setType(type);
        eventBus.post(event);
    }
}
