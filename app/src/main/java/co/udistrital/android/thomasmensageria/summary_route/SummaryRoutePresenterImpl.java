package co.udistrital.android.thomasmensageria.summary_route;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.stream.Collectors;

import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.lib.EventBus;
import co.udistrital.android.thomasmensageria.lib.GreenRobotEventBus;
import co.udistrital.android.thomasmensageria.summary_route.events.SummaryRouteEvent;
import co.udistrital.android.thomasmensageria.summary_route.ui.SummaryRouteFragment;
import co.udistrital.android.thomasmensageria.summary_route.ui.SummaryRouteView;


public class SummaryRoutePresenterImpl implements SummaryRoutePresenter{

    public final static String A_INACTIVO ="inactivo";
    public static final String A_NOENTREGADO = "no entregado";
    public static final String A_ENTREGADO = "entregado";
    public final static String A_VALIDATE ="En proceso de entrega";
    private static final String A_ACTIVO = "activo";

    private SummaryRouteView view;
    private EventBus eventBus;
    private SummaryRouteInteractor interactor;

    public SummaryRoutePresenterImpl(SummaryRouteFragment summaryRouteFragment) {
        this.view = summaryRouteFragment;
        eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new SummaryRouteInteractorImpl();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void consultRoutes() {
        if (view != null) {
            view.hideElements();
            view.showProgress();
            interactor.execute();
        }
    }



    @SuppressLint("NewApi")
    private void reportRoutes(List<Route> routeList) {
        List<Route> rutas;
        rutas =routeList.stream().filter(r -> r.getEstado().equals(A_ENTREGADO)).collect(Collectors.toList());//efectivos
        view.reportEffective(rutas.size());
        calculeRecaptures(rutas);
        rutas = routeList.stream().filter(r -> r.getEstado().equals(A_NOENTREGADO)).collect(Collectors.toList());//fallidos
        view.reportFailed(rutas.size());
        rutas = routeList.stream().filter(r -> r.getEstado().equals(A_ACTIVO)).collect(Collectors.toList());//pendientes
        int activos = rutas.size();
        rutas = routeList.stream().filter(r -> r.getEstado().equals(A_VALIDATE)).collect(Collectors.toList());//pendientes
        view.reportSlopes(activos+rutas.size());
        view.reportService();
        view.graphReports();
        view.msg();
    }

    private void calculeRecaptures(List<Route> rutas) {
        float suma = 0;
        for (Route ruta: rutas) {
            suma += Float.valueOf(ruta.getValor_recaudo());
        }
        view.reportRecaptures(suma);
    }

    @Override
    public void closeRoutes() {
        interactor.closing();
    }

    @Override
    @Subscribe
    public void onEventMainThread(SummaryRouteEvent event) {
        String error = event.getErrorMessage();
        if (error != null){
            if (error.isEmpty())
                view.onMsgError("Rutas no existen");
             else
                view.onMsgError(error);
        }else {
            view.hideProgress();
            view.showElements();
            reportRoutes(event.getRouteList());
        }
    }




}
