package org.example.jdbc.orm.manytomany.impl.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.jdbc.orm.manytomany.view.DishTypes;
import org.example.jdbc.orm.manytomany.api.DishDAO;
import org.example.jdbc.orm.manytomany.api.Service;
import org.example.jdbc.orm.manytomany.entities.Dish;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

public class DishServiceImpl implements Service<Dish, Long> {
    private BasicDataSource bds;
    public DishServiceImpl(){
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
    @Override
    public Dish save(Dish dish) {
        try(Connection connection = bds.getConnection()){
            connection.setAutoCommit(false);
            DishDAO dishDAO = new DishDAOImpl(connection);
            Dish savedDish = dishDAO.save(dish);
            if(savedDish == null){
                connection.rollback();
                return null;
            }
            connection.commit();
            return savedDish;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Dish update(Dish dish) {
        try(Connection connection = bds.getConnection()){
            connection.setAutoCommit(false);
            DishDAO dishDAO = new DishDAOImpl(connection);
            Dish updatedDish = dishDAO.update(dish);
            if(updatedDish == null){
                connection.rollback();
                return null;
            }
            connection.commit();
            return updatedDish;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Dish delete(Dish dish) {
        try(Connection connection = bds.getConnection()){
            connection.setAutoCommit(false);
            DishDAO dishDAO = new DishDAOImpl(connection);
            Dish deletedDish = dishDAO.delete(dish);
            if(deletedDish == null){
                connection.rollback();
                return null;
            }
            connection.commit();
            return deletedDish;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Dish find(Long id) {
        try(Connection connection = bds.getConnection()){
            DishDAO dishDAO = new DishDAOImpl(connection);
            Dish found = dishDAO.find(id);
            if(found == null){
                System.out.println("Dish with id " + id + " not found.");
                return null;
            }
            return found;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Dish> findAll() {
        try(Connection connection = bds.getConnection()){
            DishDAO dishDAO = new DishDAOImpl(connection);
            List<Dish> dishes = dishDAO.findAll();
            if(dishes.isEmpty()){
                System.out.println("No dishes found.");
                return new ArrayList<>();
            }
            return dishes;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public List<Dish> findByType(DishTypes type) {
        try(Connection connection = bds.getConnection()){
            DishDAO dishDAO = new DishDAOImpl(connection);
            List<Dish> dishes = dishDAO.find(type);
            if(dishes.isEmpty()){
                System.out.println("No dishes found.");
                return new ArrayList<>();
            }
            return dishes;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public List<Dish> formMenu(DishTypes type, double from, double to){
        try(Connection connection = bds.getConnection()){
            DishDAO dishDAO = new DishDAOImpl(connection);
            List<Dish> dishes = dishDAO.find(type, from, to);
            if(dishes.isEmpty()){
                System.out.println("No dishes found.");
                return new ArrayList<>();
            }
            return dishes;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public List<Dish> findNew(DishTypes types, Date date) {
        try(Connection connection = bds.getConnection()){
            DishDAO dishDAO = new DishDAOImpl(connection);
            List<Dish> dishes = dishDAO.findNew(types, date);
            if(dishes.isEmpty()){
                System.out.println("No dishes found.");
                return new ArrayList<>();
            }
            return dishes;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}