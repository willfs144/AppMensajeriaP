package co.udistrital.android.thomasmensageria.delete_route.ui;


public interface DeleteRouteView {

    void showInput();
    void hideInput();
    void showProgress();
    void hideProgress();

    void routeDeleted();
    void routeNotDeleted();

    void showMsg();
}
