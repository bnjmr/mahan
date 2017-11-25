package ir.aspacrm.my.app.mahanet.events;


import ir.aspacrm.my.app.mahanet.gson.TrafficSplitNotMainModel;

/**
 * Created by FCC on 10/10/2017.
 */

public class EventOnLoadTrafficSplitNotMain {

    TrafficSplitNotMainModel[] trafficSplitNotMainModel;

    public EventOnLoadTrafficSplitNotMain(TrafficSplitNotMainModel[] trafficSplitNotMainModel) {
        this.trafficSplitNotMainModel = trafficSplitNotMainModel;
    }

    public TrafficSplitNotMainModel[] getTrafficSplitNotMainModel() {

        return trafficSplitNotMainModel;
    }

    public void setTrafficSplitNotMainModel(TrafficSplitNotMainModel[] trafficSplitNotMainModel) {
        this.trafficSplitNotMainModel = trafficSplitNotMainModel;
    }
}
