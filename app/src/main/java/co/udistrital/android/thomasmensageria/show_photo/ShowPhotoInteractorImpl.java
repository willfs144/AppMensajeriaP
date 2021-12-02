package co.udistrital.android.thomasmensageria.show_photo;

import co.udistrital.android.thomasmensageria.entities.Photo;


public class ShowPhotoInteractorImpl implements ShowPhotoInteractor{

    private ShowPhotoRepository repository;

    public ShowPhotoInteractorImpl() {
        repository = new ShowPhotoRepositoryImpl();
    }

    @Override
    public void subscribe(String idRoute) {
        repository.subscribe(idRoute);
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribe();
    }

    @Override
    public void removePhoto(Photo photo) {
        repository.removePhoto(photo);
    }
}
