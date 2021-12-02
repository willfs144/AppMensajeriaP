package co.udistrital.android.thomasmensageria.show_photo;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import co.udistrital.android.thomasmensageria.domain.FirebaseActionListenerCallback;
import co.udistrital.android.thomasmensageria.domain.FirebaseEventListenerCallback;
import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.entities.Photo;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.show_photo.events.ShowPhotoEvent;


public class ShowPhotoRepositoryImpl implements ShowPhotoRepository{


    private FirebaseHelper firebaseAPI;
    private EventBus eventBus;



    public ShowPhotoRepositoryImpl() {
        firebaseAPI = FirebaseHelper.getInstance();
        eventBus = GreenRobotEventBus.getInstance();
    }


    @Override
    public void subscribe(String idRoute) {
        firebaseAPI.checkForData(new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {

            }
            @Override
            public void onError(DatabaseError error) {
                if (error != null){
                    post(ShowPhotoEvent.READ_EVENT, error.getMessage());
                }else{
                    post(ShowPhotoEvent.READ_EVENT, "");
                }
            }
        });

        update(idRoute);

    }

    public void update(String idRoute){
        firebaseAPI.subscribe(new FirebaseEventListenerCallback() {
            @Override
            public void onChildAdded(DataSnapshot snapshot) {
                Photo photo = snapshot.getValue(Photo.class);
                photo.setId(snapshot.getKey());
                post(ShowPhotoEvent.READ_EVENT, photo);
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot) {
                Photo photo = snapshot.getValue(Photo.class);
                photo.setId(snapshot.getKey());
                post(ShowPhotoEvent.READ_EVENT, photo);
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                Photo photo = snapshot.getValue(Photo.class);
                photo.setId(snapshot.getKey());
                post(ShowPhotoEvent.DELETE_EVENT, photo);
            }



            @Override
            public void onCancelled(DatabaseError error) {
                post(ShowPhotoEvent.READ_EVENT, error.getMessage());
            }
        }, idRoute);
    }

    @Override
    public void unsubscribe() {
        firebaseAPI.unsubscribe();
    }

    @Override
    public void removePhoto(final Photo photo) {
        firebaseAPI.remove(photo, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                post(ShowPhotoEvent.DELETE_EVENT, photo);
            }

            @Override
            public void onError(DatabaseError error) {
                post(ShowPhotoEvent.DELETE_EVENT, error.getMessage());
            }
        });

    }


    private void post(int type, Photo photo) {
        post(type, null, photo);

    }

    private void post(int type, String error) {
        post(type, error, null);
    }

    private void post(int type, String error, Photo photo) {

        ShowPhotoEvent event = new ShowPhotoEvent();
        event.setType(type);
        event.setError(error);
        event.setPhoto(photo);
        eventBus.post(event);
    }
}
