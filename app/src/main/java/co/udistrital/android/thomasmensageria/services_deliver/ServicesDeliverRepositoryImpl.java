package co.udistrital.android.thomasmensageria.services_deliver;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.entities.Product;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.services_deliver.events.ServicesDeliverEvent;

import static co.udistrital.android.thomasmensageria.entities.Route.V_CCVALIDADA;
import static co.udistrital.android.thomasmensageria.entities.Route.A_CCVALIDADA;
import static co.udistrital.android.thomasmensageria.entities.Route.V_RECAUDO;
import static co.udistrital.android.thomasmensageria.entities.Route.A_RECAUDO;

public class ServicesDeliverRepositoryImpl implements ServicesDeliverRepository{

    private FirebaseHelper helper;
    private EventBus eventBus;

    public ServicesDeliverRepositoryImpl() {
        helper = FirebaseHelper.getInstance();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void getDescriptionRoute(int idproducto) {
        getProduct(idproducto);
    }

    @Override
    public void getProduct(int idproduct) {

        final DatabaseReference product = helper.getOneProductReference(""+idproduct);

        product.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                product.setId(""+dataSnapshot.getKey());
                post(product);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                post(databaseError.toString());
            }
        });
    }

    @Override
    public void updateStated(String id_route) {
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put(V_CCVALIDADA, A_CCVALIDADA);
            updates.put(V_RECAUDO, A_RECAUDO);
            helper.getOneRouteReference(id_route).updateChildren(updates);
    }


    private void post(String error){
        post(null, error);
    }

    private void post(Product product){
        post(product, null);
    }

    private void post(Product product, String error){
        ServicesDeliverEvent event = new ServicesDeliverEvent();
        event.setProduct(product);
        event.setError(error);
        eventBus.post(event);
    }


}
