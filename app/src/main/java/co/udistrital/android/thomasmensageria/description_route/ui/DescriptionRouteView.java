package co.udistrital.android.thomasmensageria.description_route.ui;

import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Product;
import co.udistrital.android.thomasmensageria.entities.Route;


public interface DescriptionRouteView {

    void showElements();
    void hideElements();
    void showProgressBar();
    void hideProgressBar();

    void onError(String error);
    void setContentRoute();
    void setContentClient(Client client);
    void setContentProduct(Product product);




}
