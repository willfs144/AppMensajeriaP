package co.udistrital.android.thomasmensageria.domain;

import com.google.firebase.database.DatabaseError;


public interface FirebaseActionListenerCallback {
    void onSuccess();
    void onError(DatabaseError error);
}
