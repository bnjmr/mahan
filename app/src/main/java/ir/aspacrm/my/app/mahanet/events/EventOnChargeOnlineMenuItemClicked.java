package ir.aspacrm.my.app.mahanet.events;

public class EventOnChargeOnlineMenuItemClicked {
    int whichMenuItem;

    public EventOnChargeOnlineMenuItemClicked(int whichMenuItem) {
        this.whichMenuItem = whichMenuItem;
    }

    public int getWhichMenuItem() {
        return whichMenuItem;
    }
}
