package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap ngm;
    public HistoryTextHandler(NGramMap map) {
        ngm = map;
    }

    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        String response = "";
        for (String w: words) {
            if (ngm.wHMap.containsKey(w)) {
                response += w + ": " + new TimeSeries(ngm.wHMap.get(w), startYear, endYear).toString() + "\n";
            }
        }
        return response;
    }
}
