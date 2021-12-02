package co.udistrital.android.thomasmensageria.add_route.ui;



public interface AddRouteView {
    void showInput();
    void hideInput();
    void showProgress();
    void hideProgress();


    void closeDialog();
    void showMsg(String msg);
}
