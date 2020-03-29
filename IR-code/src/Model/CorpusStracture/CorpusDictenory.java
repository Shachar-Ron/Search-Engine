package Model.CorpusStracture;

import java.io.*;
import java.util.*;

/**
 * A singleton class containing the data structures
 * that each parser holds and eventually holds
 * the final dictionary.
 */
public class CorpusDictenory {
    private static CorpusDictenory ourInstance = new CorpusDictenory();
    public TreeMap<String, LinkedList<String>> Dictenory;
    public TreeMap<String, String> finalDic;
    public TreeMap<String, LinkedList<String>> essenceDic;
    public HashMap<String, Integer> ArticleInform;

    int totalNumberOfWords = 0;
    String currMaxWord = "";
    int max = 0;


    public static CorpusDictenory getInstance() {
        return ourInstance;
    }

    /**
     * constructor
     */
    private CorpusDictenory() {
        Dictenory = new TreeMap<>(new Comperator());
        ArticleInform = new HashMap<>();
        essenceDic = new TreeMap<>(new Comperator());
        finalDic = new TreeMap<>(new Comperator());
    }

    /**
     * The method gets an entity and adds it
     * to the entity's data structure (Tree Map)
     * @param word
     * @param articleKey
     */
    public void addessenceDic(String word, String articleKey) {
        if (word.endsWith("."))
            word = word.substring(0, word.length() - 1);
        if (word.length() < 1)
            return;
        if (essenceDic.containsKey(word)) {
            LinkedList<String> tmp = essenceDic.get(word);
            tmp.add(articleKey);
            essenceDic.replace(word, essenceDic.get(word), tmp);

        } else {
            LinkedList<String> tmp = new LinkedList<>();
            tmp.add(articleKey);
            essenceDic.put(word, tmp);
        }

    }

    /**
     * The method adds a number that complies with
     * one of the rules of numbers in parser
     * to the terms data structure(Tree Map)
     * @param word
     * @param articleKey
     */
    public void addNumber(String word, String articleKey) {
        if (word.endsWith("."))
            word = word.substring(0, word.length() - 1);
        if (Dictenory.containsKey(word)) {
            LinkedList<String> tmp = Dictenory.get(word);
            tmp.add(articleKey);
            Dictenory.replace(word, Dictenory.get(word), tmp);
            addInfoToArticleInfo(word);
        } else {
            LinkedList<String> tmp = new LinkedList<>();
            tmp.add(articleKey);
            Dictenory.put(word, tmp);
            addInfoToArticleInfo(word);
        }
    }

    /**
     * The method adds a word that complies
     * with one of the rules of words in parser
     * to the data structure of the terms (Tree Map)
     * @param word
     * @param articleKey
     */
    public void addWord(String word, String articleKey) {
        if (word.endsWith("."))
            word = word.substring(0, word.length() - 1);
        String tempWord;
        if (word.length() < 1)
            return;
        if (word.charAt(0) >= 'A' && word.charAt(0) <= 'Z')
            word = word.toUpperCase();
        else
            word = word.toLowerCase();

        if (Dictenory.containsKey(word)) {
            LinkedList<String> tmp = Dictenory.get(word);
            tmp.add(articleKey);
            Dictenory.replace(word, Dictenory.get(word), tmp);
            addInfoToArticleInfo(word);

        }
        else {
            if (Dictenory.containsKey(word.toLowerCase())) {
                if (word.charAt(0) >= 'A' && word.charAt(0) <= 'Z') {
                    LinkedList<String> tmp = Dictenory.get(word.toLowerCase());
                    tmp.add(articleKey);
                    Dictenory.replace(word.toLowerCase(), Dictenory.get(word.toLowerCase()), tmp);
                    addInfoToArticleInfo(word.toLowerCase());

                }
            }
            else if (Dictenory.containsKey(word.toUpperCase())) {
                LinkedList<String> tmp = Dictenory.get(word.toUpperCase());
                tmp.add(articleKey);
                Dictenory.remove(word.toUpperCase());
                Dictenory.put(word.toLowerCase(), tmp);
                addInfoToArticleInfo(word);

            } else {
                LinkedList<String> tmp = new LinkedList<>();
                tmp.add(articleKey);
                Dictenory.put(word, tmp);
                addInfoToArticleInfo(word);

            }
        }
    }

    /**
     * The method counts for each document the number of unique words
     * and the maximum number of occurrences of the word
     * that appears most frequently in the document.
     * @param word
     */
    private void addInfoToArticleInfo(String word) {
        if (word.endsWith("."))
            word = word.substring(0, word.length() - 1);
        totalNumberOfWords++;
        if (ArticleInform.containsKey(word)) {
            int oldValue = ArticleInform.get(word);
            ArticleInform.replace(word, oldValue, oldValue + 1);
        } else {
            ArticleInform.put(word, 1);
        }
        int currMaxNumber = ArticleInform.get(word);
        if (currMaxNumber > this.max) {
            this.currMaxWord = word;
            this.max = currMaxNumber;
        }
    }

    /**
     *
     * @param finalDic
     */
    public void addCorpusDic(TreeMap <String, String> finalDic){
        this.finalDic = (TreeMap <String, String>)finalDic.clone();
        essenceDic.clear();
    }

    /**
     * The function builds the posting file and for each term
     * records how many times it appears in the corpus.
     * @return
     */
    private TreeMap<String, LinkedList<String>> buildDictenory() {

        TreeMap<String, LinkedList<String>> newDic = new TreeMap<>(new Comperator());

        for (String name : Dictenory.keySet()) {
            LinkedList<String> tmp = Dictenory.get(name);
            LinkedList<String> newList = new LinkedList<>();
            boolean flag = false;
            int counter = 0;
            String curr = "";
            for (String str : tmp) {
                if (str.equals(curr)) {
                    counter++;
                    //tmp.remove(str);
                } else {
                    if (counter != 0)
                        newList.add(curr + "," + counter);
                    curr = str;
                    flag = true;
                    counter = 1;

                }
            }
            if (flag) {
                if (counter != 0)
                    newList.add(curr + "," + counter);
            }
            newDic.put(name, newList);
        }

        return newDic;
    }

    /**
     *
     * @return
     */
    public String[] getArticlea() {
        String[] results = new String[4];
        results[0] = "" + totalNumberOfWords;
        results[1] = currMaxWord;
        results[2] = "" + max;
        max = 0;
        totalNumberOfWords = 0;
        currMaxWord = "";
        ArticleInform.clear();
        return results;
    }

    /**
     * The method sends a copy of the dictionary.
     * @return
     */
    public TreeMap<String, LinkedList<String>> getDictenery() {

        Object temp = buildDictenory().clone();
        Dictenory.clear();
        return (TreeMap) temp;
    }

    /**
     *
     * @return data structure that holds all entities
     * (including the entities that appear only once in each corpus)
     */
    public TreeMap<String, LinkedList<String>> getEssenceDic() {
        TreeMap<String, LinkedList<String>> newDic = new TreeMap<>();
        for (String name : essenceDic.keySet()) {
            LinkedList<String> tmp = essenceDic.get(name);
            LinkedList<String> newList = new LinkedList<>();
            boolean flag = false;
            int counter = 0;
            String curr = "";
            for (String str : tmp) {
                if (str.equals(curr)) {
                    counter++;
                    //tmp.remove(str);
                } else {
                    if (counter != 0)
                        newList.add(curr + "," + counter);
                    curr = str;
                    flag = true;
                    counter = 1;
                }
            }
            if (flag) {
                if (counter != 0)
                    newList.add(curr + "," + counter);
            }
            newDic.put(name, newList);
        }
        return newDic;
    }

    /**
     * The method resets all the data structures in the memory
     */
    public void reset(){
        Dictenory.clear();
        essenceDic.clear();
        ArticleInform.clear();
        finalDic.clear();
    }

    /**
     * Override
     * The method implements a comparison for the treeMap
     * so that we can enter each term in a dictionary with
     * a large / small letter in an orderly manner.
     */
    static class Comperator implements Comparator<String>{
        @Override
        public int compare(String t1,String t2){
            return t1.toLowerCase().compareTo(t2.toLowerCase());
        }
    }

    public void loadDic(String path,boolean withStem){
        try {
            finalDic.clear();
            String str2="";
            if(withStem)
                str2 = "Stem";
          BufferedReader  br2 = new BufferedReader(new FileReader(path + "\\dictionary"+str2+".txt"));
            String str;
          while((str = br2.readLine()) != null){
              String [] info = str.split(">");
              String key = info[0];
              String value = info[1];
              finalDic.put(key,value);
          }
        }
        catch (Exception e){
        }
    }
}
