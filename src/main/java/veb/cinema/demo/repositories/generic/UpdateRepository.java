package veb.cinema.demo.repositories.generic;

public interface UpdateRepository<T> {
    void update(T entity);
}
