package ir.aspacrm.my.app.mahanet.events;

/**
 * Created by Microsoft on 4/24/2016.
 */
public class EventOnGetRegisterCustomerResponse {
    boolean result;
    String message = "";
    public EventOnGetRegisterCustomerResponse(boolean result, String message) {
        this.result = result;
        this.message = message;
    }
    public boolean isResult() {
        return result;
    }
    public String getMessage() {
        return message;
    }
}
