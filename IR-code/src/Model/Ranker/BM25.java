package Model.Ranker;

import Model.CorpusStracture.InfoTerms;
import java.util.HashMap;
import java.util.List;
/**
 * The Class is assisted by tfIdfCompute to return for each term
 * its ranking according to the formula BM25.
 * The class initializes all data about the given corpus such as its size and average document length.
 */
public class BM25 {

    TfIdfCompute tfIdfCompute;
    double k;
    double b;
    int avrgDocLength;
    int corpusSize;
    boolean ifStemming;
    HashMap<String,Double>query_rank;

    public BM25(HashMap<String,Double>query_rank ,String path, boolean ifStemming){
        k=1.8; //1.8
        b=0.7; //0.7
        this.query_rank=(HashMap<String,Double>)query_rank.clone();
        tfIdfCompute = new TfIdfCompute(query_rank,path,ifStemming);
        avrgDocLength=450;
        corpusSize=472525;
        this.ifStemming=ifStemming;
    }
    /**
     * The method is used in the TfIdfCompute class
     * to return HashMap <String, Double> - for each term its score exists according to the formula.
     * @return
     */
    public HashMap<String,Double> getScoreQueris(){
        InfoTerms infoTerms = InfoTerms.getInstance();
        List<HashMap<String, Double>> tf_idf= tfIdfCompute.getTFMatrix();
        HashMap < String,Double > scoreInfo = new HashMap<>();
        for(int i=1;i<=corpusSize;i++){
            double score = 0;
            for(String word : query_rank.keySet()){
                double idfNumber;
                double itfNumber;
                try {
                    idfNumber = tf_idf.get(1).get(word);
                    itfNumber = tf_idf.get(0).get(word + ":" + i);
                }
                catch (Exception e){
                    continue;
                }
                score = score + idfNumber*((itfNumber*(k+1)/(itfNumber + k*(1-b+b*(infoTerms.getDocSize(i)/avrgDocLength)))));
            }
            scoreInfo.put(""+i,score);
        }
        return scoreInfo;
    }
}
