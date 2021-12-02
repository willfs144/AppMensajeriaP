package co.udistrital.android.thomasmensageria.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import co.udistrital.android.thomasmensageria.add_photo.ui.AddPhotoFragment;
import co.udistrital.android.thomasmensageria.lib.base.ImageStorage;
import co.udistrital.android.thomasmensageria.lib.base.ImageStorageFinishedListener;


public class CloudinaryImageStorage implements ImageStorage {

    private static class SingletonHolder {
        private static final CloudinaryImageStorage INSTANCE = new CloudinaryImageStorage();
    }

    private Cloudinary cloudinary;

    public CloudinaryImageStorage(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }

    public CloudinaryImageStorage(){
        cloudinary =new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "my_cloud_name",
                "api_key", "my_api_key",
                "api_secret", "my_api_secret"));
    }

    public CloudinaryImageStorage(Context context){
        cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(context));
    }

    public static ImageStorage getInstance() {
        return SingletonHolder.INSTANCE;
    }



    @Override
    public String getImageUrl(String id) {
        return cloudinary.url().generate(id);
    }


    @Override
    public void upload(final File file, final String id, final ImageStorageFinishedListener listener) {

        new AsyncTask<Void, Void, Void>() {

            boolean success = false;

            @Override
            protected Void doInBackground(Void... voids) {
                Map params = ObjectUtils.asMap("public_id", id);
                try {
                    cloudinary.uploader().upload(file,params);
                    success=true;
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onError(e.getLocalizedMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
               if (success){
                   listener.onSuccess();
               }
            }
        }.execute();
    }


}
