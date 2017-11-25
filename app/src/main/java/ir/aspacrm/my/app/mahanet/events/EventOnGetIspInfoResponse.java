package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.gson.GetIspInfoResponse;

/**
 * Created by Microsoft on 3/5/2016.
 */
public class EventOnGetIspInfoResponse {
    GetIspInfoResponse ispInfo;
    public EventOnGetIspInfoResponse(GetIspInfoResponse ispInfo) {
        this.ispInfo = ispInfo;
    }
    public GetIspInfoResponse getIspInfo() {
        return ispInfo;
    }
}
