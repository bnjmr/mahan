package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.model.ModelRegisterCustomerResponse;

/**
 * Created by FCC on 11/7/2017.
 */

public class EventOnSuccessRegisterCustomer {

    ModelRegisterCustomerResponse customerResponse;

    public ModelRegisterCustomerResponse getCustomerResponse() {
        return customerResponse;
    }

    public void setCustomerResponse(ModelRegisterCustomerResponse customerResponse) {
        this.customerResponse = customerResponse;
    }

    public EventOnSuccessRegisterCustomer(ModelRegisterCustomerResponse customerResponse) {

        this.customerResponse = customerResponse;
    }
}
