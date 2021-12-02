package co.udistrital.android.thomasmensageria.show_photo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.udistrital.android.thomasmensageria.R;
import co.udistrital.android.thomasmensageria.entities.Photo;
import co.udistrital.android.thomasmensageria.lib.GlideImageLoader;
import co.udistrital.android.thomasmensageria.lib.ImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;


public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {



    private List<Photo> photoList;
    private ImageLoader imageLoader;
    private OnItemClickListener onItemClickListener;

    private String urlMensajero;
    private String nombreMensajero;
    private String cargoMensajero;


    public PhotoListAdapter(List<Photo> photoList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.photoList = photoList;
        this.imageLoader = imageLoader;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo currentPhoto = photoList.get(position);
        holder.setOnItemClickListener(currentPhoto, onItemClickListener);

        imageLoader.load(holder.imgMain, currentPhoto.getUrl());
        this.imageLoader.load(holder.imgAvatar, this.urlMensajero);
        holder.txtCargo.setText(this.cargoMensajero);
        holder.txtUser.setText(this.nombreMensajero);
        holder.txtPlace.setText("Soporte: " + (position + 1));

        holder.imgDelete.setVisibility(View.VISIBLE);

    }

    public void addPhoto(Photo photo) {
        if (!photoList.contains(photo)) {
            photoList.add(photo);
            notifyDataSetChanged();
        }
    }

    public void removePhoto(Photo photo) {
        photoList.remove(photo);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @BindView(R.id.txtUser)
        TextView txtUser;
        @BindView(R.id.layoutHeader)
        LinearLayout layoutHeader;
        @BindView(R.id.imgMain)
        ImageView imgMain;
        @BindView(R.id.imgDelete)
        ImageButton imgDelete;
        @BindView(R.id.txtPlace)
        TextView txtPlace;
        @BindView(R.id.txtCargo)
        TextView txtCargo;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOnItemClickListener(final Photo photo, final OnItemClickListener listener) {

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick(photo);
                }
            });

        }
    }

    public void setUrlMensajero(String urlMensajero) {
        this.urlMensajero = urlMensajero;
    }

    public void setNombreMensajero(String nombreMensajero) {
        this.nombreMensajero = nombreMensajero;
    }

    public void setCargoMensajero(String cargoMensajero) {
        this.cargoMensajero = cargoMensajero;
    }
}
