package ir.aspacrm.my.app.mahanet.events;

import java.util.ArrayList;

import ir.aspacrm.my.app.mahanet.gson.FactorDetailResponse;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetFactorDetailsResponse {
    ArrayList<FactorDetailResponse> factorDetailsResponse;
    public EventOnGetFactorDetailsResponse(ArrayList<FactorDetailResponse> factorDetailsResponse) {
        this.factorDetailsResponse = factorDetailsResponse;
    }
    public ArrayList<FactorDetailResponse> getFactorDetailResponse() {
        return factorDetailsResponse;
    }
}
