package co.udistrital.android.thomasmensageria.domain;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Manifest;
import co.udistrital.android.thomasmensageria.entities.Messenger;
import co.udistrital.android.thomasmensageria.entities.Photo;
import co.udistrital.android.thomasmensageria.entities.Product;
import co.udistrital.android.thomasmensageria.entities.Route;


public class FirebaseHelper {



    private DatabaseReference dataReference;
    private ChildEventListener photosEventListener;
    private final static String PHOTO_PATH = "photo";
    private final static String MANIFEST_PATH = "manifest";
    private final static String INFORMATION_PATH = "information";
    private final static String PRODUCT_PATH = "product";
    private final static String ROUTE_PATH = "routes";
    private final static String MESSENGER_PATH = "messengers";
    private final static String CLIENT_PATH = "clients";

    private final static String TITLE_CEDULA_PATH = "cedula";
    private final static String TITLE_IDGUIA_PATH = "id_guia";
    private final static String TITLE_IDACTIVO_PATH = "activo";
    private final static String TITLE_ID_MESSENGER_PATH = "idmensajero";
    private final static String TITLE_GUIA_PATH = "guia";

    private final static boolean ACTIVO = true;

    private final static String FIREBASE_URL = "https://mensajeria-20caa.firebaseio.com";


    private static class SingletonHolper {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance(){
        return SingletonHolper.INSTANCE;
    }

    public FirebaseHelper() {
        dataReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }


    public String getAuthUserEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;
        if (user != null){
            email = user.getEmail();
        }
        return email;
    }



    public void signOff(){
        FirebaseAuth.getInstance().signOut();
    }

    public DatabaseReference getUserReferents(String code_user){
        DatabaseReference userReference = null;

        userReference = dataReference.getRoot().child(MESSENGER_PATH).child(code_user);
        return userReference;
    }

    public DatabaseReference getOneRouteReference(String idGuide){
        return dataReference.getRef().child(ROUTE_PATH).child(idGuide);
    }

    public Query getOneRouteReferenceGuide(String idGuide){
        return  dataReference.getRoot().child(ROUTE_PATH).orderByChild(TITLE_IDGUIA_PATH).equalTo(idGuide);
    }

    public Query getOneClientReference(String cedula){
        return  dataReference.getRoot().child(CLIENT_PATH).orderByChild(TITLE_CEDULA_PATH).equalTo(cedula);
    }

    public DatabaseReference getOneProductReference(String idProduct){
        return dataReference.getRoot().child(PRODUCT_PATH).child(idProduct);
    }

    public DatabaseReference getClientReference(String idCedula){
        return dataReference.getRoot().child(CLIENT_PATH).child(idCedula);
    }


    public Query getRouteReferents(){
        if (getAuthUserEmail() != null){
            String codeMessenger =getAuthUserEmail().split("@")[0].toString();
            return dataReference.getRef().child(ROUTE_PATH).orderByChild(TITLE_ID_MESSENGER_PATH).equalTo(codeMessenger);
        }
        return null;
    }

    public String create(){
        return dataReference.push().getKey();
    }

    public void update(Photo photo){
        this.dataReference.getRoot().child(PHOTO_PATH).child(photo.getId()).setValue(photo);
    }

    public void update(Manifest manifest){
        this.dataReference.getRoot().child(MANIFEST_PATH).child(manifest.getId()).setValue(manifest);
    }

    public void remove(Photo photo, FirebaseActionListenerCallback listenerCallback){
        this.dataReference.getRef().child(PHOTO_PATH).child(photo.getId()).removeValue();
        listenerCallback.onSuccess();
    }

    public void putRoutes(Route route, String idGuia){
        this.dataReference.getRoot().child(ROUTE_PATH).child(idGuia).setValue(route);
    }

    public void putClients(Client client, String cedula){
        this.dataReference.getRoot().child(CLIENT_PATH).child(cedula).setValue(client);
    }

    public void putProducts(Product product, String idProducto){
        this.dataReference.getRoot().child(PRODUCT_PATH).child(idProducto).setValue(product);
    }

    public void putMessenger(Messenger messenger, String cedula) {
        this.dataReference.getRoot().child(MESSENGER_PATH).child(cedula).setValue(messenger);
    }


    public void updateCargueInformation(String codeMessager, Map<String, Object> updates) {
        this.dataReference.getRoot().child(INFORMATION_PATH).child(codeMessager).setValue(updates);
    }


    public DatabaseReference getOneInformationReference(String codeMessager){
        return dataReference.getRef().child(INFORMATION_PATH).child(codeMessager);
    }

    public void deleteRoutesMessager(String codRoute){
        this.dataReference.getRef().child(ROUTE_PATH).child(codRoute).removeValue();
    }




    public void checkForData(final FirebaseActionListenerCallback listenerCallback){
        this.dataReference.getRef().child(PHOTO_PATH).child(PHOTO_PATH).orderByChild(TITLE_GUIA_PATH).equalTo("1100120200001").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() > 0 ){
                    listenerCallback.onSuccess();
                }else {
                    listenerCallback.onError(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listenerCallback.onError(databaseError);
            }

        });
    }


    public void subscribe(final FirebaseEventListenerCallback listenerCallback, String guia){
        if (photosEventListener == null){
            photosEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    listenerCallback.onChildAdded(dataSnapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    listenerCallback.onChildChanged(dataSnapshot);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    listenerCallback.onChildRemoved(dataSnapshot);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError firebaseError) {
                    listenerCallback.onCancelled(firebaseError);
                }
            };
        }
        this.dataReference.getRef().child(PHOTO_PATH).orderByChild(TITLE_GUIA_PATH).equalTo(guia).addChildEventListener(photosEventListener);
    }

    public void unsubscribe(){
        if (photosEventListener != null){
            this.dataReference.getRef().child(PHOTO_PATH).removeEventListener(photosEventListener);
        }
    }
}
