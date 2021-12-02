package co.udistrital.android.thomasmensageria.generate_pdf;

import org.greenrobot.eventbus.Subscribe;

import co.udistrital.android.thomasmensageria.generate_pdf.events.PDFEvent;
import co.udistrital.android.thomasmensageria.generate_pdf.ui.PDF_View;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;


public class PDFPresenterImpl implements PDFPresenter {

    private PDF_View view;
    private EventBus eventBus;
    private PDFInteractor interactor;

    public PDFPresenterImpl(PDF_View view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        interactor = new PDFInteractorImpl();
    }


    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void getDeltailRoute(int idproducto) {
        if (view != null){
            view.hideElements();
            view.showProgress();
        }
        interactor.execute(idproducto);

    }

    @Override
    public void updateStated(String idRoute) {
        interactor.updateRoute(idRoute);
    }

    @Override
    @Subscribe
    public void onEventMainThread(PDFEvent event) {
        String errorMsg = event.getError();
        if (view != null){
            view.showElements();
            view.hideProgress();
        }
        if (errorMsg != null){
            view.msgServices(event.getError());
        }else{
            view.setContentProduct(event.getProduct());
            view.generarPDF();
        }
    }
}
