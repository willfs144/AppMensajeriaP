package co.udistrital.android.thomasmensageria.services_route;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Product;
import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.services_route.events.ServicesRouteEvent;

import static co.udistrital.android.thomasmensageria.entities.Route.A_INACTIVO;
import static co.udistrital.android.thomasmensageria.entities.Route.A_NOENTREGADO;
import static co.udistrital.android.thomasmensageria.entities.Route.A_ENTREGADO;


public class ServicesRouteRepositoryImpl  implements ServicesRouteRepository{
    private FirebaseHelper helper;
    private EventBus eventBus;


    public ServicesRouteRepositoryImpl() {
        helper = FirebaseHelper.getInstance();
        eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void getRoute(String idRoute) {

        final Query route = helper.getOneRouteReference(idRoute);

        route.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               Route route = dataSnapshot.getValue(Route.class);
               if(route!=null) {
                   if (!route.getEstado().toString().equals(A_INACTIVO))
                       if (!route.getEstado().toString().equals(A_ENTREGADO))
                           if (!route.getEstado().toString().equals(A_NOENTREGADO))
                               if (route.getValidado()) {
                                   route.setId(dataSnapshot.getKey().toString());
                                   getProduct(route);
                               } else post("Ruta a entregar pendiente por verificar");
                           else post("Ruta reportada como NO entregada");
                       else post("Ruta entregada al cliente");
                   else post("Ruta inactiva");
               }else post("Ruta no existe");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                post(databaseError.toString());
            }
        });
    }

    @Override
    public void getProduct(final Route route) {

        String codeMessenger = helper.getAuthUserEmail().split("@")[0].toString();

        if (route != null && route.getIdmensajero().equals(codeMessenger)){
            DatabaseReference product = helper.getOneProductReference(""+route.getIdproducto());

            product.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Product product = dataSnapshot.getValue(Product.class);
                    product.setId(""+dataSnapshot.getKey());
                    getClient(route,product);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    post(databaseError.toString());
                }
            });
        }else
            post("Número de guia no asignada");



    }

    @Override
    public void getClient(final Route route, final Product producto) {
        //Query client = helper.getOneClientReference(""+cedula);


        final DatabaseReference client = helper.getClientReference(""+producto.getCliente());

        client.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Client client1 = dataSnapshot.getValue(Client.class);
                client1.setId(dataSnapshot.getKey());
                client1.setCedula(dataSnapshot.getKey());
                post(route, client1);
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

    private void post(Route route, Client client){
        post(route, client,null);
    }

    private void post(Route route, Client client, String error){
        ServicesRouteEvent event = new ServicesRouteEvent();
        event.setRoute(route);
        event.setClient(client);
        event.setError(error);
        eventBus.post(event);
    }


}
