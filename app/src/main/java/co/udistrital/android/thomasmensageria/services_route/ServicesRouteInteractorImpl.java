package co.udistrital.android.thomasmensageria.services_route;


public class ServicesRouteInteractorImpl implements ServicesRouteInteractor {


    private ServicesRouteRepository repository;


    public ServicesRouteInteractorImpl() {
        this.repository = new ServicesRouteRepositoryImpl();
    }

    @Override
    public void execute(String idroute) {
        repository.getRoute(idroute);
    }
}
