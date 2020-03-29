package Model.Parser.ParserRuls;

import Model.CorpusStracture.CorpusDictenory;
import Model.Parser.Parse;

/**
 * An abstract class of general law that implements
 * the IRuleChecker interface.
 */
public abstract class ARuleChecker implements IRuleChecker {
    /**
     * The method adds a term that complies
     * with one of the number laws.
     * @param toAdd
     * @param key
     */
    protected void addToDictionary(String toAdd, String key) {
        CorpusDictenory dictenory = CorpusDictenory.getInstance();
        dictenory.addNumber(toAdd, key);
    }

    /**
     * The method adds to the dictionary term
     * that meets one of the rules of words.
     * @param toAdd
     * @param key
     */
    protected void addToDictionaryWord(String toAdd, String key) {
        CorpusDictenory dictenory = CorpusDictenory.getInstance();
        dictenory.addWord(toAdd, key);
    }

    /**
     * The method adds a new entity to the dictionary.
     * @param toAdd
     * @param key
     */
    protected void addToessenceDic(String toAdd, String key) {
        CorpusDictenory dictenory = CorpusDictenory.getInstance();
        dictenory.addessenceDic(toAdd, key);
    }


    protected String getWord(String[] words, int i) {
        String word = words[i];
        word = deleteDelimitors(word);
        words[i] = word;
        return word;

    }

    public String deleteDelimitors(String word) {
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

    public boolean checkStopWord() {
        String checkWord;
        Parse par = Parse.getInstance();
        checkWord = deleteDelimitors(par.l_singleWords[par.index]);
        checkWord = checkWord.toLowerCase(); // problem with May and may
        if (par.hs_stopwords.contains(checkWord)) {
            return true;
        }
        return false;
    }
}

