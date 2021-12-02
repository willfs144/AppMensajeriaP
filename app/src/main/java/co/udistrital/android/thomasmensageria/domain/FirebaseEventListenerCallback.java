package co.udistrital.android.thomasmensageria.domain;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


public interface FirebaseEventListenerCallback {
    void onChildAdded(DataSnapshot snapshot);
    void onChildChanged(DataSnapshot snapshot);
    void onChildRemoved(DataSnapshot snapshot);
    void onCancelled(DatabaseError error);
}
