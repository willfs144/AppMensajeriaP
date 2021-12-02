package co.udistrital.android.thomasmensageria.add_firm;

import android.content.Context;


public class AddFirmInteractorImpl implements AddFirmInteractor{

    private AddFirmRepository repository;

    public AddFirmInteractorImpl(Context context) {
        repository = new AddFirmRepositoryImpl(context);
    }

    @Override
    public void execute(String idRoute, String path) {
        repository.uploadPhoto(idRoute,path);
    }
}
