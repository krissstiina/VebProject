package veb.cinema.demo.dto;

import java.io.Serializable;

public class TicketDto implements Serializable {
    private int id;
    private int sessionId;
    private int userId;

    protected TicketDto(){}

    public TicketDto(int id, int sessionId,int userId){
        this.id = id;
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

}
