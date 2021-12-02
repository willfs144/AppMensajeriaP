package co.udistrital.android.thomasmensageria.generate_pdf;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.domain.api.ApiUpdateRouteLogImpl;
import co.udistrital.android.thomasmensageria.entities.Manifest;
import co.udistrital.android.thomasmensageria.entities.Product;
import co.udistrital.android.thomasmensageria.generate_pdf.events.PDFEvent;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;

import static co.udistrital.android.thomasmensageria.entities.Route.V_ESTADO;
import static co.udistrital.android.thomasmensageria.entities.Route.A_INACTIVO;
import static co.udistrital.android.thomasmensageria.entities.Route.A_ENTREGADO;

public class PDFRepositoryImpl implements PDFRepository{

    private FirebaseHelper helper;
    private EventBus eventBus;

    public PDFRepositoryImpl() {
        helper = FirebaseHelper.getInstance();
        eventBus = GreenRobotEventBus.getInstance();
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
        updates.put(V_ESTADO, A_ENTREGADO);
        updateRouteLog(id_route, A_ENTREGADO, A_INACTIVO);
        helper.getOneRouteReference(id_route).updateChildren(updates);
    }


    private void updateRouteLog(String idRoute, String novedad, String estado) {
        final String newPhotoId = helper.create();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Manifest manifest = new Manifest();
        manifest.setId(newPhotoId);
        manifest.setIdRuta(idRoute);
        manifest.setEstado(estado);
        manifest.setNovedad(novedad);
        manifest.setFecha(timeStamp);
        helper.update(manifest);

        ApiUpdateRouteLogImpl api = new ApiUpdateRouteLogImpl();
        api.updateRouteLog(timeStamp, idRoute, A_ENTREGADO , novedad);
    }

    private void post(String error){
        post(null, error);
    }

    private void post(Product product){
        post(product, null);
    }

    private void post(Product product, String error){
        PDFEvent event = new PDFEvent();
        event.setProduct(product);
        event.setError(error);
        eventBus.post(event);
    }
}
