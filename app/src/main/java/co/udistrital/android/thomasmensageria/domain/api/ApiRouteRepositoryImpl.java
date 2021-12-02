package co.udistrital.android.thomasmensageria.domain.api;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import co.udistrital.android.thomasmensageria.ThomasMensageriaApplication;
import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Product;
import co.udistrital.android.thomasmensageria.entities.Route;


public class ApiRouteRepositoryImpl extends Application{

    private final static String VOLLEY_URL = "http://gestionenvios-001-site1.itempurl.com/Appointment/GetRoute?";

    public static RequestQueue requestQueue;
    private FirebaseHelper helper;
    private Context context;
    private String cedula;


    public ApiRouteRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
        context = ThomasMensageriaApplication.getAppContext();
        requestQueue = Volley.newRequestQueue(context);

    }



    public void loadSetup(String cedula) {
        this.cedula=cedula;
        final Query information = helper.getOneInformationReference(cedula);
        information.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Boolean load = dataSnapshot.child("load").getValue(Boolean.class);
                    String fecha = dataSnapshot.child("fecha").getValue(String.class);
                    post(load, fecha);
                }else
                    post(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void post(final Boolean load){
        post(load, null);
    }

    public void post(final Boolean load, String fecha) {
        if(load){}
        else {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            if (fecha==null || !fecha.equals(timeStamp)){
                ApiLoginRepositoryImpl apiLogin = new ApiLoginRepositoryImpl();
                apiLogin.loadMessenger(this.cedula);

                String url = VOLLEY_URL + "idmensajero=" + this.cedula;

                JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        onResponseLoaded, onResponseError);

                System.out.println("respuesta__Response: " + jsObjRequest.toString());

                requestQueue.add(jsObjRequest);
            }

        }
        Log.e("load_",load.toString());
    }


    private final Response.Listener<JSONArray> onResponseLoaded = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray jsonArray) {
            try{

                for (int i=0; i <jsonArray.length(); i++){
                    JSONObject json = jsonArray.getJSONObject(i);
                    addRoute(json);
                    addClient(json);
                    addProduct(json);
                    Log.e("RESPONSE__Response",json.toString());
                }

                updateStateCargue();
            }catch (JSONException e){
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }
    };

    private void addRoute(JSONObject json) throws JSONException {
        Route route = new Route();
        route.setAutorizacion("1234");
        route.setBarrio(json.getString("Barrio"));
        route.setCantidad(json.getInt("CantidadProducto"));
        route.setCc_validada(false);
        route.setCiudad(json.getString("NameCiti"));
        route.setConsecutivo(json.getInt("id_appointment"));
        route.setDireccion(json.getString("Direccion"));
        route.setEstado("activo");//cambiar
        String fechaE []= json.getString("FechaEntrega").split("T");
        route.setFecha_entrega(fechaE[0]);
        String fechaR []= json.getString("FechaAccion").split("T");
        route.setFecha_envio(fechaR[0]);
        route.setIdmensajero(json.getString("IdentificaMensa"));
        route.setIdproducto(json.getInt("id_Producto"));
        route.setLatitude(Double.parseDouble(json.getString("Latitud")));
        route.setLongitude(Double.parseDouble(json.getString("longitud")));
        route.setObservacion(json.getString("ObserbacionEntrega"));
        route.setOrigen("Bodega Salitre");
        route.setRecaudo(false);
        Boolean servicio = json.getBoolean("Contraentrega");
        route.setServicio(servicio ? "Credito en destino":"Envío Gratis");
        route.setValidado(false);
        route.setValor_recaudo(servicio ?"5000":"0");

        helper.putRoutes(route, json.getString("Guia"));
    }

    private void addProduct(JSONObject json) throws JSONException {
        Product product = new Product();
        product.setCliente(json.getString("Documento"));
        product.setNombre(json.getString("NameProduct"));
        product.setEstado("activo");
        helper.putProducts(product, json.getString("id_Producto"));
    }

    private void addClient(JSONObject json) throws JSONException {
        Client client = new Client();
        client.setEstado(true);
        client.setNombre(json.getString("Nombre")+" "+json.getString("Apellido"));
        client.setTelefono(json.getString("Telefono"));
        client.setCorreo(json.getString("Correo"));
        helper.putClients(client, json.getString("Documento"));
    }

    private void updateStateCargue() {
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("load", true);
        helper.updateCargueInformation(this.cedula, updates);
    }

    private final Response.ErrorListener onResponseError =  new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("ERROR_Response",error.toString());
        }
    };
}
