package com.lkre.index;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import com.lkre.index.models.Pair;
import com.lkre.index.services.ForecastService;
import com.lkre.index.services.database.DatabaseService;
import lombok.Getter;

@ManagedBean
public class IndexBacking {

    private DatabaseService databaseService = new DatabaseService();
    @Getter
    private LineChartModel dateModel;

    public IndexBacking() throws IOException {}

    @PostConstruct
    public void init() {
        List<Pair> mapTemperatures = databaseService.getMapTemperatures(24);
        createDateModel(mapTemperatures);
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

    public int totalTemperatures() {
        return databaseService.getTemperaturesNumber();
    }

    public String getActualTemperatureFromOpenWeather() throws IOException {
        ForecastService forecastService = new ForecastService();
        double actualTemperature = forecastService.getActualTemperatureInCelsius();
        return Double.toString(actualTemperature);
    }
}