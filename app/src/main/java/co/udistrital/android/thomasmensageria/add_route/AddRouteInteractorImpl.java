package co.udistrital.android.thomasmensageria.add_route;



public class AddRouteInteractorImpl implements AddRouteInteractor {

    private AddRouteRepository repository;

    public AddRouteInteractorImpl() {
        repository = new AddRouteRepositoryImpl();
    }

    @Override
    public void execute(String idRoute, String novedad, String idAdmin) {
        repository.addRoute(idRoute,novedad,idAdmin);
    }
}
