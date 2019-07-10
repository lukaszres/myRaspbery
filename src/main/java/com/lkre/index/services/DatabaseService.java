package com.lkre.index.services;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.lkre.index.models.Pair;

public class DatabaseService {

    private final String INSERT_TEMPERATURE = "INSERT INTO tb_temperatures"
            + " (stt_temperature, stt_date) "
            + "VALUES (?, ?) ";

    private final String GET_LAST_TEMPERATURE = "SELECT stt_temperature FROM tb_temperatures ORDER BY stt_date DESC LIMIT 1";
    private final String GET_TEMPERATURES = "SELECT stt_temperature FROM tb_temperatures ORDER BY stt_date DESC LIMIT ?";
    private final String GET_TEMPERATURES_WITH_TIMES = "SELECT * FROM tb_temperatures ORDER BY stt_date DESC LIMIT ?";

    private Connection getConnection() throws SQLException {
        String host = "ec2-54-217-234-157.eu-west-1.compute.amazonaws.com";
        String username = "kgstaylndjlftv";
        String password = "3b7355b06d43c72b3042b6958257f7cc7b545800aab8d5f048a004cd02204bee";
        String port = "5432";
        String dataBase = "dcfvmna0pvh26i";
        String dbUrl = "jdbc:postgresql://" + host + ":" + port + "/" + dataBase + "?sslmode=require";
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
                Date date = rs.getDate("stt_date");
                pairs.add(new Pair<>(date, n));
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

}
