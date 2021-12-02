package co.udistrital.android.thomasmensageria.generate_pdf;

import co.udistrital.android.thomasmensageria.generate_pdf.events.PDFEvent;


public interface PDFPresenter {
    public void onCreate();
    void onDestroy();
    void getDeltailRoute(int idproducto);
    void updateStated(String stated);
    void onEventMainThread(PDFEvent event);
}
