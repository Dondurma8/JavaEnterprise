package org.example.jdbc.orm.onetoone;

import java.util.List;

public interface EntityController<T extends Entity>{
    T save(T entity);
    T update(T entity);
    T delete(T entity);
    T findById(Object id);
    List<T> findAll();
}
