package co.udistrital.android.thomasmensageria.services_deliver;


public interface ServicesDeliverRepository {

    void getDescriptionRoute(int idproducto);
    void getProduct(int idproduct);

    void updateStated(String stated);
    //void getClient(final Product producto);
}
