package co.udistrital.android.thomasmensageria.login;


public interface LoginRepository {
    void signIn(String email, String password);
    void checkSesion();
}
