package co.udistrital.android.thomasmensageria.get_route;


public interface RouteListInteractor {

    void removeRoute(String idRoute);
    void approveRoute(String id_route, boolean approve);

    void destroyListener();
    void unsubscribe();
    void subscribe();
}
