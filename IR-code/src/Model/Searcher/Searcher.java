package Model.Searcher;

import Model.CorpusStracture.CorpusDictenory;
import Model.Parser.Parse;
import Model.Ranker.OfflineSemantic;
import Model.Ranker.OnlineSemantic;
import Model.Ranker.Ranker;
import javafx.scene.control.Alert;
import sample.UserInterface;

/**
 * The class returns the 50 most relevant documents for the given query
 */
import java.util.*;

public class Searcher {
    boolean ifSemantic;
    boolean ifStemmer;

    public Searcher(boolean ifSemantic, boolean ifStemmer) {
        this.ifSemantic = ifSemantic;
        this.ifStemmer = ifStemmer;
    }

    /**
     * The method performs a parser for the query it received.
     * And sends RANKER the query and description (for each word in query with score 1 and for each word in description score of 0.35)
     *
     * @return the 50 most relevant documents for a given query.
     */
    public TreeMap<Integer, String[]> getTop50ByQueryFile(boolean withpath, String... strings) {
        TreeMap<Integer, String[]> output = new TreeMap<>();
        HashMap<Integer, String> num_query = new HashMap<>();
        if (!withpath) {
            ReadQueries readQueries = new ReadQueries(strings[0]);
            num_query = readQueries.getQueries();
        } else {
            num_query.put(1, strings[0]);
        }
        Iterator iterator = num_query.entrySet().iterator();

        while (iterator.hasNext()) {
            long startTime = System.currentTimeMillis();
            Map.Entry mapElement = (Map.Entry) iterator.next();
            int numQuery = (int) mapElement.getKey();
            String query = (String) mapElement.getValue();
            CorpusDictenory corpusDictenory = CorpusDictenory.getInstance();
            Parse parse = Parse.getInstance();
            parse.initParse(strings[1]);
            String[] splitQuery = query.split("@");
            HashMap<String, Double> wightedQuery = new HashMap<>();
            List<List<String>> splitedQuery = new LinkedList<>();
            for (String str : splitQuery) {
                parse.parseDoc(numQuery + "", str, ifStemmer);
                List<String> list = new LinkedList<>();
                for (String str1 : corpusDictenory.Dictenory.keySet()) {
                    if (str1.equals("CHUNNEL")) {
                        list.add("CHANNEL");
                    }
                    list.add(str1);
                }
                for (String str1 : corpusDictenory.essenceDic.keySet()) {
                    list.add(str1);
                }
                corpusDictenory.Dictenory.clear();
                corpusDictenory.essenceDic.clear();
                corpusDictenory.ArticleInform.clear();
                splitedQuery.add(list);
            }
            boolean flag = true;
            for (List<String> list : splitedQuery) {
                for (String str : list) {
                    if (flag)
                        wightedQuery.put(str, 1.0);
                    else {
                        if (!wightedQuery.containsKey(str))
                            wightedQuery.put(str, 1 * 0.35);
                    }
                }
                flag = false;
            }
            try {
                HashMap<String, Double> query_rank = (HashMap<String, Double>) wightedQuery.clone();
                if (ifSemantic) {
                    query_rank = (HashMap<String, Double>) OfflineSemantic.searchSynonym(query_rank).clone();
                }
                if (UserInterface.cb4.isSelected())

                    query_rank = (HashMap<String, Double>) OnlineSemantic.searchSynonym(query_rank).clone();
                TreeMap<String, String> dic = corpusDictenory.finalDic;
                Collection<String> oldSet = query_rank.keySet();
                TreeSet<String> newSet = new TreeSet<>();
                newSet.addAll(oldSet);
                for (String word : newSet) {
                    if (!(dic.containsKey(word) && dic.containsKey(word.toLowerCase()) && dic.containsKey(word.toUpperCase())))
                        query_rank.remove(word);
                }
                Ranker ranker = new Ranker(UserInterface.path2);
                HashMap<String, Double> hasRank = ranker.rank(query_rank, ifStemmer);
                long stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                System.out.println("querie Number ::" + numQuery + " took ::" + elapsedTime / 1000F);
                List<Map.Entry<String, Double>> list2 =
                        new LinkedList<Map.Entry<String, Double>>(hasRank.entrySet());
                Collections.sort(list2, new Comparator<Map.Entry<String, Double>>() {
                    public int compare(Map.Entry<String, Double> o1,
                                       Map.Entry<String, Double> o2) {
                        return (o2.getValue()).compareTo(o1.getValue());
                    }
                });

                String[] str = new String[50];
                int i = 0;
                for (Map.Entry<String, Double> aa : list2) {
                    str[i] = aa.getKey();
                    i++;
                    if (i == 50)
                        break;
                }
                ConvertDocNum convertDocNum = new ConvertDocNum(str);
                String[] str2 = convertDocNum.convert();
                output.put(numQuery, str2);
            } catch (Exception e) {
            }
        }
        return output;
    }

}
