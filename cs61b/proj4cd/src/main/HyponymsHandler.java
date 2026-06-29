package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {
    WN wN;
    public HyponymsHandler(WN wN) {
        this.wN = wN;
    }
    @Override
    public String handle(NgordnetQuery q) {
        String[] words = q.words().toArray(new String[0]);
        int k = q.k();
        int startYear = q.startYear();
        int endYear = q.endYear();
        List<String> hyponymList;
        String returnString = "[";
        if (k == 0) {
            hyponymList = wN.findHyponymsZero(words);
        } else {
            hyponymList = wN.findHyponymsNonZero(words, k, startYear, endYear);
        }
        for (int i = 0; i < hyponymList.size(); i++) {
            if (i == hyponymList.size() - 1) {
                returnString += hyponymList.get(i);
            } else {
                returnString += hyponymList.get(i) + ", ";
            }
        }
        return returnString + "]";
    }
}
