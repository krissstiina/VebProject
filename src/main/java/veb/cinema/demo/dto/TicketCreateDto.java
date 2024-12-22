package veb.cinema.demo.dto;

public class TicketCreateDto {
    private int sessionId;
    private int userId;

    protected TicketCreateDto(){}

    public TicketCreateDto( int sessionId, int userId){
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
