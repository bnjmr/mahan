package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.gson.CityResponse;

/**
 * Created by Microsoft on 4/24/2016.
 */
public class EventOnGetCityResponse {
    CityResponse[] cityResponses;
    public EventOnGetCityResponse(CityResponse[] cityResponses) {
        this.cityResponses = cityResponses;
    }
    public CityResponse[] getCityResponses() {
        return cityResponses;
    }
}
