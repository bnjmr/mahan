package ir.aspacrm.my.app.mahanet.events;

/**
 * Created by Microsoft on 3/7/2016.
 */
public class EventOnGetErrorStartFactor {
    int errorType;
    public EventOnGetErrorStartFactor(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
