package org.example.jdbc.orm.manytomany.impl.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.jdbc.orm.manytomany.api.IngredientDAO;
import org.example.jdbc.orm.manytomany.api.Service;
import org.example.jdbc.orm.manytomany.entities.Ingredient;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class IngredientServiceImpl implements Service<Ingredient, Long> {
    private BasicDataSource bds;
    public IngredientServiceImpl(){
        Properties properties = new Properties();
        try(InputStream inputStream = DishServiceImpl.class.getClassLoader().getResourceAsStream("storageProperties.properties")){
            properties.load(inputStream);
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String driver = properties.getProperty("driver");
            String poolSize = properties.getProperty("poolSize");
            int size = Integer.parseInt(poolSize);
            String maxPoolSize = properties.getProperty("maxPoolSize");
            int maxSize = Integer.parseInt(maxPoolSize);
            bds = new BasicDataSource();
            bds.setUrl(url);
            bds.setUsername(user);
            bds.setPassword(password);
            bds.setDriverClassName(driver);
            bds.setInitialSize(size);
            bds.setMaxTotal(maxSize);
        }
        catch (IOException e){
            throw new RuntimeException("Error loading database properties", e);
        }
    }
    public void close(){
        try{
            bds.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public List<Ingredient> findByName(String name){
        try(Connection connection = bds.getConnection()){
            IngredientDAO ingredientDAO = new IngredientDAOImpl(connection);
            List<Ingredient> ingredients = ingredientDAO.find(name);
            if(ingredients.isEmpty()){
                System.out.println("No ingredients found.");
                return new ArrayList<>();
            }
            return ingredients;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public List<Ingredient> findByCalories(double from, double to){
        try(Connection connection = bds.getConnection()){
            IngredientDAO ingredientDAO = new IngredientDAOImpl(connection);
            List<Ingredient> ingredients = ingredientDAO.caloriesFind(from, to);
            if(ingredients.isEmpty()){
                System.out.println("No ingredients found.");
                return new ArrayList<>();
            }
            return ingredients;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    @Override
    public Ingredient save(Ingredient ingredient) {
        try(Connection connection = bds.getConnection()){
            connection.setAutoCommit(false);
            IngredientDAO ingredientDAO = new IngredientDAOImpl(connection);
            Ingredient savedIngredient = ingredientDAO.save(ingredient);
            if(savedIngredient == null){
                connection.rollback();
                return null;
            }
            connection.commit();
            return savedIngredient;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Ingredient update(Ingredient ingredient) {
        try(Connection connection = bds.getConnection()){
            connection.setAutoCommit(false);
            IngredientDAO ingredientDAO = new IngredientDAOImpl(connection);
            Ingredient updatedIngredient = ingredientDAO.update(ingredient);
            if(updatedIngredient == null){
                connection.rollback();
                return null;
            }
            connection.commit();
            return updatedIngredient;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Ingredient delete(Ingredient ingredient) {
        try(Connection connection = bds.getConnection()){
            connection.setAutoCommit(false);
            IngredientDAO ingredientDAO = new IngredientDAOImpl(connection);
            Ingredient deletedIngredient = ingredientDAO.delete(ingredient);
            if(deletedIngredient == null){
                connection.rollback();
                return null;
            }
            connection.commit();
            return deletedIngredient;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Ingredient find(Long id) {
        try(Connection connection = bds.getConnection()){
            IngredientDAO ingredientDAO = new IngredientDAOImpl(connection);
            Ingredient foundIngredient = ingredientDAO.find(id);
            if(foundIngredient == null){
                System.out.println("Ingredient with id " + id + " not found.");
                return null;
            }
            return foundIngredient;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Ingredient> findAll() {
        try(Connection connection = bds.getConnection()){
            IngredientDAO ingredientDAO = new IngredientDAOImpl(connection);
            List<Ingredient> ingredients = ingredientDAO.findAll();
            if(ingredients.isEmpty()){
                System.out.println("No ingredients found.");
                return new ArrayList<>();
            }
            return ingredients;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
