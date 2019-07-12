package com.lkre.index.services.database;

import static com.lkre.index.services.database.SQLQueries.GET_LAST_TEMPERATURE;
import static com.lkre.index.services.database.SQLQueries.GET_TEMPERATURES_NUMBER;
import static com.lkre.index.services.database.SQLQueries.GET_TEMPERATURES_WITH_TIMES;
import static com.lkre.index.services.database.SQLQueries.INSERT_TEMPERATURE;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.lkre.index.models.Pair;
import com.lkre.index.services.PropertiesService;

public class DatabaseService {

    private PropertiesService propertiesService;

    public DatabaseService() throws IOException {
        propertiesService = new PropertiesService();
    }

    private Connection getConnection() throws SQLException {
        String host = propertiesService.getPropertyService("database-host");
        String username = propertiesService.getPropertyService("database-username");
        String password = propertiesService.getPropertyService("database-password");
        String port = propertiesService.getPropertyService("database-port");
        String dataBase = propertiesService.getPropertyService("database-name");
        String dbUrl = "jdbc:postgresql://" + host + ":" + port + "/" + dataBase + "?sslmode" +
                "=require";
        return DriverManager.getConnection(dbUrl, username, password);
    }

    public void addTemperature(
            BigDecimal temperature,
            Timestamp timestamp
    ) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_TEMPERATURE);
            preparedStatement.setBigDecimal(1, temperature);
            preparedStatement.setTimestamp(2, timestamp);
            int i = preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BigDecimal getLastTemperature() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_LAST_TEMPERATURE);
            connection.close();
            resultSet.next();
            return resultSet.getBigDecimal(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Pair> getMapTemperatures(int number) {
        try {
            Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    GET_TEMPERATURES_WITH_TIMES);
            preparedStatement.setInt(1, number);
            ResultSet rs = preparedStatement.executeQuery();
            connection.close();

            List<Pair> pairs = new ArrayList<>();

            while (rs.next()) {
                float n = rs.getFloat("stt_temperature");
                Timestamp timestamp = rs.getTimestamp("stt_date");
                pairs.add(new Pair<>(timestamp, n));
            }
            return pairs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Float> getTemperatures(int number) {
        try {
            Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    GET_TEMPERATURES_WITH_TIMES);
            preparedStatement.setInt(1, number);
            ResultSet rs = preparedStatement.executeQuery();
            connection.close();
            ArrayList<Float> floats = new ArrayList<>();
            while (rs.next()) {
                float n = rs.getFloat("stt_temperature");
                floats.add(n);
            }

            return floats;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTemperaturesNumber() {
        Connection connection;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_TEMPERATURES_NUMBER);
            connection.close();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
