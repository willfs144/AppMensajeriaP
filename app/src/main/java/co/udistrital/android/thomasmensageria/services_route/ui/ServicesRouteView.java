package co.udistrital.android.thomasmensageria.services_route.ui;

import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Product;
import co.udistrital.android.thomasmensageria.entities.Route;


public interface ServicesRouteView {

    void showIconSearch();
    void hideIconSearch();

    void showIconBar();
    void hideIconBar();

    void showIconClear();
    void hideIconClear();

    void showForm();
    void hideForm();
    void showProgress();
    void hideProgress();

    void changeTextToIcon();

    void onError(String error);
    void setContentRoute(Route route);
    void setContentClient(Client client);
}
