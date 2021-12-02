package co.udistrital.android.thomasmensageria.lib;

import android.widget.ImageView;


public interface ImageLoader {

    void load(ImageView imgAvatar, String url);
    void load(ImageView imageView, int my_drawable_image_name );
    void setOnFinishedImageLoadingListener(Object listener);
}
