package ir.aspacrm.my.app.mahanet.events;

/**
 * Created by Microsoft on 4/6/2016.
 */
public class EventOnGetIspUrlResponse {
    String ispUrl = "";
    public EventOnGetIspUrlResponse(String ispUrl) {
        this.ispUrl = ispUrl;
    }
    public String getIspUrl() {
        return ispUrl;
    }
}
