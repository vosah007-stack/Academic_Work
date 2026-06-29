package main;

import java.util.ArrayList;
import java.util.List;

public class Synset {
    int id;
    String[] words;
    List<Synset> hyponyms;
    public Synset(int id, String[] words) {
        this.id = id;
        this.words = words;
        hyponyms = new ArrayList<Synset>();
    }
    public void add(Synset s) {
        hyponyms.add(s);
    }
}
