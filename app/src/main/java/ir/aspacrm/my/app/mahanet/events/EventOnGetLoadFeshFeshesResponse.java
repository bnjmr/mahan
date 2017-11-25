package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.model.Feshfeshe;

import java.util.List;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetLoadFeshFeshesResponse {
    List<Feshfeshe> feshfesheList;
    public EventOnGetLoadFeshFeshesResponse(List<Feshfeshe> feshfesheList) {
        this.feshfesheList = feshfesheList;
    }
    public List<Feshfeshe> getFeshfesheList() {
        return feshfesheList;
    }
}
