package com.lkre.index.services.database;

import lombok.Getter;

@Getter
class SQLQueries {
    final static String INSERT_TEMPERATURE = "INSERT INTO tb_temperatures"
            + " (stt_temperature, stt_date) "
            + "VALUES (?, ?) ";

    final static String GET_LAST_TEMPERATURE = "SELECT stt_temperature FROM tb_temperatures " +
            "ORDER BY stt_date DESC LIMIT 1";
    final static String GET_TEMPERATURES = "SELECT stt_temperature FROM tb_temperatures ORDER BY" +
            " stt_date DESC LIMIT ?";
    final static String GET_TEMPERATURES_WITH_TIMES = "SELECT * FROM tb_temperatures ORDER BY " +
            "stt_date DESC LIMIT ?";
    final static String GET_TEMPERATURES_NUMBER = "SELECT COUNT(*) FROM tb_temperatures";
}
