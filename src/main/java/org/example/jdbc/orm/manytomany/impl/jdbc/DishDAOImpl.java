package org.example.jdbc.orm.manytomany.impl.jdbc;

import org.example.jdbc.orm.manytomany.view.DishTypes;
import org.example.jdbc.orm.manytomany.api.DishDAO;
import org.example.jdbc.orm.manytomany.entities.Dish;
import org.example.jdbc.orm.manytomany.entities.Ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DishDAOImpl implements DishDAO{
    private Connection connection;
    public DishDAOImpl(Connection connection){
        this.connection = connection;
    }
    @Override
    public List<Dish> find(DishTypes type) {
        List<Dish> dishes = new ArrayList<>();
        String find = "SELECT * FROM dishes WHERE type = ?";
        try(PreparedStatement stmt = connection.prepareStatement(find)){
            stmt.setString(1, type.toString());
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                Dish dish = new Dish(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getString("description"),
                        DishTypes.valueOf(resultSet.getString("type").toUpperCase()),
                        resultSet.getDouble("price"));
                dish.setIngredients(getIngredientsByDishId(dish.getId()));
                dishes.add(dish);
            }
            return dishes;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    @Override
    public List<Dish> find(DishTypes type, double from, double to) {
        List<Dish> dishes = new ArrayList<>();
        String find = "SELECT * FROM dishes WHERE type = ? AND price BETWEEN ? AND ?";
        try (PreparedStatement stmt = connection.prepareStatement(find)) {
            stmt.setString(1, type.toString());
            stmt.setDouble(2, from);
            stmt.setDouble(3, to);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Dish dish = new Dish(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getString("description"),
                        DishTypes.valueOf(resultSet.getString("type").toUpperCase()),
                        resultSet.getDouble("price"));
                dish.setIngredients(getIngredientsByDishId(dish.getId()));
                dishes.add(dish);
            }
            return dishes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    private Set<Ingredient> getIngredientsByDishId(Long dishId) {
        Set<Ingredient> ingredients = new HashSet<>();
        String query = "SELECT i.id, i.name, i.amount, i.type, i.calories FROM ingredients i " +
                "JOIN recipes r ON i.id = r.ingredient_id WHERE r.dish_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, dishId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                ingredients.add(new Ingredient(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getInt("amount"), resultSet.getString("type"),
                        resultSet.getDouble("calories")));
            }
            return ingredients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }
    @Override
    public Dish save(Dish dish) {
        String save = "INSERT INTO dishes (name, description, type, price) VALUES (?, ?, ?, ?)";
        String saveIngredients = "INSERT INTO recipes (dish_id, ingredient_id, amount) VALUES (?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(save, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement ingredientStatement = connection.prepareStatement(saveIngredients)){
            stmt.setString(1, dish.getName());
            stmt.setString(2, dish.getDescription());
            stmt.setString(3, dish.getType().toString());
            stmt.setDouble(4, dish.getPrice());
            int inserted = stmt.executeUpdate();
            if(inserted == 0)
                throw new SQLException();
            ResultSet resultSet = stmt.getGeneratedKeys();
            if(resultSet.next())
                dish.setId(resultSet.getLong(1));
            else
                return null;
            for (Ingredient ingredient : dish.getIngredients()) {
                ingredientStatement.setLong(1, dish.getId());
                ingredientStatement.setLong(2, ingredient.getId());
                ingredientStatement.setInt(3, ingredient.getAmount());
                ingredientStatement.addBatch();
            }
            ingredientStatement.executeBatch();
            return dish;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Dish update(Dish dish) {
        String updateQuery = "UPDATE dishes SET name = ?, description = ?, type = ?, price = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            stmt.setString(1, dish.getName());
            stmt.setString(2, dish.getDescription());
            stmt.setString(3, dish.getType().toString());
            stmt.setDouble(4, dish.getPrice());
            stmt.setLong(5, dish.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0 ? dish : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Dish delete(Dish dish) {
        String deleteQuery = "DELETE FROM dishes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setLong(1, dish.getId());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0 ? dish : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public Dish find(Long id) {
        String findQuery = "SELECT * FROM dishes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(findQuery)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                Dish dish = new Dish(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getString("description"),
                        DishTypes.valueOf(resultSet.getString("type").toUpperCase()),
                        resultSet.getDouble("price"));
                dish.setIngredients(getIngredientsByDishId(dish.getId()));
                return dish;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Dish> findAll() {
        List<Dish> dishes = new ArrayList<>();
        String findAllQuery = "SELECT * FROM dishes";
        try (PreparedStatement stmt = connection.prepareStatement(findAllQuery)){
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Dish dish = new Dish(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getString("description"),
                        DishTypes.valueOf(resultSet.getString("type").toUpperCase()),
                        resultSet.getDouble("price"));
                dish.setIngredients(getIngredientsByDishId(dish.getId()));
                dishes.add(dish);
            }
            return dishes;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    @Override
    public List<Dish> findNew(DishTypes types, Date date){
        List<Dish> dishes = new ArrayList<>();
        String findNew = "SELECT * FROM dishes WHERE type = ? AND date > ?";
        try (PreparedStatement stmt = connection.prepareStatement(findNew)) {
            stmt.setString(1, types.toString());
            stmt.setDate(2, new Date(date.getTime()));
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Dish dish = new Dish(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getString("description"),
                        DishTypes.valueOf(resultSet.getString("type").toUpperCase()),
                        resultSet.getDouble("price"));
                dish.setIngredients(getIngredientsByDishId(dish.getId()));
                dishes.add(dish);
            }
            return dishes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
