package org.example.jdbc.orm.onetoone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EngineController implements EntityController{
    private final Connection connection;

    public EngineController(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Engine save(Entity entity) {
        Engine engine = (Engine) entity;
        String query = "INSERT INTO engines (brand, type, power, volume) VALUES ('" + engine.getBrand() +
                "', '" + engine.getType() + "', " + engine.getPower() + ", " + engine.getVolume() + ");";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int inserted = statement.executeUpdate();
            if (inserted == 0)
                throw new SQLException();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                entity.setId(id);
                return engine;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Engine update(Entity entity) {
        Engine engine = (Engine) entity;
        String query = "UPDATE engines SET brand = '" + engine.getBrand() + "', type = '" + engine.getType() + "', power = " +
        engine.getPower() + ", volume = " + engine.getVolume() + " WHERE id = " + engine.getId() + ";";
        try{
            Statement statement = connection.createStatement();
            int updated = statement.executeUpdate(query);
            if(updated == 0)
                throw new SQLException();
            return engine;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Engine delete(Entity entity) {
        Engine engine = (Engine) entity;
        String query = "DELETE FROM engines WHERE id = " + engine.getId() + ";";
        try{
            Statement statement = connection.createStatement();
            int deleted = statement.executeUpdate(query);
            if(deleted == 0)
                throw new SQLException();
            return engine;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Engine findById(Object id) {
        String query = "SELECT * FROM engines WHERE id = " + id + ";";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                return new Engine(resultSet.getLong("id"), resultSet.getString("brand"), resultSet.getString("type"),
                        resultSet.getInt("power"), resultSet.getDouble("volume"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Engine> findAll() {
        List<Engine> engines = new ArrayList<>();
        String query = "SELECT * FROM engines;";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                engines.add(new Engine(resultSet.getLong("id"), resultSet.getString("brand"),
                        resultSet.getString("type"), resultSet.getInt("power"), resultSet.getDouble("volume")));
            }
            return engines;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
