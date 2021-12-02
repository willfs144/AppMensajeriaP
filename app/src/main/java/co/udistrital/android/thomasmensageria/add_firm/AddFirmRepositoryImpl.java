package co.udistrital.android.thomasmensageria.add_firm;

import android.content.Context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import co.udistrital.android.thomasmensageria.add_firm.events.AddFirmEvent;
import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.entities.Photo;
import co.udistrital.android.thomasmensageria.lib.CloudinaryImageStorage;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.lib.base.ImageStorage;
import co.udistrital.android.thomasmensageria.lib.base.ImageStorageFinishedListener;

public class AddFirmRepositoryImpl implements AddFirmRepository{

    private FirebaseHelper firebaseAPI;
    private EventBus eventBus;
    private ImageStorage imageStorage;
    private final static String V_URL_FIRM = "url_firm";


    public AddFirmRepositoryImpl(Context context) {
        firebaseAPI = FirebaseHelper.getInstance();
        eventBus = GreenRobotEventBus.getInstance();
        imageStorage = new CloudinaryImageStorage(context);
    }

    @Override
    public void uploadPhoto(final String id_route, String path) {

        post(AddFirmEvent.UPLOAD_INIT);
        ImageStorageFinishedListener listener = new ImageStorageFinishedListener() {
            @Override
            public void onSuccess() {
                String url = imageStorage.getImageUrl(id_route);
                Map<String, Object> updates = new HashMap<String, Object>();
                updates.put(V_URL_FIRM, url);
                firebaseAPI.getOneRouteReference(id_route).updateChildren(updates);
                post(AddFirmEvent.UPLOAD_COMPLETE);
            }

            @Override
            public void onError(String error) {
                post(AddFirmEvent.UPLOAD_ERROR, error);
            }
        };
        imageStorage.upload(new File(path), id_route, listener);

    }


    private void post(int type) {
        post(type, null);
    }

    private void post(int type, String error) {
        AddFirmEvent event = new AddFirmEvent();
        event.setError(error);
        event.setType(type);
        eventBus.post(event);
    }
}
