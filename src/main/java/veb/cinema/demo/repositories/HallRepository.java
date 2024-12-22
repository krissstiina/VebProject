package veb.cinema.demo.repositories;

import org.springframework.stereotype.Repository;

import veb.cinema.demo.models.Hall;
import veb.cinema.demo.repositories.generic.CreateRepository;
import veb.cinema.demo.repositories.generic.ReadRepository;

@Repository
public interface HallRepository extends ReadRepository<Hall>, CreateRepository<Hall> {

}
