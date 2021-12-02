package co.udistrital.android.thomasmensageria.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.domain.api.ApiRouteRepositoryImpl;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.login.events.LoginEvent;


public class LoginRepositoryImpl implements LoginRepository {
    private FirebaseHelper helper;
    private DatabaseReference dataReference;
    private final ApiRouteRepositoryImpl imple;
    private String cedula;




    public LoginRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
        //this.dataReference = helper.getDataReference();
        imple = new ApiRouteRepositoryImpl();
    }

    @Override
    public void signIn(String email, String password) {
       FirebaseAuth auth = FirebaseAuth.getInstance();
       String usuario = email+"@thomas.com";
       try {
           auth.signInWithEmailAndPassword(usuario, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
               @Override
               public void onSuccess(AuthResult authResult) {
                   imple.loadSetup(email);
                   postEvent(LoginEvent.onSignInSuccess);
               }
           }).addOnFailureListener(new OnFailureListener() {

               @Override
               public void onFailure(@NonNull Exception e) {
                   postEvent(LoginEvent.onSignInError, e.getMessage());
               }
           });
       }catch (Exception e){
           postEvent(LoginEvent.onSignInError, e.getMessage());
       }
    }

    @Override
    public void checkSesion() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            this.cedula = helper.getAuthUserEmail().split("@")[0].toString();
            imple.loadSetup(cedula);
            postEvent(LoginEvent.onSignInSuccess);
        }else
            postEvent(LoginEvent.onFailedToRecoverSession);
    }

    private void postEvent(int type, String errorMessage){

        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null ){
            loginEvent.setErrorMessage(errorMessage);
        }
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }
    private void postEvent(int type){
        postEvent(type, null);
    }
}
