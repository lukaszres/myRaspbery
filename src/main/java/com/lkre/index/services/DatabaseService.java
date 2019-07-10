package com.lkre.index.services;

import java.sql.*;

public class DatabaseService {

    private final String INSERT_LOG = "INSERT INTO tb_temperatures"
            + " (stt_temperature, stt_date) "
            + "VALUES (?, ?) ";

    private Connection getConnection() throws SQLException {
        String host = "ec2-54-217-234-157.eu-west-1.compute.amazonaws.com";
        String username = "kgstaylndjlftv";
        String password = "3b7355b06d43c72b3042b6958257f7cc7b545800aab8d5f048a004cd02204bee";
        String port = "5432";
        String dataBase = "dcfvmna0pvh26i";
//        String dbUrl = "jdbc:postgresql://ec2-54-217-234-157.eu-west-1.compute.amazonaws.com:5432/dcfvmna0pvh26i?sslmode=require";
        String dbUrl = "jdbc:postgresql://" + host + ":" + port + "/" + dataBase + "?sslmode=require";
        return DriverManager.getConnection(dbUrl, username, password);
    }

    public void addTemperature(String temperature) {
        Timestamp currentTimestamp = new Timestamp(new java.util.Date().getTime());
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_LOG);
            preparedStatement.setString(1, temperature);
            preparedStatement.setTimestamp(2, currentTimestamp);
            int i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
