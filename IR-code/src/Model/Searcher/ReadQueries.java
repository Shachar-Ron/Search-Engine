package Model.Searcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
/**
 * The class returns its query and description for each query from the query file
 * in the format that exists in the model
 */
public class ReadQueries {

    String path;


    public ReadQueries(String path){
        this.path=path;
    }
    /**
     * The method extracts its query and description words and inserts it into
     * a HashMap <Integer, String> data structure -
     * for each query query number that is relevant to it.
     * @return
     */
    public HashMap<Integer,String> getQueries() {
        HashMap<Integer, String> ID_Query = new HashMap<>();
        StringBuilder allFile= new StringBuilder();
        String[] splitLine;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) {
                allFile.append(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        String temp=allFile.toString();
        splitLine = temp.split("<num>|<title>|<desc>|<narr>");
        for(int i=0;i+3<splitLine.length;i+=4){
            String [] numOfQuery= splitLine[i+1].split(": ");
            String [] numOfQuery2=numOfQuery[1].split(" ");
            int num=Integer.parseInt(numOfQuery2[0]);
            String toadd = splitLine[i+3].replaceAll("Identify","");
            ID_Query.put(num,splitLine[i+2]+"@"+toadd);
        }

        return ID_Query;
    }
}
