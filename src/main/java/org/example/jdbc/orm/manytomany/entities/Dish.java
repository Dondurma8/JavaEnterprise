package org.example.jdbc.orm.manytomany.entities;

import org.example.jdbc.orm.manytomany.view.DishTypes;

import java.util.HashSet;
import java.util.Set;

public class Dish extends Entity<Long> {
    private String name;
    private String description;
    private DishTypes type;
    private double price;
    private Set<Ingredient> ingredients = new HashSet<>();

    public Dish(long id, String name, String description, DishTypes type, double price){
        super(id);
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
    }
    public Dish(long id){
        super(id);
    }
    public Dish(String name, String description, DishTypes type, double price){
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
    }
    public Dish(String name, String description, DishTypes type, double price, Set<Ingredient> ingredients){
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.ingredients = ingredients;
    }
    @Override
    public String toString() {
        return "Dish{" + name + ", " + type + ", " + price + ", " + description + ". " + ingredients + "}";
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public DishTypes getType() {
        return type;
    }
    public void setType(DishTypes type) {
        this.type = type;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Set<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
