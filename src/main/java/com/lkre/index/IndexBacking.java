package com.lkre.index;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.*;
import com.lkre.index.models.Pair;
import com.lkre.index.services.DatabaseService;

@ManagedBean
public class IndexBacking {

    private String firstName = "John";
    private String lastName = "Doe";
    private DatabaseService databaseService = new DatabaseService();
    private BarChartModel barModel;
    private LineChartModel dateModel;

    public IndexBacking() throws IOException {}

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
        series1.setLabel("Termometr 1");
        for (Pair entry : mapTemperatures) {
            series1.set(entry.getL()
                             .toString(), (float) entry.getR());
        }
        dateModel.addSeries(series1);
        dateModel.setTitle("Termometry");
        dateModel.setZoom(false);
        dateModel.getAxis(AxisType.Y)
                 .setLabel("Values");
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);

        Timestamp newerTimestamp = (Timestamp) mapTemperatures.get(0)
                                                              .getL();
        int duration = ((14 * 60) + 59) * 1000;
        Timestamp maxTimestamp = new Timestamp(newerTimestamp.getTime() + duration);
        axis.setMax(maxTimestamp.toString());
        axis.setTickFormat("%H : %M");

        dateModel.setAnimate(true);
        dateModel.setLegendPosition("w");
        dateModel.getAxes()
                 .put(AxisType.X, axis);
    }

    public LineChartModel getDateModel() {
        return dateModel;
    }

    public int totalTemperatures() {
        return databaseService.getTemperaturesNumber();
    }
}