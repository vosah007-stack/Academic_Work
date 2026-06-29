package main;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class WN extends HashMap<Integer, List<Integer>> {
    TreeMap<Integer, Synset> synsets;
    TreeMap<String, List<Integer>> wToId;
    HashMap<Integer, Boolean> marked;
    public NGramMap ngm;


    public WN(String synsetsFileName, String hyponymsFileName, String wordHistoryFileName, String yearHistoryFileName) {
        super();
        synsets = new TreeMap<>();
        wToId = new TreeMap<>();
        marked = new HashMap<>();
        ngm = new NGramMap(wordHistoryFileName, yearHistoryFileName);
        In in = new In(synsetsFileName);
        String[] wordsPerLine;
        String[] wordsPerSynset;
        List<Integer> hyponymsPerLine;
        Synset tempS;
        while (!in.isEmpty()) {
            wordsPerLine = in.readLine().split(",");
            wordsPerSynset = wordsPerLine[1].split(" ");
            tempS = new Synset(Integer.parseInt(wordsPerLine[0]), wordsPerSynset);
            addSynset(tempS);
        }
        in = new In(hyponymsFileName);
        while (!in.isEmpty()) {
            hyponymsPerLine = new ArrayList<>();
            wordsPerLine = in.readLine().split(",");
            for (int i = 1; i < wordsPerLine.length; i++) {
                hyponymsPerLine.add(Integer.parseInt(wordsPerLine[i]));
            }
            addHyponyms(Integer.parseInt(wordsPerLine[0]), hyponymsPerLine);
        }
    }
    public void addSynset(Synset s) {
        synsets.put(s.id, s);
        for (String w: s.words) {
            if (wToId.containsKey(w)) {
                wToId.get(w).add(s.id);
            } else {
                wToId.put(w, new ArrayList<>());
                wToId.get(w).add(s.id);
            }
        }
        marked.put(s.id, false);
        this.put(s.id, new ArrayList<>());
    }
    public void addHyponyms(int hypernym, List<Integer> hyponyms) {
        for (int h: hyponyms) {
            this.get(hypernym).add(h);
            synsets.get(hypernym).add(synsets.get(h));
        }
    }

    public List<String> findHyponyms(String word) {
        BST hyponymTree = new BST();
        if (!wToId.containsKey(word)) {
            return new ArrayList<>();
        }
        List<Integer> ids = wToId.get(word);
        for (int i = 0; i < ids.size(); i++) {
            findHyponymsHelperBST(synsets.get(ids.get(i)), hyponymTree);
        }
        for (int id: marked.keySet()) {
            marked.put(id, false);
        }
        return hyponymTree.ordered();
    }

    public BST findHyponymsBST(String word) {
        BST hyponymTree = new BST();
        if (!wToId.containsKey(word)) {
            return new BST();
        }
        List<Integer> ids = wToId.get(word);
        for (int i = 0; i < ids.size(); i++) {
            findHyponymsHelperBST(synsets.get(ids.get(i)), hyponymTree);
        }
        for (int id: marked.keySet()) {
            marked.put(id, false);
        }
        return hyponymTree;
    }
    public BSTMS findHyponymsBSTMS(String word, int k, int startYear, int endYear) {
        BSTMS hyponymTree = new BSTMS(k, ngm, startYear, endYear);
        if (!wToId.containsKey(word)) {
            return new BSTMS(k, ngm, startYear, endYear);
        }
        List<Integer> ids = wToId.get(word);
        for (int i = 0; i < ids.size(); i++) {
            findHyponymsHelperBSTMS(synsets.get(ids.get(i)), hyponymTree);
        }
        for (int id: marked.keySet()) {
            marked.put(id, false);
        }
        return hyponymTree;
    }

    public void findHyponymsHelperBST(Synset s, BST hT) {
        if (!marked.get(s.id)) {
            marked.put(s.id, true);
            for (String word: s.words) {
                hT.add(word);
            }
        }
        for (Synset h: s.hyponyms) {
            findHyponymsHelperBST(h, hT);
        }
    }

    public void findHyponymsHelperBSTMS(Synset s, BSTMS hT) {
        if (!marked.get(s.id)) {
            marked.put(s.id, true);
            for (String word: s.words) {
                hT.add(word);
            }
        }
        for (Synset h: s.hyponyms) {
            findHyponymsHelperBSTMS(h, hT);
        }
    }

    public List<String> findHyponymsZero(String[] words) {
        List<BST> hyponymsBSTs = new ArrayList<>();
        BST minBST = new BST();
        BST currentBST;
        List<String> currentList;
        List<String> updateList;
        for (String word: words) {
            currentBST = findHyponymsBST(word);
            if (currentBST.size() != 0) {
                if (minBST.size() == 0 || currentBST.size() < minBST.size()) {
                    if (minBST.size != 0) {
                        hyponymsBSTs.add(minBST);
                    }
                    minBST = currentBST;
                } else {
                    hyponymsBSTs.add(currentBST);
                }
            } else {
                return new ArrayList<>();
            }
        }
        currentList = minBST.ordered();
        updateList = new ArrayList<>();
        for (BST h: hyponymsBSTs) {
            for (int i = 0; i < currentList.size(); i++) {
                if (h.contains(currentList.get(i))) {
                    updateList.add(currentList.get(i));
                }
            }
            currentList = updateList;
            updateList = new ArrayList<>();
        }
        return currentList;
    }
    public List<String> findHyponymsNonZero(String[] words, int k, int startYear, int endYear) {
        List<BST> hyponymsBSTs = new ArrayList<>();
        BST minBST = new BST();
        BST currentBST;
        List<String> currentList;
        List<String> updateList;
        for (String word: words) {
            currentBST = findHyponymsBST(word);
            if (currentBST.size() != 0) {
                if (minBST.size() == 0 || currentBST.size() < minBST.size()) {
                    if (minBST.size != 0) {
                        hyponymsBSTs.add(minBST);
                    }
                    minBST = currentBST;
                } else {
                    hyponymsBSTs.add(currentBST);
                }
            } else {
                return new ArrayList<>();
            }
        }
        currentList = minBST.ordered();
        updateList = new ArrayList<>();
        for (BST h: hyponymsBSTs) {
            for (int i = 0; i < currentList.size(); i++) {
                if (h.contains(currentList.get(i))) {
                    updateList.add(currentList.get(i));
                }
            }
            currentList = updateList;
            updateList = new ArrayList<>();
        }
        BSTMS returnBSTMS = new BSTMS(k, ngm, startYear, endYear);
        for (String w: currentList) {
            returnBSTMS.add(w);
        }
        return returnBSTMS.ordered();
    }
}
