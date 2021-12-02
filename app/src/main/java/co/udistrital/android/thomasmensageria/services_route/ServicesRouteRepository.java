package co.udistrital.android.thomasmensageria.services_route;

import co.udistrital.android.thomasmensageria.entities.Product;
import co.udistrital.android.thomasmensageria.entities.Route;


public interface ServicesRouteRepository {
    void getRoute(String idRoute);
    void getProduct(Route route);
    void getClient(Route route, Product producto);
}
