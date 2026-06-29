package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    NGramMap ngm;
    public HistoryHandler(NGramMap map) {
        ngm = map;
    }
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        List<TimeSeries> lts = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for (String w: words) {
            if (ngm.wHMap.containsKey(w)) {
                labels.add(w);
                lts.add(new TimeSeries(ngm.wHMap.get(w), startYear, endYear));
            }
        }
        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        return Plotter.encodeChartAsString(chart);

    }
}
