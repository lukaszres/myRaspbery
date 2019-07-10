package com.lkre.index.services;

import java.math.BigDecimal;
import java.sql.*;

public class DatabaseService {

    private final String INSERT_TEMPERATURE = "INSERT INTO tb_temperatures"
            + " (stt_temperature, stt_date) "
            + "VALUES (?, ?) ";

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
