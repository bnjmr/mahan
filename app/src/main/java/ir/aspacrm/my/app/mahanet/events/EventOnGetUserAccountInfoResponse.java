package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.model.Account;

public class EventOnGetUserAccountInfoResponse {
    Account accountInfo;
    public EventOnGetUserAccountInfoResponse(Account accountInfo) {
        this.accountInfo = accountInfo;
    }
    public Account getAccountInfo() {
        return accountInfo;
    }
}
