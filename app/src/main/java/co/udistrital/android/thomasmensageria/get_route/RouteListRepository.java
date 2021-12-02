package co.udistrital.android.thomasmensageria.get_route;


public interface RouteListRepository {


    void approveRoute(String id_route, boolean approve);
    void removeRoute(String idRoute);//Route route

    void destroyListener();

    void unSubscribeToRouteListEvents();

    void subscribeToRouteListEvents();
}
