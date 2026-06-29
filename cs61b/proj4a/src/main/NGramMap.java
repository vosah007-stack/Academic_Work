package main;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static main.TimeSeries.MAX_YEAR;
import static main.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    Map<String, TimeSeries> wHMap;
    TimeSeries yHTSeries;

    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        wHMap = new HashMap<>();
        yHTSeries = new TimeSeries();
        TimeSeries tempTS = new TimeSeries();
        In in = new In(wordHistoryFilename);
        String[] wordsPerLine;
        while (!in.isEmpty()) {
            wordsPerLine = in.readLine().split("\t");
            if (wHMap.containsKey(wordsPerLine[0])) {
                wHMap.get(wordsPerLine[0]).put(Integer.parseInt(wordsPerLine[1]), Double.parseDouble(wordsPerLine[2]));
            } else {
                wHMap.put(wordsPerLine[0], new TimeSeries());
                wHMap.get(wordsPerLine[0]).put(Integer.parseInt(wordsPerLine[1]), Double.parseDouble(wordsPerLine[2]));
            }
        }
        in = new In(yearHistoryFilename);
        while (!in.isEmpty()) {
            wordsPerLine = in.readLine().split(",");
            yHTSeries.put(Integer.parseInt(wordsPerLine[0]), Double.parseDouble(wordsPerLine[1]));
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        TimeSeries wordTS;
        if (wHMap.containsKey(word)) {
            wordTS = new TimeSeries(wHMap.get(word), startYear, endYear);
            ts = wordTS;
            return ts;
        }
        return ts;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        TimeSeries ts = new TimeSeries();
        if (wHMap.containsKey(word)) {
            ts = new TimeSeries(wHMap.get(word), MIN_YEAR, MAX_YEAR);
            return ts;
        }
        return new TimeSeries();
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(yHTSeries, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        if (wHMap.containsKey(word)) {
            ts = new TimeSeries(wHMap.get(word), startYear, endYear);
            ts = ts.dividedBy(yHTSeries);
            return ts;
        }
        return ts;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries ts = new TimeSeries();
        if (wHMap.containsKey(word)) {
            ts = new TimeSeries(wHMap.get(word), MIN_YEAR, MAX_YEAR);
            ts = ts.dividedBy(yHTSeries);
            return ts;
        }
        return ts;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        for (String w: words) {
            if (wHMap.containsKey(w)) {
                ts = ts.plus(weightHistory(w, startYear, endYear));
            }
        }
        return ts;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries ts = new TimeSeries();
        for (String w: words) {
            if (wHMap.containsKey(w)) {
                ts = ts.plus(weightHistory(w));
            }
        }
        return ts;
    }

}
