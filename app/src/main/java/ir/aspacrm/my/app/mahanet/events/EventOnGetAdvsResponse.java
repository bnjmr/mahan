package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.gson.GetAdvsResponse;

import java.util.List;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetAdvsResponse {
    List<GetAdvsResponse> advsResponse;
    public EventOnGetAdvsResponse(List<GetAdvsResponse> getAdvsResponse) {
        this.advsResponse = getAdvsResponse;
    }
    public List<GetAdvsResponse> getAdvsResponse() {
        return advsResponse;
    }
}
