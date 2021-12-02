package co.udistrital.android.thomasmensageria.services_returned.events;


public class ServiceReturnedEvent {

    public final static int onRouteDeleteInError = 0;
    public final static int onRouteDeleteInSuccess = 1;


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
