package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.gson.RegConnectResponse;

import java.util.List;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetRegConnectionResponse {
    List<RegConnectResponse> regConnectResponses;
    public EventOnGetRegConnectionResponse(List<RegConnectResponse> regConnectResponses) {
        this.regConnectResponses = regConnectResponses;
    }
    public List<RegConnectResponse> getRegConnectResponses() {
        return regConnectResponses;
    }
}
