package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.gson.ChargeOnlineMainItemResponse;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetChargeOnlineMainItem {
    ChargeOnlineMainItemResponse chargeOnlineMainItemResponse;

    public EventOnGetChargeOnlineMainItem(ChargeOnlineMainItemResponse chargeOnlineMainItemResponse) {
        this.chargeOnlineMainItemResponse = chargeOnlineMainItemResponse;
    }

    public ChargeOnlineMainItemResponse getChargeOnlineMainItemResponse() {
        return chargeOnlineMainItemResponse;
    }
}
