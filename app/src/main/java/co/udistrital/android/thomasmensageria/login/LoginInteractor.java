package co.udistrital.android.thomasmensageria.login;


public interface LoginInteractor {
    void checkSession();
    void doSignIn(String email, String password);
}
