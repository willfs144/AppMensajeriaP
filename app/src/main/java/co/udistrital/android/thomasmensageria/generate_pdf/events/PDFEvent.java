package co.udistrital.android.thomasmensageria.generate_pdf.events;

import co.udistrital.android.thomasmensageria.entities.Product;


public class PDFEvent {

    private String error;
    private Product product;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
