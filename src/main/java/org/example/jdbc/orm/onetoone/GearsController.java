package org.example.jdbc.orm.onetoone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GearsController implements EntityController{
    private final Connection connection;
    public GearsController (Connection connection){
        this.connection = connection;
    }
    @Override
    public Transmission save(Entity entity) {
        Transmission transmission = (Transmission) entity;
        String query = "INSERT INTO transmissions (brand, type, speed) VALUES ('" + transmission.getBrand() +
                "', '" + transmission.getType() + "', " + transmission.getSpeed() + ");";
        try(PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
            int inserted = statement.executeUpdate();
            if (inserted == 0)
                throw new SQLException();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                entity.setId(id);
                return transmission;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Transmission update(Entity entity) {
        Transmission transmission = (Transmission) entity;
        String query = "UPDATE transmissions SET brand = '" + transmission.getBrand() + "', type = '" +
                transmission.getType() + "', speed = " + transmission.getSpeed() + " WHERE id = " + transmission.getId() + ";";
        try {
            Statement statement = connection.createStatement();
            int updated = statement.executeUpdate(query);
            if (updated == 0)
                throw new SQLException();
            return transmission;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Transmission delete(Entity entity) {
        Transmission transmission = (Transmission) entity;
        String query = "DELETE FROM transmissions WHERE id = " + transmission.getId() + ";";
        try {
            Statement statement = connection.createStatement();
            int deleted = statement.executeUpdate(query);
            if (deleted == 0)
                throw new SQLException();
            return transmission;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Transmission findById(Object id) {
        String query = "SELECT * FROM transmissions WHERE id = " + id + ";";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return new Transmission(resultSet.getInt("id"), resultSet.getString("brand"),
                        resultSet.getString("type"), resultSet.getInt("speed"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Transmission> findAll() {
        List<Transmission> transmissions = new ArrayList<>();
        String query = "SELECT * FROM transmissions;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                transmissions.add(new Transmission(resultSet.getInt("id"), resultSet.getString("brand"),
                        resultSet.getString("type"), resultSet.getInt("speed")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return transmissions;
    }
}
