package ir.aspacrm.my.app.mahanet.events;

/**
 * Created by Microsoft on 3/5/2016.
 */
public class EventOnGetErrorGetGraph {
    int errorType;
    public EventOnGetErrorGetGraph(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
