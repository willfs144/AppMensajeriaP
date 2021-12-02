package co.udistrital.android.thomasmensageria.generate_pdf.ui;

import co.udistrital.android.thomasmensageria.entities.Product;


public interface PDF_View {

    void setContentProduct(Product product);
    void generarPDF();
    void msgServices(String error);

    void hideElements();
    void showProgress();
    void showElements();
    void hideProgress();
}
