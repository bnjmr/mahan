package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.model.Payment;

import java.util.List;

/**
 * Created by Microsoft on 3/10/2016.
 */
public class EventOnGetPaymentResponse {
    List<Payment> payments;
    public EventOnGetPaymentResponse(List<Payment> payments) {
        this.payments = payments;
    }
    public List<Payment> getPayments() {
        return payments;
    }
}
