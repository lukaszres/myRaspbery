package com.lkre.index.services;

import static java.lang.Math.round;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.lkre.index.services.JSONService.JsonService;

public class ForecastService {
    private PropertiesService propertiesService;
    private JsonService jsonService;
    private final String USER_AGENT = "Mozilla/5.0";

    public ForecastService() throws IOException {
        propertiesService = new PropertiesService();
        jsonService = new JsonService();
    }

    public double getActualTemperatureInCelsius() throws IOException {
        String response = getResponse();
        double mainTemperature = jsonService.getMainTemperature(response);
        return round((mainTemperature - 273.15));
    }

    private String getResponse() throws IOException {
        String url = propertiesService.getProperty("openweathermap-url");
        String city = propertiesService.getProperty("openweathermap-city");
        String country = propertiesService.getProperty("openweathermap-country-code");
        String appid = propertiesService.getProperty("openweathermap-appid");
        url += "?q=" + city + "," + country + "&appid=" + appid;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();
    }
}
