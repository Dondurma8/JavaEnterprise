package org.example.jdbc.orm.manytomany.api;

import org.example.jdbc.orm.manytomany.entities.Ingredient;

import java.util.List;

public interface IngredientDAO extends DAO<Ingredient, Long> {
    List<Ingredient> find(String name);
    List<Ingredient> caloriesFind(double from, double to);
}