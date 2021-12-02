package co.udistrital.android.thomasmensageria.description_route;

import co.udistrital.android.thomasmensageria.entities.Product;


public interface DescriptionRouteRepository {
    void getDescriptionRoute(int idproduct);
    void getProduct(int idproduct);
    void getClient(Product producto);
}
