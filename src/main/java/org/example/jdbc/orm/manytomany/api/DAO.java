package org.example.jdbc.orm.manytomany.api;

import org.example.jdbc.orm.manytomany.entities.Entity;
import java.util.List;

public interface DAO<E extends Entity<K>, K> {
    E save(E entity);
    E update(E entity);
    E delete(E entity);
    E find(K id);
    List<E> findAll();
}
