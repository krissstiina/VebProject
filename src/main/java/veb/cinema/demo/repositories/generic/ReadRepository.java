package veb.cinema.demo.repositories.generic;

import java.util.Optional;

import java.util.List;

public interface ReadRepository<T> {
    Optional<T> findById(int id);
    List<T> findAll();
}
