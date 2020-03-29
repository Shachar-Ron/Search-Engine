package Model.Parser.ParserRuls;

import Stemmer.Stemmer;

import java.util.ArrayList;

/**
 * Abstract class for ordinary dictionary words.
 */
public abstract class AWordsRule extends ARuleChecker {
    protected  int[] results = new int[2];
    protected Stemmer stemmer;
    public AWordsRule() {
        results[0] = 0;
        results[1] = 0;
        if (stemmer == null) {// This is the first iteration
            stemmer = new Stemmer();
        }
    }



}
