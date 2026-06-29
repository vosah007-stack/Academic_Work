package main;

import java.util.TreeMap;

public class BSTMS extends BST {
    int maxSize;
    NGramMap ngm;
    int startYear;
    int endYear;
    BSTTV bsttv;

    public BSTMS(int mS, NGramMap ngm, int startYear, int endYear) {
        super();
        maxSize = mS;
        this.ngm = ngm;
        this.startYear = startYear;
        this.endYear = endYear;
        bsttv = new BSTTV(ngm, startYear, endYear);
    }

    public void add(String v) {
        double pop = sumHistory(v);
        if (pop > 0) {
            if (size < maxSize) {
                if (size == 0) {
                    root = new Node(v, null);
                    size = 1;
                    bsttv.add(v);
                } else {
                    if (root.add(v)) {
                        size++;
                        bsttv.add(v);
                    }
                }
            } else {
                if (sumHistory(v) > sumHistory(bsttv.root.findSmallestWord())) {
                    remove(bsttv.root.findSmallestWord());
                    bsttv.remove();
                    add(v);
                }
            }
        }
    }
    public double sumHistory(String word) {
        TreeMap<Integer, Double> wordMap = ngm.countHistory(word, startYear, endYear);
        double sum = 0;
        for (double v: wordMap.values()) {
            sum += v;
        }
        return sum;
    }
}

