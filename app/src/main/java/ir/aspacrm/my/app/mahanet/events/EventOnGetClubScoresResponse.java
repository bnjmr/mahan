package ir.aspacrm.my.app.mahanet.events;

import ir.aspacrm.my.app.mahanet.model.ClubScore;

import java.util.List;
public class EventOnGetClubScoresResponse {
    List<ClubScore> clubScoresResponse;
    public EventOnGetClubScoresResponse(List<ClubScore> clubScoresResponse) {
        this.clubScoresResponse = clubScoresResponse;
    }
    public List<ClubScore> getClubScoresResponse() {
        return clubScoresResponse;
    }
}
