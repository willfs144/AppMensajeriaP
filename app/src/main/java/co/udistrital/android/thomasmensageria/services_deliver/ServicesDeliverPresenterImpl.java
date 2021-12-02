package co.udistrital.android.thomasmensageria.services_deliver;

import android.util.Log;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.greenrobot.eventbus.Subscribe;

import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.services_deliver.events.ServicesDeliverEvent;
import co.udistrital.android.thomasmensageria.services_deliver.ui.ServicesDeliverView;


public class ServicesDeliverPresenterImpl implements ServicesDeliverPresenter{

    private ServicesDeliverView view;
    private EventBus eventBus;
    private ServicesDeliverInteractor interactor;

    public ServicesDeliverPresenterImpl(ServicesDeliverView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        interactor = new ServicesDeliverInteractorImpl();
    }

    @Override
    public void onResume() {
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    public void validateCedula(Client client, Route route, IntentResult scanResult){
        Log.e("cedula1", client.getCedula().toString().trim());
        if (scanResult.getFormatName().equals(IntentIntegrator.PDF_417)){
            String result = scanResult.getContents();
            String  cedula =result.substring(48,58).replaceFirst("^0*","");
            Log.e("cedula2",cedula.toString());
            if (cedula.equals(client.getCedula())){
                    view.switchCedula();
                    client.setValidate(true);

                    String nombre1 = result.substring(92, 124).trim();
                    String nombre2 = result.substring(125,140).trim();
                    String apellido1 = result.substring(58,74).trim();
                    String apellido2 = result.substring(75,91).trim();
                    /*String sexo = result.substring(151,152);
                    String anual = result.substring(152,156);
                    String mes = result.substring(156,158);
                    String dia = result.substring(158,160);
                    String rh = result.substring(166,168);*/

                    view.showValidateCC(cedula,nombre1,nombre2,apellido1,apellido2);

                this.view.msgServices("Cedula validada");
            }else {this.view.msgServices("Cedula diferente");}

        }

    }

    public void validateButtomCedula(Client client, String cedula, String name1, String name2, String surname1, String surname2){
        if (cedula.equals(client.getCedula())) {
            view.switchCedula();
            client.setValidate(true);
            view.showValidateCC(cedula,client.getNombre().toUpperCase().toString(),"","","");
            this.view.msgServices("Cedula validada");
        }else {
            this.view.msgServices("Cedula diferente");
        }

    }

    @Override
    public void getDeltailRoute(int idproducto) {
        if (view != null){
            view.hideElements();
            view.showProgress();
        }
        this.interactor.execute(idproducto);

    }

    @Override
    @Subscribe
    public void onEventMainThread(ServicesDeliverEvent event) {
        String errorMsg = event.getError();
        if (view != null){
            view.showElements();
            view.hideProgress();
        }
        if (errorMsg != null){
            view.msgServices(event.getError());
        }else{
            view.setContentProduct(event.getProduct());
        }

    }

    @Override
    public void updateStated(String idRoute) {
        this.interactor.updateStatedCC(idRoute);
    }
}
