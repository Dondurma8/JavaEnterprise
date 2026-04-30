package org.example.jdbc.orm.manytomany.api;

import org.example.jdbc.orm.manytomany.entities.Dish;
import org.example.jdbc.orm.manytomany.view.DishTypes;

import java.sql.Date;
import java.util.List;

public interface DishDAO extends DAO<Dish, Long> {
    List<Dish> find(DishTypes type);
    List<Dish> find(DishTypes type, double from, double to);
    List<Dish> findNew(DishTypes types, Date date);
}
