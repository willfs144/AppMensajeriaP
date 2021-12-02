package co.udistrital.android.thomasmensageria.map_route;

import co.udistrital.android.thomasmensageria.entities.Route;

public interface MapRoutePresenter {
    void addInfo(Route route);
    void onInfoError(String error);
}
