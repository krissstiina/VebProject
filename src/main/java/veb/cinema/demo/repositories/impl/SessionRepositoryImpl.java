package veb.cinema.demo.repositories.impl;

import org.springframework.stereotype.Repository;

import veb.cinema.demo.models.MySession;
import veb.cinema.demo.repositories.SessionRepository;

@Repository
public class SessionRepositoryImpl extends BaseRepositoryImpl<MySession> implements SessionRepository {

    public SessionRepositoryImpl() {
        super(MySession.class);
    }

}
