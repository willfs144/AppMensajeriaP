package co.udistrital.android.thomasmensageria.main;


public class MainInteractorImpl implements MainInteractor {

    private MainRepository mainRepository;


    public MainInteractorImpl() {
        mainRepository = new MainRepositoryImpl();
    }

    @Override
    public void signOff() {
        mainRepository.signoff();
    }

    @Override
    public void execute() {
        mainRepository.updateProfileShow();
    }
}
