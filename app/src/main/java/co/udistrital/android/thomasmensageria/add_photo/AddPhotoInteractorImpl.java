package co.udistrital.android.thomasmensageria.add_photo;

import android.content.Context;

import co.udistrital.android.thomasmensageria.add_photo.ui.AddPhotoView;


public class AddPhotoInteractorImpl implements AddPhotoInteractor {

    AddPhotoRepository repository;

    public AddPhotoInteractorImpl() {
        repository = new AddPhotoRepositoryImpl();
    }


    @Override
    public void execute(String idRoute, String path) {
        repository.uploadPhoto(idRoute, path);
    }
}
