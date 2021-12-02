package co.udistrital.android.thomasmensageria.services_deliver.events;

import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Product;


public class ServicesDeliverEvent {

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
