package org.example.jdbc.orm.manytomany.api;

import org.example.jdbc.orm.manytomany.entities.Entity;

import java.util.List;

public interface Service<E extends Entity<K>, K>{
    E save(E e);
    E update(E e);
    E delete(E e);
    E find(K id);
    List<E> findAll();
}