package com.lkre.index;

import com.lkre.index.models.Pair;
import com.lkre.index.services.DatabaseService;
import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.math.BigDecimal;
import java.util.List;

@ManagedBean
public class IndexBacking {

    private String firstName = "John";
    private String lastName = "Doe";
    private DatabaseService databaseService = new DatabaseService();
    private BarChartModel barModel;
    private LineChartModel dateModel;

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
        createDateModel(mapTemperatures);
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

    private void createDateModel(List<Pair> mapTemperatures) {
        dateModel = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
        for (Pair entry : mapTemperatures) {
            series1.set(entry.getL().toString(), (float) entry.getR());
        }
        dateModel.addSeries(series1);
        dateModel.setTitle("Zoom for Details");
        dateModel.setZoom(true);
        dateModel.getAxis(AxisType.Y).setLabel("Values");
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);
        String maxTimeStamp = mapTemperatures.get(0).getL().toString();
        axis.setMax(maxTimeStamp);
        axis.setTickFormat("%H : %M");

        dateModel.getAxes().put(AxisType.X, axis);
    }

    public LineChartModel getDateModel() {
        return dateModel;
    }

}