package co.udistrital.android.thomasmensageria.generate_pdf;


public class PDFInteractorImpl implements PDFInteractor {

    private PDFRepository repository;

    public PDFInteractorImpl() {
        repository = new PDFRepositoryImpl();
    }

    @Override
    public void execute(int idproducto) {
        repository.getProduct(idproducto);
    }

    public void updateRoute(String idRoute){
        repository.updateStated(idRoute);
    }
}
