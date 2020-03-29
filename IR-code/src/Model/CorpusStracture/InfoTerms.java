package Model.CorpusStracture;

import sample.UserInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 *  Singleton class that reads invertedIndex and documentInfo to produce the data
 *  structures which we generate tf and idf to produce a rating for each word in the document.
 */
public class InfoTerms {
    private static InfoTerms ourInstance = new InfoTerms();
    private HashMap<Integer, Double> infoTermInDoc;
    private HashMap<Integer, Double> infoDocSize;
    private HashMap<Integer, String> infoArticle;

    public static InfoTerms getInstance() {
        return ourInstance;
    }

    private InfoTerms() {
        infoArticle = new HashMap<>();
        try {
            BufferedReader reader;
            String stem = "";
            if (UserInterface.cb1.isSelected())
                stem = "Stem";
            reader = new BufferedReader(new FileReader(UserInterface.path2 + "//articleIndex" + stem + ".txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] splitLine = new String[2];
                line = line.replaceAll(" ", "");
                int index = line.indexOf("-");
                splitLine[0] = line.substring(0, index);
                splitLine[1] = line.substring(index + 1);
                //int old = Integer.parseInt(splitLine[0]);
                infoArticle.put(Integer.parseInt(splitLine[0]), splitLine[1]);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String stem = "";
        if (UserInterface.cb1.isSelected())
            stem = "Stem";
        infoTermInDoc = new HashMap<>();
        infoDocSize = new HashMap<>();
        int counter = 1;
        BufferedReader reader;
        try {
            String[] splitLine;
            reader = new BufferedReader(new FileReader(UserInterface.path2 + "//DocumentInfo" + stem + ".txt"));
            String line = reader.readLine();
            while (line != null) {
                splitLine = line.split("@|\\|\\|");
                double result = Double.parseDouble(splitLine[3]);
                infoDocSize.put(counter, Double.parseDouble(splitLine[1]));
                infoTermInDoc.put(counter, result);
                counter++;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * @param idDoc
     * @return the quantity of the word with the maximum occurrence
     * in the given document.
     */
    public Double getMaxWordInDoc(int idDoc) {
        return infoTermInDoc.get(idDoc);
    }
    /**
     *
     * @param idDoc
     * @return the number of terms in the specific document.
     */
    public Double getDocSize(int idDoc) {
        return infoDocSize.get(idDoc);
    }
    /**
     *
     * @param id
     * @return the actual document name for the temporary number number we gave it.
     */
    public String getConvert(int id) {
        return infoArticle.get(id);
    }
    /**
     *
     * @param docName
     * @return for the original number the temporary name we gave it.
     */
    public String getUnConvert(String docName) {

        for (Map.Entry<Integer, String> entry : infoArticle.entrySet()) {
            if (docName.equals(entry.getValue()))
                return "" + entry.getKey();
        }
        return "";
    }

}
