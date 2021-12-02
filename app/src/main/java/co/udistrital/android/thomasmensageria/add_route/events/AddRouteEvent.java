package co.udistrital.android.thomasmensageria.add_route.events;

public class AddRouteEvent {

    public final static int onRouteAddInError = 0;
    public final static int onRouteAddInSuccess = 1;

    private int eventType;
    private String errorMessage;

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
