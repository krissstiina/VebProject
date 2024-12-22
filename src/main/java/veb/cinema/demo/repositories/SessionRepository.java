package veb.cinema.demo.repositories;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import veb.cinema.demo.models.MySession;
import veb.cinema.demo.repositories.generic.CreateRepository;
import veb.cinema.demo.repositories.generic.DeleteRepository;
import veb.cinema.demo.repositories.generic.ReadRepository;
import veb.cinema.demo.repositories.generic.UpdateRepository;

import java.util.List;

@Repository
public interface SessionRepository extends ReadRepository<MySession>, CreateRepository<MySession>, UpdateRepository<MySession>, DeleteRepository<MySession> {
    
}
