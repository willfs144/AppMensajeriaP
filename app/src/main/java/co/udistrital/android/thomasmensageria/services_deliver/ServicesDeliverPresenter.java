package co.udistrital.android.thomasmensageria.services_deliver;

import com.google.zxing.integration.android.IntentResult;

import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.services_deliver.events.ServicesDeliverEvent;

/**
 * Created by wisuarez on 16/09/2019.
 */

public interface ServicesDeliverPresenter {

    void onResume();
    void onPause();
    void validateCedula(Client client, Route route, IntentResult scanResult);
    void validateButtomCedula(Client client, String cedula, String name1, String name2, String lastName1, String lastName2);
    void getDeltailRoute(int idproducto);
    void onEventMainThread(ServicesDeliverEvent event);

    void updateStated(String stated);
}
