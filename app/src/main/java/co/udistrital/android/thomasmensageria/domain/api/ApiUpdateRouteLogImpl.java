package co.udistrital.android.thomasmensageria.domain.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import co.udistrital.android.thomasmensageria.ThomasMensageriaApplication;



public class ApiUpdateRouteLogImpl {

    private final static String VOLLEY_URL = "http://gestionenvios-001-site1.itempurl.com/Appointment/GetNewManifest?";
    private static final String A_ACTIVO = "activo";
    private final static String A_VALIDATE ="En proceso de entrega";
    public final static String A_INACTIVO ="inactivo";
    public static final String A_NOENTREGADO = "no entregado";
    public static final String A_ENTREGADO = "entregado";


    private Context context;
    public static RequestQueue requestQueue;

    public ApiUpdateRouteLogImpl() {
        context = ThomasMensageriaApplication.getAppContext();
        requestQueue = Volley.newRequestQueue(context);
    }

    public void updateRouteLog(String fecha, String idRuta, String novedad, String observacion){
        int idNovedad= findIdNovedad(novedad);

        String url = VOLLEY_URL + "fecha=" + fecha+"&idRuta="+ idRuta+"&IdNovedad="+idNovedad+"&Obserbacion="+observacion;
        Log.e("updateRouteLog", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("updateRouteLog", error.toString());
            }
        });
        System.out.println("respuesta: "+ jsonObjectRequest.toString());
        requestQueue.add(jsonObjectRequest);

    }

    private int findIdNovedad(String novedad) {
        switch (novedad){
            case A_ACTIVO:
                return 1;
            case A_VALIDATE:
                return 2;
            case A_ENTREGADO:
                return 3;
            case A_NOENTREGADO:
                return 4;
            default:
                return 0;
        }
    }


}
