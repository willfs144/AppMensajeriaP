package co.udistrital.android.thomasmensageria.services_deliver;


public class ServicesDeliverInteractorImpl implements ServicesDeliverInteractor{

    ServicesDeliverRepository repository;

    public ServicesDeliverInteractorImpl() {

        repository = new ServicesDeliverRepositoryImpl();
    }

    @Override
    public void execute(int idproducto) {
        repository.getDescriptionRoute(idproducto);
    }

    @Override
    public void updateStatedCC(String idRoute) {
        repository.updateStated(idRoute);
    }
}
