package org.example.jdbc.orm.manytomany.impl.jdbc;

import org.example.jdbc.orm.manytomany.entities.Ingredient;
import org.example.jdbc.orm.manytomany.api.IngredientDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAOImpl implements IngredientDAO {
    private final Connection connection;
    public IngredientDAOImpl(Connection connection){
        this.connection = connection;
    }
    @Override
    public List<Ingredient> find(String name) {
        List<Ingredient> ingredients = new ArrayList<>();
        String findByName = "SELECT * FROM ingredients WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(findByName)) {
            stmt.setString(1, name);
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
        return new ArrayList<>();
    }
    @Override
    public List<Ingredient> caloriesFind(double from, double to) {
        List<Ingredient> ingredients = new ArrayList<>();
        String caloriesFind = "SELECT * FROM ingredients WHERE calories BETWEEN ? AND ?";
        try (PreparedStatement stmt = connection.prepareStatement(caloriesFind)) {
            stmt.setDouble(1, from);
            stmt.setDouble(2, to);
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
        return new ArrayList<>();
    }
    @Override
    public Ingredient save(Ingredient ingredient) {
        String save = "INSERT INTO ingredients (name, amount, type, calories) VALUES (?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(save, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, ingredient.getName());
            statement.setInt(2, ingredient.getAmount());
            statement.setString(3, ingredient.getType());
            statement.setDouble(4, ingredient.getCalories());
            int inserted = statement.executeUpdate();
            if(inserted == 0)
                throw new SQLException();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next())
                ingredient.setId(resultSet.getLong(1));
            else
                return null;
            return ingredient;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Ingredient update(Ingredient ingredient) {
        String update = "UPDATE ingredients SET name = ?, amount = ?, type = ?, calories = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(update)) {
            stmt.setString(1, ingredient.getName());
            stmt.setInt(2, ingredient.getAmount());
            stmt.setString(3, ingredient.getType());
            stmt.setDouble(4, ingredient.getCalories());
            stmt.setLong(5, ingredient.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0 ? ingredient : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Ingredient delete(Ingredient ingredient) {
        String delete = "DELETE FROM ingredients WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(delete)) {
            stmt.setLong(1, ingredient.getId());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0 ? ingredient : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Ingredient find(Long id) {
        String find = "SELECT * FROM ingredients WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(find)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return new Ingredient(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getInt("amount"), resultSet.getString("type"),
                        resultSet.getDouble("calories"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        String findAll = "SELECT * FROM ingredients";
        try (PreparedStatement stmt = connection.prepareStatement(findAll)){
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                ingredients.add(new Ingredient(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getInt("amount"), resultSet.getString("type"),
                        resultSet.getDouble("calories")));
            }
            return ingredients;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}