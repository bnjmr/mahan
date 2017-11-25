package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.gson.CurrentFeshFesheResponse;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetCurrentFeshFesheResponse {
    CurrentFeshFesheResponse currentFeshFesheResponse;
    public EventOnGetCurrentFeshFesheResponse(CurrentFeshFesheResponse currentFeshFesheResponses) {
        this.currentFeshFesheResponse = currentFeshFesheResponses;
    }
    public CurrentFeshFesheResponse getCurrentFeshFesheResponse() {
        return currentFeshFesheResponse;
    }
}
