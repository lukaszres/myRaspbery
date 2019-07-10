package com.lkre.index;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import com.lkre.index.services.DatabaseService;
import com.lkre.index.models.Pair;

@ManagedBean
public class IndexBacking {

    private String firstName = "John";
    private String lastName = "Doe";
    private DatabaseService databaseService = new DatabaseService();
    private BarChartModel barModel;

    @PostConstruct
    public void init() {
        barModel = new BarChartModel();
        ChartSeries chartSeries = new ChartSeries();
        chartSeries.setLabel("Temperatury");
        List<Pair> mapTemperatures = databaseService.getMapTemperatures(24);
        for (Pair entry : mapTemperatures) {
            chartSeries.set(entry.getL(), (float) entry.getR());
        }
        barModel.addSeries(chartSeries);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String showGreeting() {
        return "Hello " + firstName + " " + lastName + "!";
    }

    public String readLastTemperature() {
        BigDecimal lastTemperature = databaseService.getLastTemperature();
        return "Ostatnia temperatura: " + lastTemperature;
    }

    public String readTemperatures(int number) {
        List<Float> temperatures = databaseService.getTemperatures(number);
        return "Ostatnie " + number + "temperatur to: " + temperatures;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }
}