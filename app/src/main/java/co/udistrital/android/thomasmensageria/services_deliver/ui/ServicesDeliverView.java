package co.udistrital.android.thomasmensageria.services_deliver.ui;

import co.udistrital.android.thomasmensageria.entities.Product;


public interface ServicesDeliverView {

    void showProgress();
    void hideProgress();

    void validateCedula();
    void bcontinue();

    void showClient();
    void showRoute();

    void showValidateCC(String cc, String name1, String name2, String surname1, String surname2);
    void switchCedula();
    void msgServices(String msg);

    void hideElements();
    void showElements();

    void setContentProduct(Product product);
}
