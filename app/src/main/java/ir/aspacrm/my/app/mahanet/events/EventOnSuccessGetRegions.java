package ir.aspacrm.my.app.mahanet.events;

import java.util.List;

import ir.aspacrm.my.app.mahanet.model.ModelRegions;

/**
 * Created by FCC on 11/6/2017.
 */

public class EventOnSuccessGetRegions {
    List<ModelRegions> modelRegionsList;

    public EventOnSuccessGetRegions(List<ModelRegions> modelRegionsList) {
        this.modelRegionsList = modelRegionsList;
    }

    public List<ModelRegions> getModelRegionsList() {

        return modelRegionsList;
    }

    public void setModelRegionsList(List<ModelRegions> modelRegionsList) {
        this.modelRegionsList = modelRegionsList;
    }
}
