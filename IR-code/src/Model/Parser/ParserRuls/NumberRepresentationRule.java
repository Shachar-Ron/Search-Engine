package Model.Parser.ParserRuls;

import java.util.ArrayList;

public class NumberRepresentationRule extends ANumberRules{
    /**
     * The method implements the law for phrases (number and subsequent word)
     * that contain the words thousand / million / billion
     * and make them a uniform format-number-k / m / b
     * @param words
     * @param key
     * @param index
     * @return
     */
    public int[] roleChecker(String[] words, String key,int index){

        results[0]=0;
        results[1]=0;
    String word = getWord(words,index);
        if (!isNumber(word))
            return results;
    String nextWord = "";
    double number = 0.0;
    String tempWord="";
        if(word.contains(",")){
            tempWord=  word.replaceAll(",","");
            number = Double.parseDouble(tempWord);
    }
       else {
                number = Double.parseDouble(word);
  }
        if (index < words.length- 1)
    nextWord = getWord(words,index+1);

    //check if the next word incrice our number
        if (nextWord.equals("thousand") || nextWord.equals("Thousand"))
    {
        number = number * 1000;
        results[0]=1;
        results[1]=1;
    }
        else if (nextWord.equals("Million") || nextWord.equals("million"))
    {
        number = number * 1000000;
        results[0]=1;
        results[1]=1;
    }
        else if (nextWord.equals("Billion") || nextWord.equals("billion"))
    {
        number = number * 1000000000;
        results[0]=1;
        results[1]=1;
    }

    //check how many digits left to the poiont we have and update the nMultiper according to it
    long longNumber;
    longNumber = (long)number;
    int nMultiplier = 0; //how much we need to incrice the number
    int numOfOcc = String.valueOf(longNumber).length();

        if (numOfOcc <=3)
    {
        number = roundTheDigits(number);
        number = roundTheDigits(number);

        String strPrice=String.valueOf(number);
        if(strPrice.contains(".0")) {
            number = Double.parseDouble(strPrice);
            int newNumber = (int) number;
            addToDictionary(String.valueOf(newNumber),key);
        }
        else{
            addToDictionary(String.valueOf(number),key);
        }

        results[0]=1;
        results[1]++;
        return results;
    }
        if (numOfOcc >3 &&numOfOcc <=6)
    {
        number = number / 1000;
        number = roundTheDigits(number);

        String strPrice=String.valueOf(number);
        if(strPrice.contains(".0")) {
            number = Double.parseDouble(strPrice);
            int newNumber = (int) number;
            addToDictionary(String.valueOf(newNumber) + 'K',key);
        }
        else{
            addToDictionary(String.valueOf(number)+ 'K',key);
        }


        results[0]=1;
        results[1]++;
        return results;
    }
        else if (numOfOcc >6 &&numOfOcc <=9)
    {
        number = number / 1000000;
        number = roundTheDigits(number);

        String strPrice=String.valueOf(number);
        if(strPrice.contains(".0")) {
            number = Double.parseDouble(strPrice);
            int newNumber = (int) number;
            addToDictionary(String.valueOf(newNumber) + 'M',key);
        }
        else{
            addToDictionary(String.valueOf(number)+ 'M',key);
        }


        results[0]=1;
        results[1]++;
        return results;
    }
        else if (numOfOcc >= 10)
    {
        number = number / 1000000000;
        number = roundTheDigits(number);

        String strPrice=String.valueOf(number);
        if(strPrice.contains(".0")) {
            number = Double.parseDouble(strPrice);
            int newNumber = (int) number;
            addToDictionary(String.valueOf(newNumber) + 'B',key);
        }
        else{
            addToDictionary(String.valueOf(number)+ 'B',key);
        }

        results[0]=1;
        results[1]++;
        return results;
    }
        results[0]=1;
        results[1]++;
        return results;

}
    private double roundTheDigits(double toRound)
    {
        int help;
        help = (int)(toRound * 100);
        toRound = (double)help / 100;
        return toRound;
    }

}
