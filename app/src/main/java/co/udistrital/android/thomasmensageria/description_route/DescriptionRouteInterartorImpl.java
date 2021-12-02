package co.udistrital.android.thomasmensageria.description_route;

import android.util.Log;


public class DescriptionRouteInterartorImpl implements DescriptionRouteInteractor{

    private DescriptionRouteRepository repository;


    public DescriptionRouteInterartorImpl(){
        repository = new DescriptionRouteRepositoryImpl();
    }

    @Override
    public void execute(int idproducto) {
        repository.getDescriptionRoute(idproducto);
    }

}
