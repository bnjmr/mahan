package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.gson.NotifyResponse;

import java.util.List;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetNotifiesResponse {
    List<NotifyResponse> notifyResponse;
    public EventOnGetNotifiesResponse(List<NotifyResponse> notifyResponse) {
        this.notifyResponse = notifyResponse;
    }
    public List<NotifyResponse> getNotifyResponse() {
        return notifyResponse;
    }
}
