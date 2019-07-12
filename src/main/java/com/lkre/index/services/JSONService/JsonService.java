package com.lkre.index.services.JSONService;

import org.primefaces.json.JSONObject;

public class JsonService {

    public double getMainTemperature(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getJSONObject("main")
                         .getDouble("temp");
    }
}
