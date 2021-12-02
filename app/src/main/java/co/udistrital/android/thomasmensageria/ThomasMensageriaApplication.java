package co.udistrital.android.thomasmensageria;

import android.app.Application;
import android.content.Context;

import com.cloudinary.Cloudinary;
import com.google.firebase.FirebaseApp;



public class ThomasMensageriaApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        setupFirebase();
    }

    private void setupFirebase() {
        FirebaseApp.initializeApp(FirebaseApp.getInstance().getApplicationContext());
    }

    public static Context getAppContext() {
        return appContext;
    }
}
