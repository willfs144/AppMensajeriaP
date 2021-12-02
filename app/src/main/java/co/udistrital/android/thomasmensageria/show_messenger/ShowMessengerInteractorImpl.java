package co.udistrital.android.thomasmensageria.show_messenger;


public class ShowMessengerInteractorImpl implements ShowMessengerInteractor{

    private ShowMessengerRepository repository;

    public ShowMessengerInteractorImpl() {
        repository = new ShowMessengerRepositoryImpl();
    }

    @Override
    public void execute(String idmensajero) {
        repository.updateProfileShow(idmensajero);
    }
}
