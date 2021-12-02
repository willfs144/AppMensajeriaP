package co.udistrital.android.thomasmensageria.lib;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;

public class GlideImageLoader implements ImageLoader {

    private RequestManager glideRequestManager;
    private RequestListener onFinishedLoadingListener;
    private Context context;


    public GlideImageLoader(Context context) {
        this.context = context;
    }

    public GlideImageLoader(RequestManager glideRequestManager) {
        this.glideRequestManager = glideRequestManager;
    }


    @Override
    public void load(ImageView imgeView, String URL) {
        if (onFinishedLoadingListener !=null){
            glideRequestManager
                    .load(URL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .listener(onFinishedLoadingListener)
                    .into(imgeView);
            Log.e("Url imagen:", URL.toString());
        }else{
            glideRequestManager
                    .load(URL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(800,800)
                    .centerCrop()
                    .into(imgeView);
            Log.e("Url imagen:", URL.toString());
        }

    }

    public void load(ImageView imageView, int my_drawable_image_name ){
        glideRequestManager
                .load(my_drawable_image_name)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(200,200)
                .centerCrop()
                .into(imageView);
    }


    @Override
    public void setOnFinishedImageLoadingListener(Object listener) {
        if (listener instanceof RequestListener)
            this.onFinishedLoadingListener = (RequestListener) listener;
    }
}
