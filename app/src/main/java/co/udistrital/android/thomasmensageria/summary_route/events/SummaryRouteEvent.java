package co.udistrital.android.thomasmensageria.summary_route.events;

import java.util.List;

import co.udistrital.android.thomasmensageria.entities.Route;


public class SummaryRouteEvent {

    public final static int onRouteInError = 0;
    public final static int onRouteInSuccess = 1;


    private int eventType;
    private String errorMessage;
    private List<Route> routeList;

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
