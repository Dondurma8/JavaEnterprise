package org.example.jdbc.orm.manytomany.entities;

public class Ingredient extends Entity<Long> {
    private String name;
    private int amount;
    private String type;
    private double calories;

    public Ingredient(long id, String name, int amount, String type, double calories) {
        super(id);
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.calories = calories;
    }
    public Ingredient(long id){
        super(id);
    }
    public Ingredient(String name, int amount, String type, double calories){
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.calories = calories;
    }
    @Override
    public String toString() {
        return "Ingredient{" + name + ", amount " + amount + ", " + type + ", " + calories + "cal" + "}";
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getCalories() {
        return calories;
    }
    public void setCalories(double calories) {
        this.calories = calories;
    }
}