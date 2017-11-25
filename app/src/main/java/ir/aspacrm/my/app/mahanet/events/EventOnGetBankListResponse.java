package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.gson.LoadBanksResponse;

/**
 * Created by Microsoft on 5/23/2016.
 */
public class EventOnGetBankListResponse {
    LoadBanksResponse[] bankList;
    public EventOnGetBankListResponse(LoadBanksResponse[] response) {
        bankList = response;
    }
    public LoadBanksResponse[] getBankList() {
        return bankList;
    }
}
