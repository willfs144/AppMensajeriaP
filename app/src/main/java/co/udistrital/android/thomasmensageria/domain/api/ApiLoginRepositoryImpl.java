package co.udistrital.android.thomasmensageria.domain.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import co.udistrital.android.thomasmensageria.ThomasMensageriaApplication;
import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.entities.Messenger;


public class ApiLoginRepositoryImpl {

    private final static String VOLLEY_URL = "http://gestionenvios-001-site1.itempurl.com/Administration/GetLoginApp?";
    private final static String PASSWORD = "Colombia2021*";

    public static RequestQueue requestQueue;
    private FirebaseHelper helper;
    private Context context;

    public ApiLoginRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
        context = ThomasMensageriaApplication.getAppContext();
        requestQueue = Volley.newRequestQueue(context);
    }


    public void loadMessenger(String cedula){

        String url = VOLLEY_URL+"Identificacion="+cedula+"&Password="+PASSWORD;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                onResponseLoaded, onResponseError);
        System.out.println("respuesta__Response: "+jsObjRequest.toString());
        requestQueue.add(jsObjRequest);
    }

    private final Response.Listener<JSONObject> onResponseLoaded = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                addMessenger(response);//cambiar
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("RESPONSE__Response",response.toString());
        }
    };



    private final Response.ErrorListener onResponseError =  new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("ERROR_Response",error.toString());
        }
    };

    public void addMessenger(JSONObject json) throws JSONException {
        Messenger messenger = new Messenger();
        messenger.setEstado("activo");
        messenger.setCedula(json.getString("Identification"));
        String lastName2 = json.getString("LastName2");
        messenger.setApellido(json.getString("LastName1")+" "+(lastName2=="null" ? " ":lastName2));
        messenger.setNombre(json.getString("Names"));
        messenger.setEmail(json.getString("Email"));
        messenger.setCargo("Mensajero");
        messenger.setTelefono("3194445356");
        messenger.setUrl("https://i.ytimg.com/vi/O2yg08ecjn4/hqdefault.jpg");

        helper.putMessenger(messenger, messenger.getCedula());
    }
}
