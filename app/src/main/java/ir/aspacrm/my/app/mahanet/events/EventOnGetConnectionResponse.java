package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.model.Connection;

import java.util.List;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetConnectionResponse {
    List<Connection> getConnectionsResponses;
    public EventOnGetConnectionResponse(List<Connection> getConnectionsResponses) {
        this.getConnectionsResponses = getConnectionsResponses;
    }
    public List<Connection> getGetConnectionsResponses() {
        return getConnectionsResponses;
    }
}
