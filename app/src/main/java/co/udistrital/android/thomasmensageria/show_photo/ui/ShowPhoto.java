package co.udistrital.android.thomasmensageria.show_photo.ui;

import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.udistrital.android.thomasmensageria.add_photo.ui.AddPhotoFragment;
import co.udistrital.android.thomasmensageria.entities.Messenger;
import co.udistrital.android.thomasmensageria.entities.Photo;
import co.udistrital.android.thomasmensageria.lib.GlideImageLoader;
import co.udistrital.android.thomasmensageria.lib.ImageLoader;
import co.udistrital.android.thomasmensageria.show_messenger.ui.ShowMessenger;
import co.udistrital.android.thomasmensageria.show_photo.ShowPhotoPresenter;
import co.udistrital.android.thomasmensageria.show_photo.ShowPhotoPresenterImpl;
import co.udistrital.android.thomasmensageria.show_photo.adapters.OnItemClickListener;
import co.udistrital.android.thomasmensageria.show_photo.adapters.PhotoListAdapter;


public class ShowPhoto implements ShowPhotoView, OnItemClickListener {

    AddPhotoFragment photoFragment;
    private ShowPhotoPresenter presenterShowPhoto;
    RecyclerView recyclerViewPhoto;
    ProgressBar progressBarPhoto;
    private ShowMessenger showMessenger;
    private Messenger mensajero;

    PhotoListAdapter adapter;


    public ShowPhoto(AddPhotoFragment addPhotoFragment, RecyclerView recyclerViewPhoto, ProgressBar progressBarPhoto) {
        presenterShowPhoto = new ShowPhotoPresenterImpl(this);
        this.photoFragment = addPhotoFragment;
        this.recyclerViewPhoto = recyclerViewPhoto;
        this.progressBarPhoto = progressBarPhoto;
        showMessenger = new ShowMessenger(addPhotoFragment.getRoute().getIdmensajero());

        presenterShowPhoto.onCreate();
        presenterShowPhoto.subscribe(addPhotoFragment.getRoute().getId());
        setupAdapter();
        setupRecyclerView();
        setupMensajero();
    }

    public void setupMensajero() {

    }


    public void onDestroy(){
        presenterShowPhoto.unsubscribe();
        presenterShowPhoto.OnDestroy();
        this.photoFragment = null;
    }

    private void setupAdapter() {
        RequestManager requestManager = Glide.with(photoFragment);
        ImageLoader imageLoader = new GlideImageLoader(requestManager);
        List<Photo> photoList = new ArrayList<Photo>();
        adapter = new PhotoListAdapter(photoList, imageLoader, this);
    }

    private void setupRecyclerView() {
        recyclerViewPhoto.setLayoutManager(new LinearLayoutManager(photoFragment.getActivity()));
        recyclerViewPhoto.setAdapter(adapter);
    }

    @Override
    public void showList() {
        photoFragment.showBottoms();
        recyclerViewPhoto.setVisibility(View.VISIBLE);

       showMensajero();
    }

    private void showMensajero() {
        this.mensajero = showMessenger.getMessenger();
        adapter.setNombreMensajero(mensajero.getNombre()+" "+mensajero.getApellido());
        adapter.setCargoMensajero(mensajero.getCargo()+":");
        adapter.setUrlMensajero(mensajero.getUrl());
    }

    @Override
    public void hideList() {
        photoFragment.hideBottoms();
        recyclerViewPhoto.setVisibility(View.GONE);

    }

    @Override
    public void showProgress() {
        progressBarPhoto.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarPhoto.setVisibility(View.GONE);

    }

    @Override
    public void addPhoto(Photo photo) {
        adapter.addPhoto(photo);
    }

    @Override
    public void removePhoto(Photo photo) {
        adapter.removePhoto(photo);
    }

    @Override
    public void onPhotosError(String error) {
        photoFragment.showSnackbar(error);
    }

    @Override
    public void onDeleteClick(Photo photo) {
        presenterShowPhoto.removePhoto(photo);
    }

    public void refreshView(){
        this.photoFragment.refresh();
    }

    public PhotoListAdapter getAdapter() {
        return adapter;
    }

    public Messenger getMensajero() {
        return mensajero;
    }
}
