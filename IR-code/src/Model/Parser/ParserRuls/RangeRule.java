package Model.Parser.ParserRuls;

import java.util.ArrayList;

public class RangeRule extends ANumberRules {
    /**
     *The method implements a law for phrases that include "-"
     *or for "Between x and x" phrases and saves them as a single term.
     * @param words
     * @param key
     * @param index
     * @return
     */
    public int[] roleChecker(String [] words, String key,int index)
    {
        //We will now examine the word
        results[0]=0;
        results[1]=0;
        String word = getWord(words,index).toLowerCase();
        String result = "";
        //If the word is between, just like in the stopwords check, and there is a format of a range following it, we need to add it here.
       if (word == "between" &&  3 < words.length )
       {
            if(isNumeric(getWord(words,index+1))&&isNumeric(getWord(words,index+3))){

                if(getWord(words,index+2).toLowerCase().equals("and")){

                    double num1 = Double.parseDouble(getWord(words,index+1));
                    double num3 = Double.parseDouble(getWord(words,index+3));
                    result = num1 + "-" + num3;
                   results[0]=1;
                   results[1]=4;

                }
            }
        }

        else if ((word.matches("^[a-zA-Z]*[\\-]{1}[a-zA-Z]+\\-[a-zA-Z]+$"))||((word.matches("^[a-zA-Z]+\\-{1}[a-zA-Z]+$"))))
        {
            results[0] = 1;
            results[1] = 1;
            result=getWord(words,index);
        }

        if (results[1] == 0)
            return results;
        addToDictionary(result,key);
        return results;
    }

}
