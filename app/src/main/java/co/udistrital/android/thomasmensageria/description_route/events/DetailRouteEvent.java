package co.udistrital.android.thomasmensageria.description_route.events;

import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Product;


public class DetailRouteEvent {

    private String error;
    private Client client;
    private Product product;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

