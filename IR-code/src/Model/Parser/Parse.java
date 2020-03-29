
package Model.Parser;
import Model.Parser.ParserRuls.*;
import java.util.ArrayList;
import java.io.IOException;
import java.util.*;
import java.lang.String;
import java.io.File;

/**
 * The super class that governs and applies every word to the rules
 * The department receives a text string and parses it.
 */
public class Parse {
    private static Parse ourInstance = new Parse();
    public static ArrayList<String> hs_stopwords;
    public String[] l_singleWords;
    public int index;
    public boolean withStemming=true;
    public  String  path;
    public boolean ifInit=true;
    public static Parse getInstance() {
        return ourInstance;
    }


    //The constructor
    private Parse() {

    }
    /**
     * The method reads all stopwords from a file structure into a data structure
     * @param path
     */
    public void initParse(String path){
        try {
            Scanner s = new Scanner(new File(path+"\\05 stop_words.txt"));
            hs_stopwords = new ArrayList<>();
            while (s.hasNext()) {
                hs_stopwords.add(s.next());
            }
            s.close();
        } catch (IOException e) {
            // Handle a potential exception
        }
    }

    // Parse the document according to the set of rules given to us in the assignment

    /**
     * The function receives a document (articleKey)
     * and for each term in the document passes it the sequence
     * of rules and eventually inserts it into TreeMap With or without stemming
     * @param articleKey
     * @param txt
     * @param withStemming
     */
    public void parseDoc(String articleKey,String txt, boolean withStemming) {
        this.withStemming = withStemming;
        l_singleWords = txt.split("[\\s\")\\]\\[(><;:?+@|*]+");
        index = 0;
        int[] results = new int[2];
        boolean ifFound;
        String[] rulesToCheack = {"DayMonthRule", "MothYearRule","ExpressionsRepresentationRule", "SingleWordRule","PhoneNumberRepresentationRule", "PriceRepresentationRule", "PrecentageRepresentationRole", "RangeRule","KiloOrMeterRepresentationRule", "NumberRepresentationRule"};
        while (index < l_singleWords.length) {
            if (l_singleWords[index].equals("")) {
                index++;
                continue;
            }
            if(checkStopWord()) {
                index++;
                continue;
            }
            ifFound = false;
            results[0] = 0;
            results[1] = 0;
            for (int i = 0; i < rulesToCheack.length&&index<l_singleWords.length; i++) {
                if (l_singleWords[index].equals("")) {
                    break;
                }
               IRuleChecker Rulechecker = RulesFactory.getRuleChecker(rulesToCheack[i]);
                results = Rulechecker.roleChecker(l_singleWords, articleKey, index);
                if (results[0] == 1) {
                    index += results[1];
                    ifFound = true;
                    break;
                }
            }
            if (ifFound)
                continue;
            else {
                index++;
            }
        }
    }

    /**
     * The method receives word and lowers all irrelevant punctuation.
     * @param word
     * @return
     */
    private String deleteDelimitors(String word) {
        if (word.equals("U.S.")) {
            return word;
        }
        if (word.equals(""))
            return word;
        if (word.charAt(0) == ',') {
            word = word.substring(1);
        }
        if (word.equals(""))
            return word;
        if (word.equals(',') || word.equals('.'))
            return "";
        if (word.charAt(word.length() - 1) == ',' && word.length() != 1) {
            word = word.substring(0, word.length() - 1);
        }
        if (word.equals("") || word.equals('.') || word.equals(','))
            return word;
        if (word.startsWith("-") || word.startsWith(".")) {
            while ((word.startsWith("-") || word.startsWith(".")) && !word.isEmpty()) {
                word = word.substring(1);
            }
        }
        return word;
    }

    /**
     * The method gets a word.
     * @return true if the word is stopWords
     */
    public boolean checkStopWord() {
        String checkWord;
        checkWord = deleteDelimitors(l_singleWords[index]);
        if(l_singleWords[index].equals("May")||l_singleWords[index].equals("between"))
            return false;
        checkWord = checkWord.toLowerCase(); // problem with May and may
        if (hs_stopwords.contains(checkWord)) {
            return true;
        }
        return false;
    }
}





