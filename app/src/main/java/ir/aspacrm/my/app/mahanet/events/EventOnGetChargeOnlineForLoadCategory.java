package ir.aspacrm.my.app.mahanet.events;

import java.util.List;

import ir.aspacrm.my.app.mahanet.gson.ChargeOnlineCategory;

/**
 * Created by HaMiD on 3/14/2017.
 */

public class EventOnGetChargeOnlineForLoadCategory {
    List<ChargeOnlineCategory> chargeOnlineCategoryList;

    public EventOnGetChargeOnlineForLoadCategory(List<ChargeOnlineCategory> chargeOnlineCategoryList) {
        this.chargeOnlineCategoryList = chargeOnlineCategoryList;
    }

    public List<ChargeOnlineCategory> getChargeOnlineCategoryList() {
        return chargeOnlineCategoryList;
    }
}
