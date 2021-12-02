package co.udistrital.android.thomasmensageria.description_route;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import co.udistrital.android.thomasmensageria.description_route.events.DetailRouteEvent;
import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Product;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;


public class DescriptionRouteRepositoryImpl implements DescriptionRouteRepository{

    private FirebaseHelper helper;
    private EventBus eventBus;

    private Product producto;
    private Client cliente;

    public DescriptionRouteRepositoryImpl() {
        helper = FirebaseHelper.getInstance();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void getDescriptionRoute(int idproduct) {
        getProduct(idproduct);

    }

    @Override
    public void getProduct(int idproduct) {

        final DatabaseReference product = helper.getOneProductReference(""+idproduct);

        product.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                product.setId(""+dataSnapshot.getKey());
                getClient(product);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                post(databaseError.toString());
            }
        });
    }

    @Override
    public void getClient(final Product producto) {
        //Query client = helper.getOneClientReference(""+cedula);


        final DatabaseReference client = helper.getClientReference(""+producto.getCliente());

        client.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Client client1 = dataSnapshot.getValue(Client.class);
                client1.setId(dataSnapshot.getKey());
                post(producto,client1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                post(databaseError.toString());
            }
        });


    }



    private void post(String error){
        post(null, null, error);
    }

    private void post(Product product, Client client){
        post(product, client,null);
    }

    private void post(Product product, Client client, String error){
        DetailRouteEvent event = new DetailRouteEvent();
        event.setProduct(product);
        event.setClient(client);
        event.setError(error);
        eventBus.post(event);
    }


}
