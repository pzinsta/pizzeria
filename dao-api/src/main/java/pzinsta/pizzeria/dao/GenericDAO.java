package pzinsta.pizzeria.dao;

import java.util.List;

public interface GenericDAO <T, ID> {
    T findById(ID id);
    List<T> findAll();
    Long getCount();
    void saveOrUpdate(T entity);
    void delete(T entity);
}
