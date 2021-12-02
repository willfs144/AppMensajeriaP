package co.udistrital.android.thomasmensageria.show_messenger;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.entities.Messenger;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.show_messenger.events.ShowMessengerEvent;


public class ShowMessengerRepositoryImpl implements ShowMessengerRepository{


    private FirebaseHelper firebaseAPI;
    private EventBus eventBus;



    public ShowMessengerRepositoryImpl() {
        firebaseAPI = FirebaseHelper.getInstance();
        eventBus = GreenRobotEventBus.getInstance();
    }

    public void updateProfileShow(String idmensajero) {
        DatabaseReference profile =firebaseAPI.getUserReferents(idmensajero);

        profile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Messenger messenger = dataSnapshot.getValue(Messenger.class);
                postEvent(messenger);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                postEvent(databaseError.toString());
            }
        });

    }

    private void postEvent(String error){
        postEvent(null, error);
    }

    private void postEvent(Messenger user){
        postEvent(user, null);
    }

    private void postEvent(Messenger user, String error){
        ShowMessengerEvent event = new  ShowMessengerEvent();
        event.setError(error);
        event.setUser(user);
        eventBus.post(event);
    }


}
