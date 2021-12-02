package co.udistrital.android.thomasmensageria.add_route;


public interface AddRouteRepository {
    void approveRoute(String id_route, boolean approve);
    void addRoute(String idRoute, String novedad, String idAdmin);
}
