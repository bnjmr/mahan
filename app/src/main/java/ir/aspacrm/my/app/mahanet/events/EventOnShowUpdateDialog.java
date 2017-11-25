package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.model.getUpdate;

/**
 * Created by FCC on 10/25/2017.
 */

public class EventOnShowUpdateDialog {

    getUpdate update;

    public getUpdate getUpdate() {
        return update;
    }

    public void setUpdate(getUpdate update) {
        this.update = update;
    }

    public EventOnShowUpdateDialog(getUpdate update) {

        this.update = update;
    }
}
