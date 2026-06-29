package main;

import browser.NgordnetQueryHandler;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordHistoryFile, String yearHistoryFile,
            String synsetFile, String hyponymFile) {
        WN wN = new WN(synsetFile, hyponymFile, wordHistoryFile, yearHistoryFile);
        return new HyponymsHandler(wN);
    }
}
