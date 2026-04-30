package org.example.jdbc.orm.manytomany.view;

import org.example.jdbc.orm.manytomany.impl.jdbc.DishServiceImpl;
import org.example.jdbc.orm.manytomany.entities.Dish;
import org.example.jdbc.orm.manytomany.entities.Ingredient;
import org.example.jdbc.orm.manytomany.impl.jdbc.IngredientServiceImpl;

import java.util.*;

public class MainRecipe {
    public static void main(String[] args) {
        DishServiceImpl service = new DishServiceImpl();
        IngredientServiceImpl service1 = new IngredientServiceImpl();
//        Dish dish = new Dish("olivie", "salad with mayonnaise", "salad", 6.5);
//        Ingredient ingredient = new Ingredient("mayonnaise", 20, "sauce", 25.3);
//        Ingredient ingredient1 = new Ingredient("cucumber", 30, "vegetable", 4.8);
//        Ingredient ingredient2 = new Ingredient("chicken", 10, "meat", 13.6);
        Ingredient ingredient = new Ingredient(8, "butter", 3, "dairy", 30.8);
        Ingredient ingredient1 = new Ingredient(9, "sugar", 10, "supplement", 25.1);
        Ingredient ingredient2 = new Ingredient(3, "eggs", 50, "dairy", 15.9);
        Set<Ingredient> ingredientSet = new HashSet<>();
        ingredientSet.add(ingredient);
        ingredientSet.add(ingredient1);
        ingredientSet.add(ingredient2);
        Dish dish = new Dish("cake", "delicious homemade cake", DishTypes.DESSERTS, 20.5, ingredientSet);
        for(Dish dish1 : service.findAll()){
            System.out.println(dish1);
        }
        //service1.close();
        service.close();
    }
}
