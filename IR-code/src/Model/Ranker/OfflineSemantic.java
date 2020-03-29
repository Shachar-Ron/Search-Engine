package Model.Ranker;

import com.medallia.word2vec.Searcher;
import com.medallia.word2vec.Word2VecModel;
import javafx.scene.control.Alert;

import java.io.File;
import java.util.HashMap;
import java.util.List;
/**
 * The class uses word2Vec and uses a dictionary with weights previously trained on a small corpus.
 *
 */
public class OfflineSemantic {

    /**
     *
     * @param qeuries
     * @return additional words that have a semantic proximity for the words we received in the query.
     *          When the words added in the semantic model receive a score according
     *          to their proximity to the original word from the query.
     * @throws Exception
     */
    public static HashMap<String,Double> searchSynonym(HashMap<String,Double> qeuries) throws Exception {
        try {
            Word2VecModel word2VecExamples =
                    Word2VecModel.fromTextFile(new File("word2vec.c.output.model.txt"));
            com.medallia.word2vec.Searcher searcher = word2VecExamples.forSearch();
            HashMap<String,Double> output = ( HashMap<String,Double>)qeuries.clone();
            for(String str : qeuries.keySet()) {
                try {
                    if (str.contains(" ") || str.contains("-"))
                        continue;
                    Double weight = qeuries.get(str);
                    if(weight==0.35)
                        continue;
                    output.put(str, weight);
                    str = str.toLowerCase();
                    List<Searcher.Match> matchList = searcher.getMatches(str, 2);
                    for (com.medallia.word2vec.Searcher.Match match : matchList) {
                        String term = match.match();
                        if(qeuries.containsKey(term.toLowerCase())||qeuries.containsKey(term.toUpperCase()))
                            continue;
                        output.put(term, 0.05 );
                    }
                }catch (Exception E){
                }
            }


            return output;

        }
        catch (Exception E){
        }
return null;
  }
}
