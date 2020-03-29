package Model.Ranker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.CorpusStracture.InfoTerms;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * The class uses the DataMouseAPI site to realize the semantic model in online.
 */
public class OnlineSemantic {

    private static final String USER_AGENT = "Mozilla/5.0";

    /**
     * returns
     * @param wordToSearch
     * @return additional words that have a semantic proximity for the words
     *          we received in the query.
     *          When the words added in the semantic model
     *          receive a score according to their proximity to the original word from the query.
     * @throws Exception
     */
    public static HashMap<String, Double> searchSynonym(HashMap<String,Double> wordToSearch) throws Exception {
        HashMap<String, Double> info ;
        info = (HashMap<String,Double>)wordToSearch.clone();
        InfoTerms infoTerms = InfoTerms.getInstance();
        for (String str : wordToSearch.keySet()) {
            if (str.contains(" ")||info.get(str)==0.4)
                continue;
            String url = "https://api.datamuse.com/words?rel_syn=" + str;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            // ordering the response
            StringBuilder response;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            ObjectMapper mapper = new ObjectMapper();

            try {
                ArrayList<Word> words = mapper.readValue(
                        response.toString(),
                        mapper.getTypeFactory().constructCollectionType(ArrayList.class, Word.class)
                );

                if (words.size() > 0) {
                    int i = 0;
                    double score = 0;
                    for (Word word : words) {
                        if (word.score <= 7000) {

                            continue;
                        }
                        else if (word.score >7000) {
                            score = 0.2;
                        }
                       else if (word.score >8000) {
                        score = 0.8;
                    }
                        info.put(word.getWord(), score);
                        i++;
                        if (i > 2)
                            break;
                    }
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }
        return info;
    }

    // word and score attributes are from DataMuse API
    static class Word {
        private String word;
        private int score;

        public String getWord() {
            return this.word;
        }

        public int getScore() {
            return this.score;
        }
    }
}

