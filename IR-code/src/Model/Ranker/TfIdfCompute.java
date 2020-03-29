package Model.Ranker;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
/**
 * The class uses the dictionary and invertedIndex to produce
 * the data structures needed to realize the final BM25 formula.
 */
public class TfIdfCompute {

    String path;
    String stem = "";
    HashMap<String, Double> query_rank;
    int corpusSize = 472525;

    public TfIdfCompute(HashMap<String, Double> query_rank, String path, boolean ifStemming) {
        this.path = path;
        //this.wordsQuery=wordsQuery;
        this.query_rank = query_rank;
        if (ifStemming) {
            stem = "Stem";
        }
    }
    /**
     * The method produces from the dictionary and invertedIndex the following data structures to enable bm25 retrieval:
     * i. HashMap <String, Double> - for each term its IDF value
     * ii. HashMap <String, Double> - for each term its TF value for each specific document
     * @return
     */
    public List<HashMap<String, Double>> getTFMatrix() {
        List<HashMap<String, Double>> tf_idf = new ArrayList<HashMap<String, Double>>();
        //TF
        HashMap<String, Double> term_numOcc = new HashMap<>();
        String[] splitLine;
        int counterwordsQuery = 1;

        //IDF
        HashMap<String, Double> term_numOcc2 = new HashMap<>();
        BufferedReader reader2;
        for (String str : query_rank.keySet())
            try {
                String fileNameToSearch = "NUMBER";
                str = str.toUpperCase();
                if (str.charAt(0) >= 'A' && str.charAt(0) <= 'Z')
                    fileNameToSearch = "" + str.charAt(0);
                reader2 = new BufferedReader(new FileReader(path + "//" + fileNameToSearch/*"InvertedIndex"*/ + "" + stem + ".txt"));
                String line = reader2.readLine();
                while (line != null) {
                    splitLine = line.split("@|#");
                    String term = splitLine[0];
                    if (query_rank.containsKey(term) || query_rank.containsKey(term.toUpperCase()) || query_rank.containsKey(term.toLowerCase())) {
                        if (query_rank.containsKey(term.toUpperCase()))
                            term = term.toUpperCase();
                        if (query_rank.containsKey(term.toLowerCase()))
                            term = term.toLowerCase();
                        double result = 0;
                        int DocNumber = 0;

                        //for all doc in the line of the term
                        for (int j = 1; j < splitLine.length; j++) {
                            String[] splitDoc = splitLine[j].split(",");
                            DocNumber = Integer.parseInt(splitDoc[0]);
                            double numOccInDoc = Double.parseDouble(splitDoc[1]);
                                result = numOccInDoc * (query_rank.get(term));
                                term_numOcc.put(term + ":" + DocNumber, result);
                            }

                            //IDF
                            double numOcc = splitLine.length - 1;
                            term_numOcc2.put(term, Math.log((corpusSize) / numOcc) / Math.log(10));
                            counterwordsQuery++;


                            if (counterwordsQuery > query_rank.size())
                                break;
                        }
                        line = reader2.readLine();
                    }
                    reader2.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
                tf_idf.add(term_numOcc);
                tf_idf.add(term_numOcc2);
                return tf_idf;
            }

    }






