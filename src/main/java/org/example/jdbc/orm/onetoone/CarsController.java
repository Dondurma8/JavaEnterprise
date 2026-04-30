package org.example.jdbc.orm.onetoone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarsController implements EntityController{
    private final Connection connection;
    public CarsController(Connection connection){
        this.connection = connection;
    }
    @Override
    public Car save(Entity entity) {
        Car car = (Car) entity;
        try {
            Engine engine = car.getEngine();
            if(engine != null){
                String engineQuery = "INSERT INTO engines (brand, type, power, volume) VALUES ('" + engine.getBrand() + "', '" +
                        engine.getType() + "', " + engine.getPower() + ", " + engine.getVolume() + ");";
                try(PreparedStatement statement1 = connection.prepareStatement(engineQuery, PreparedStatement.RETURN_GENERATED_KEYS)){
                    int inserted = statement1.executeUpdate();
                    if(inserted == 0) {
                        throw new SQLException("Failed to save Engine");
                    }
                    ResultSet resultSet = statement1.getGeneratedKeys();
                    if(resultSet.next()){
                        engine.setId(resultSet.getLong(1));
                    }
                }
            }
            Transmission transmission = car.getTransmission();
            if(transmission != null){
                String gearQuery = "INSERT INTO transmissions (brand, type, speed) VALUE ('" + transmission.getBrand() + "', '" +
                        transmission.getType() + "', " + transmission.getSpeed() + ");";
                try(PreparedStatement statement1 = connection.prepareStatement(gearQuery, PreparedStatement.RETURN_GENERATED_KEYS)){
                    int inserted = statement1.executeUpdate();
                    if(inserted == 0) {
                        throw  new SQLException("Failed to save Transmission");
                    }
                    ResultSet resultSet = statement1.getGeneratedKeys();
                    if(resultSet.next()){
                        transmission.setId(resultSet.getInt(1));
                    }
                }
            }
            String query = "INSERT INTO cars (brand, model, year, engine_id) VALUES (?, ?, ?, ?)";
            try(PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
                statement.setString(1, car.getBrand());
                statement.setString(2, car.getModel());
                statement.setInt(3, car.getYear());
                if (engine != null) {
                    statement.setLong(4, (Long) engine.getId());
                } else {
                    statement.setNull(4, Types.BIGINT);
                }
                int inserted = statement.executeUpdate();
                if (inserted == 0) {
                    throw new SQLException("Failed to save Car");
                }
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    car.setId(resultSet.getLong(1));
                }
            }
            if(transmission != null){
                String carsGears = "INSERT INTO cars_gears (car_id, gear_id) VALUES (?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(carsGears)) {
                    statement.setLong(1, (Long) car.getId());
                    statement.setInt(2, (Integer) transmission.getId());
                    int inserted = statement.executeUpdate();
                    if (inserted == 0) {
                        throw new SQLException("Failed to save into cars_gears");
                    }
                }
            }
            return car;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Car update(Entity entity) {
        Car car = (Car) entity;
        try {
            Engine engine = car.getEngine();
            if (engine != null) {
                if (engine.getId() != null) {
                    String engineQuery = "UPDATE engines SET brand = ?, type = ?, power = ?, volume = ? WHERE id = ?";
                    try (PreparedStatement statement = connection.prepareStatement(engineQuery)) {
                        statement.setString(1, engine.getBrand());
                        statement.setString(2, engine.getType());
                        statement.setInt(3, engine.getPower());
                        statement.setDouble(4, engine.getVolume());
                        statement.setLong(5, (Long) engine.getId());
                        int updated = statement.executeUpdate();
                        if (updated == 0) {
                            throw new SQLException("Failed to update Engine");
                        }
                    }
                } else {
                    String engineInsertQuery = "INSERT INTO engines (brand, type, power, volume) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(engineInsertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        statement.setString(1, engine.getBrand());
                        statement.setString(2, engine.getType());
                        statement.setInt(3, engine.getPower());
                        statement.setDouble(4, engine.getVolume());
                        int inserted = statement.executeUpdate();
                        if (inserted == 0) {
                            throw new SQLException("Failed to insert new Engine");
                        }
                        ResultSet resultSet = statement.getGeneratedKeys();
                        if (resultSet.next()) {
                            engine.setId(resultSet.getLong(1));
                        }
                    }
                }
            }
            else{
                String deleteEngine = "DELETE FROM engines WHERE id = ?";
                try(PreparedStatement statement = connection.prepareStatement(deleteEngine)){
                    statement.setLong(1, (Long) engine.getId());
                    statement.executeUpdate();
                }
            }
            Transmission transmission = car.getTransmission();
            if (transmission != null) {
                if (transmission.getId() != null) {
                    String transmissionQuery = "UPDATE transmissions SET brand = ?, type = ?, speed = ? WHERE id = ?";
                    try (PreparedStatement statement = connection.prepareStatement(transmissionQuery)) {
                        statement.setString(1, transmission.getBrand());
                        statement.setString(2, transmission.getType());
                        statement.setInt(3, transmission.getSpeed());
                        statement.setInt(4, (Integer) transmission.getId());
                        int updated = statement.executeUpdate();
                        if (updated == 0) {
                            throw new SQLException("Failed to update Transmission");
                        }
                    }
                } else {
                    String transmissionInsertQuery = "INSERT INTO transmissions (brand, type, speed) VALUES (?, ?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(transmissionInsertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        statement.setString(1, transmission.getBrand());
                        statement.setString(2, transmission.getType());
                        statement.setInt(3, transmission.getSpeed());
                        int inserted = statement.executeUpdate();
                        if (inserted == 0) {
                            throw new SQLException("Failed to insert new Transmission");
                        }
                        ResultSet resultSet = statement.getGeneratedKeys();
                        if (resultSet.next()) {
                            transmission.setId(resultSet.getInt(1));
                        }
                    }
                }
                String carsGearsQuery = "REPLACE INTO cars_gears (car_id, gear_id) VALUES (?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(carsGearsQuery)) {
                    statement.setLong(1, (Long) car.getId());
                    statement.setInt(2, (Integer) transmission.getId());
                    int updated = statement.executeUpdate();
                    if (updated == 0) {
                        throw new SQLException("Failed to update cars_gears");
                    }
                }
            } else {
                String deleteCarsGearsQuery = "DELETE FROM cars_gears WHERE car_id = ?";
                try (PreparedStatement statement = connection.prepareStatement(deleteCarsGearsQuery)) {
                    statement.setLong(1, (Long) car.getId());
                    statement.executeUpdate();
                }
            }
            String carQuery = "UPDATE cars SET brand = ?, model = ?, year = ?, engine_id = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(carQuery)) {
                statement.setString(1, car.getBrand());
                statement.setString(2, car.getModel());
                statement.setInt(3, car.getYear());
                if (engine != null && engine.getId() != null) {
                    statement.setLong(4, (Long) engine.getId());
                } else {
                    statement.setNull(4, Types.BIGINT);
                }
                statement.setLong(5, (Long) car.getId());
                int updated = statement.executeUpdate();
                if (updated == 0) {
                    throw new SQLException("Failed to update Car");
                }
            }
            return car;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Car delete(Entity entity) {
        Car car = (Car) entity;
        try {
            Engine engine = car.getEngine();
            if(engine != null && engine.getId() != null){
                String deleteEngineQuery = "DELETE FROM engines WHERE id = ?";
                try(PreparedStatement statement = connection.prepareStatement(deleteEngineQuery)){
                    statement.setLong(1, (Long) engine.getId());
                    int deleted = statement.executeUpdate();
                    if(deleted == 0){
                        throw new SQLException("Failed to delete engine");
                    }
                }
            }
            Transmission transmission = car.getTransmission();
            if(transmission != null && transmission.getId() != null){
                String deleteGear = "DELETE FROM transmissions WHERE id = ?";
                try(PreparedStatement statement = connection.prepareStatement(deleteGear)){
                    statement.setInt(1, (Integer) transmission.getId());
                    int deleted = statement.executeUpdate();
                    if(deleted == 0){
                        throw new SQLException("Failed to delete gear");
                    }
                }
            }
            String deleteCarQuery = "DELETE FROM cars WHERE id = ?";
            try(PreparedStatement statement = connection.prepareStatement(deleteCarQuery)){
                statement.setLong(1, (Long) car.getId());
                int deleted = statement.executeUpdate();
                if (deleted == 0) {
                    throw new SQLException("Failed to delete car");
                }
            }
            return car;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Car findById(Object id) {
        Car car = null;
        try {
            String query = "SELECT c.id AS car_id, c.brand AS car_brand, c.model AS car_model, c.year AS car_year, " +
                    "e.id AS engine_id, e.brand AS engine_brand, e.type AS engine_type, e.power AS engine_power, e.volume AS engine_volume, " +
                    "t.id AS transmission_id, t.brand AS transmission_brand, t.type AS transmission_type, t.speed AS transmission_speed " +
                    "FROM cars c " +
                    "LEFT JOIN engines e ON c.engine_id = e.id " +
                    "LEFT JOIN cars_gears cg ON c.id = cg.car_id " +
                    "LEFT JOIN transmissions t ON cg.gear_id = t.id " +
                    "WHERE c.id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Engine engine = null;
                if (resultSet.getLong("engine_id") != 0) {
                    engine = new Engine(resultSet.getLong("engine_id"), resultSet.getString("engine_brand"),
                            resultSet.getString("engine_type"), resultSet.getInt("engine_power"),
                            resultSet.getDouble("engine_volume"));
                }
                Transmission transmission = null;
                if (resultSet.getInt("transmission_id") != 0) {
                    transmission = new Transmission(resultSet.getInt("transmission_id"), resultSet.getString("transmission_brand"),
                            resultSet.getString("transmission_type"), resultSet.getInt("transmission_speed"));
                }
                car = new Car(resultSet.getLong("car_id"), resultSet.getString("car_brand"),
                        resultSet.getString("car_model"), resultSet.getInt("car_year"), engine, transmission);
            }
            return car;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        try{
            String query = "SELECT c.id AS car_id, c.brand AS car_brand, c.model AS car_model, c.year AS car_year, " +
                    "e.id AS engine_id, e.brand AS engine_brand, e.type AS engine_type, e.power AS engine_power, e.volume AS engine_volume, " +
                    "t.id AS transmission_id, t.brand AS transmission_brand, t.type AS transmission_type, t.speed AS transmission_speed " +
                    "FROM cars c " +
                    "LEFT JOIN engines e ON c.engine_id = e.id " +
                    "LEFT JOIN cars_gears cg ON c.id = cg.car_id " +
                    "LEFT JOIN transmissions t ON cg.gear_id = t.id";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Engine engine = null;
                if (resultSet.getLong("engine_id") != 0) {
                    engine = new Engine(resultSet.getLong("engine_id"), resultSet.getString("engine_brand"),
                            resultSet.getString("engine_type"), resultSet.getInt("engine_power"),
                            resultSet.getDouble("engine_volume"));
                }
                Transmission transmission = null;
                if (resultSet.getInt("transmission_id") != 0) {
                    transmission = new Transmission(resultSet.getInt("transmission_id"), resultSet.getString("transmission_brand"),
                            resultSet.getString("transmission_type"), resultSet.getInt("transmission_speed"));
                }
                Car car = new Car(resultSet.getLong("car_id"), resultSet.getString("car_brand"),
                        resultSet.getString("car_model"), resultSet.getInt("car_year"), engine, transmission);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
