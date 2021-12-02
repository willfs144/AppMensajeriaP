package co.udistrital.android.thomasmensageria.delete_route;


public class DeleteRouteInteractorImpl implements DeleteRouteInteractor{

    private DeleteRouteRepository repository;

    public DeleteRouteInteractorImpl() {
        repository = new DeleteRouteRepositoryImpl();
    }

    @Override
    public void removeRoute(String idRoute, String observacion) {
        repository.removeRoute(idRoute, observacion);
    }
}
