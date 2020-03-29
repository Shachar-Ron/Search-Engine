package Model.Ranker;

import java.util.*;
/**
 * The class gets a path to the folder.
 * Creates a new object of bm25 and returns the score of all words.
 */
public class Ranker {
    HashMap<String,Double> infoString;
    String path;

    public Ranker(String path){
        this.path=path;

    }
    /**
     * The method  receives a hashMap containing the words that exist in the query and ranking for each word
     * plus a boolean object in order to know with the process is with / without stemming
     *
     * @param query_rank
     * @param ifStemming
     * @return HashMap <String, Double> - containing for each term its rating according to the bm25 method.
     */
    public HashMap<String,Double> rank(HashMap<String,Double>query_rank, boolean ifStemming){

        BM25 bm25 = new BM25(query_rank,path, ifStemming);
        infoString = bm25.getScoreQueris();
        return infoString;


    }
}
