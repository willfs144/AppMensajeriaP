package co.udistrital.android.thomasmensageria.summary_route;

public class SummaryRouteInteractorImpl implements SummaryRouteInteractor {

    private SummaryRouteRepository repository;

    public SummaryRouteInteractorImpl() {
        this.repository = new SummaryRouteRepositoryImpl();
    }

    @Override
    public void execute() {
        repository.consultRouts();
    }

    @Override
    public void closing() {
        repository.updateLoad();
    }
}
