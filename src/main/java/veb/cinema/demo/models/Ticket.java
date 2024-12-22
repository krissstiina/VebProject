package veb.cinema.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket extends BaseEntity{
    private MySession session;
    private User user;

    public Ticket(){}

    public Ticket(MySession session,User user){
        this.session = session;
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id")
    public MySession getSession() {
        return session;
    }

    public void setSession(MySession session) {
        this.session = session;
    }

}

