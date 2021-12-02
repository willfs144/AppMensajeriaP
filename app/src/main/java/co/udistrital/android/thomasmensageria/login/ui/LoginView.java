package co.udistrital.android.thomasmensageria.login.ui;


public interface LoginView {
    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    void handleSingIn();

    void navigateToMainScreen();
    void loginError(String error);
}
