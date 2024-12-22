package veb.cinema.demo.repositories.generic;

public interface DeleteRepository<T> {
    void delete(T entity);
    void deleteById(int id);
}
