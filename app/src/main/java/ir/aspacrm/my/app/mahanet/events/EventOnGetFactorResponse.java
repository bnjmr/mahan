package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.model.Factor;

import java.util.List;

public class EventOnGetFactorResponse {
    List<Factor> factorResponses;
    public EventOnGetFactorResponse(List<Factor> factorResponses) {
        this.factorResponses = factorResponses;
    }
    public List<Factor> getFactorResponses() {
        return factorResponses;
    }
}
