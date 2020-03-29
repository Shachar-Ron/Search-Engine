package Model.Searcher;
import Model.CorpusStracture.InfoTerms;

import java.util.*;
import java.lang.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * The class returns the 5 Entities that exist most in a particular document.
 */
public class Entities {


    String path;
    String name;

    public Entities(String path, String name) {
        this.path = path;
        this.name = name;

    }
    /**
     * The method get the Dictionary and Indexer and for a specific document
     * returns an array of the five entities with the most number of occurrences in the document.
     * @return
     */
    public String[] get5EntitiesForEachDoc() {

        String nameOfDoc=name;
        String[] top5Entities = new String[5];

        HashMap<String, Double> topEntities = new HashMap<>();

        int intDoc = Integer.parseInt(nameOfDoc);
        InfoTerms infoTerms = InfoTerms.getInstance();
        double numof = infoTerms.getDocSize(intDoc);

        String[] splitLine;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path + "//InvertedIndex.txt"));
            String line = reader.readLine();
            while (line != null) {
                splitLine = line.split("@|#");
                String term=splitLine[0];
                boolean flag = false;
                if(!term.contains(" ")) {
                    for (int i=0;i<term.length();i++){
                        if((!(term.charAt(i)>='A'&&term.charAt(0)<='Z'))){
                            flag=true;
                            break;
                        }
                    }
                }
                if(flag) {
                    line = reader.readLine();
                    continue;
                }
                    int DocNumber = 0;
                for (int j = 1; j < splitLine.length; j++) {
                    String[] splitDoc = splitLine[j].split(",");
                    DocNumber = Integer.parseInt(splitDoc[0]);
                    int numOccInDoc = Integer.parseInt(splitDoc[1]);
                    if (intDoc == DocNumber) {
                        double sum = numOccInDoc/numof;
                        topEntities.put(term+"::"+sum, sum);
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Object[] a = topEntities.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Double>) o2).getValue()
                        .compareTo(((Map.Entry<String, Double>) o1).getValue());
            }
        });
        int i=0;
        for (Object e : a) {
            if(i>=5){
                break;
            }
            top5Entities[i]=(((Map.Entry<String, Integer>) e).getKey());
            System.out.println(  top5Entities[i]+" - "+ topEntities.get( top5Entities[i]));
            i++;
        }

        return top5Entities;


    }
}

