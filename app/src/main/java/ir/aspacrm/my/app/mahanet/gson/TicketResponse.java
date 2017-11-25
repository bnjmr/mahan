package ir.aspacrm.my.app.mahanet.gson;

import ir.aspacrm.my.app.mahanet.enums.EnumTicketPriority;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class TicketResponse {
    public long code;
    public String Date = "";
    public String Title = "" ;
    public String State = "";
    public String PriorityName = "";
    public String Priority = EnumTicketPriority.KAM;
    public int Open = 0;
    public int CountUnread = 0;
}
