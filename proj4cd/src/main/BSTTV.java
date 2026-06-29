package main;


import java.util.TreeMap;

public class BSTTV {
    public class NodeTV {
        NodeTV left;
        NodeTV right;
        NodeTV parent;
        String value;
        double popularity;
        public NodeTV(String v, double pop, NodeTV p) {
            value = v;
            popularity = pop;
            left = null;
            right = null;
            parent = p;
        }
        public boolean add(String v) {
            double pop = sumHistory(v);
            if ((pop == this.popularity && v.equals(this.value)) || pop == 0) {
                return false;
            }
            if (pop > this.popularity) {
                if (this.right == null) {
                    this.right = new NodeTV(v, pop, this);
                    return true;
                }
                return this.right.add(v);
            } else {
                if (this.left == null) {
                    this.left = new NodeTV(v, pop, this);
                    return true;
                }
                return this.left.add(v);
            }
        }

        public String findSmallestWord() {
            NodeTV tempNodeTV = this;
            while (tempNodeTV.left != null) {
                tempNodeTV = tempNodeTV.left;
            }
            return tempNodeTV.value;
        }

        public void removeSmallestWord() {
            NodeTV tempNodeTV = this;
            while (tempNodeTV.left != null) {
                tempNodeTV = tempNodeTV.left;
            }
            tempNodeTV.parent.left = null;
        }
    }
    NodeTV root;
    NGramMap ngm;
    int startYear;
    int endYear;
    int size;
    public BSTTV(NGramMap ngm, int sY, int eY) {
        this.ngm = ngm;
        startYear = sY;
        endYear = eY;
        size = 0;
    }
    public void add(String v) {
        double pop = sumHistory(v);
        if (root == null) {
            root = new NodeTV(v, pop, null);
            size = 1;
        } else {
            if (root.add(v)) {
                size++;
            }
        }
    }
    public int size() {
        return size;
    }
    public void remove() {
        if (size > 0) {
            if (root.left == null) {
                root = root.right;
                size--;
            } else {
                root.removeSmallestWord();
                size--;
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
