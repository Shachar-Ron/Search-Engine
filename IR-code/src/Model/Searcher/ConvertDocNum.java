package Model.Searcher;

import Model.CorpusStracture.InfoTerms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
/**
 * The class converts the temporary document number that we gave the document
 * to the original document number - for the 50 most relevant documents for that specific query.
 */
public class ConvertDocNum {

    String []str;

    public ConvertDocNum(String [] str){

        this.str=str;
    }
    /**
     *
     * @return an array of the 50 original names of the most relevant documents for a particular query.
     */
    public String [] convert() {
        String [] output = new String[50];
        int i=0;
        InfoTerms infoTerms = InfoTerms.getInstance();
        for(String str : this.str){
            String key = infoTerms.getConvert(Integer.parseInt(str));
            output[i]=key;
            i++;
        }
        return output;
    }
}
