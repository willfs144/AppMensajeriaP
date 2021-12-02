package co.udistrital.android.thomasmensageria.services_returned;


public class ServicesReturnedInteractorImpl implements ServicesReturnedInteractor {

    private ServicesReturnedRepository repository;

    public ServicesReturnedInteractorImpl() {
        this.repository = new ServicesReturnedRepositoryImpl();
    }

    @Override
    public void updateRoute(String idRoute, String novedad) {
        repository.updateRoute(idRoute,novedad);
    }
}
