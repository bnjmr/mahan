package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.gson.AddScoreResponse;
import ir.aspacrm.my.app.mahanet.model.Locations;

/**
 * Created by HaMiD on 3/6/2017.
 */

public class EventOnAddScoreResponse {
    AddScoreResponse response;
    Locations location;

    public EventOnAddScoreResponse(AddScoreResponse response,Locations location) {
        this.response = response;
        this.location = location;
    }

    public EventOnAddScoreResponse() {
    }

    public AddScoreResponse getResponse() {
        return response;
    }

    public Locations getLocation() {
        return location;
    }
}
