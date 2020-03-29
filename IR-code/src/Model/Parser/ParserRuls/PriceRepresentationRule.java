package Model.Parser.ParserRuls;

import java.util.ArrayList;

public class PriceRepresentationRule extends ANumberRules {
    /**
     * The method implements a law for expressions containing prices
     * and passes them to defined formats.
     * @param words
     * @param key
     * @param index
     * @return
     */
    public int[] roleChecker(String[] words, String key, int index) {
        results[0] = 0;
        results[1] = 0;
        int previndex=index;
        boolean hasComma = false;
        boolean flag=false;
        double price = 0;
        boolean bMultiplier = false;
        String word = getWord(words,index);
        if(word.equals(""))
            return results;
        if (word.contains(",")) {
            hasComma = true;
            String tempWord = "";
            tempWord = word.replaceAll(",","");
            word = tempWord;
        }
        String tempWord= getWord(words,index);
        if(tempWord.equals(""))
            return results;
        if (index < words.length - 1 && (isNumber(getWord(words, index)))) {
            price = Double.parseDouble(word);
            if (index < words.length - 1) {
                if (getWord(words, index + 1).equals("million") || getWord(words, index + 1).equals("m") || getWord(words, index + 1).equals("Million")) {
                    bMultiplier = true;

                } else if (getWord(words, index + 1).equals("billion") || getWord(words, index + 1).equals("bn") || getWord(words, index + 1).equals("Billion")) {
                    price = price * 1000;
                    bMultiplier = true;
                }
                if (bMultiplier && index < words.length - 2 && getWord(words, index + 2).equals("U.S.")) {
                    index += 2;
                }
                if (index < words.length - 1 && getWord(words, index + 1).contains("/")) {
                    String [] temp = getWord(words, index + 1).split("/");
                    if(temp.length>1&&isNumber(temp[0])&&isNumber(temp[1])) {
                        if(words.length>index+2&&(getWord(words,index+2).toLowerCase().equals("dollars")||getWord(words,index+2).toLowerCase().equals("dollar"))){
                            index += 1;
                            flag = true;
                        }
                    }

                }
                if (word.length() > 2 && index < words.length - 2 && (getWord(words, index + 2).equals("Dollars")) == false && (getWord(words, index + 2).equals("dollars")) == false) {
                    if (index < words.length - 1 && (getWord(words, index + 1).equals("Dollars")) == false && (getWord(words, index + 1).equals("dollars")) == false)
                        return results;
                }
                index++;
            }
        } else if (!getWord(words, index).equals("") && getWord(words, index).charAt(0) == '$' && getWord(words, index).length() > 1 && isNumber(getWord(words, index).substring(1))) {
            price = Double.parseDouble(word.substring(1));

            if (index < words.length - 1) {

                if (getWord(words, index + 1).equals("million") || getWord(words, index + 1).equals("Million")) {
                    bMultiplier = true;
                } else if (getWord(words, index + 1).equals("billion") || getWord(words, index + 1).equals("Billion")) {
                    price = price * 1000;
                    bMultiplier = true;
                } else if (getWord(words, index + 1).equals("trillion") || getWord(words, index + 1).equals("Trillion")) {
                    price = price * 1000000;
                    bMultiplier = true;
                }
            }
        } else
            return results;


        if (bMultiplier)
            index++;
        if (!bMultiplier && price >= 1000000) {
            //make it to its representation
            price = price / 1000000;
            // we keep only 2 digits after the dot.
            int roundOff;
            roundOff = (int) (price * 100);
            price = ((double) roundOff) / 100;
            bMultiplier = true;
        }

        if (bMultiplier) {
            String strPrice2=String.valueOf(price);
            int newPrice=0;
            if(strPrice2.contains(".0")) {
                price = Double.parseDouble(strPrice2);
                newPrice = (int) price;
                addToDictionary(String.valueOf(newPrice) + " M Dollars",key);

            }
            else{

                addToDictionary(String.valueOf(price) + " M Dollars",key);
            }

        } else {
            StringBuilder str = new StringBuilder(String.valueOf(price).length() + 9);
            if (price >= 1000 && hasComma)
            {
                String strPrice=String.valueOf(price);
                if(strPrice.contains(".0")){
                    price = Double.parseDouble(strPrice);
                    int newPrice=(int)price;

                    str.append((String.valueOf(((int) (newPrice / 1000)) + ",")));
                    if (newPrice % 1000 >= 100)
                        str.append((String.valueOf(((newPrice % 1000)) + " Dollars")));
                    else if (newPrice % 100 >= 10)
                        str.append((String.valueOf(("0" + (newPrice % 100)) + " Dollars")));
                    else
                        str.append((String.valueOf(("00" + (newPrice % 100)) + " Dollars")));


                }
                else{
                    str.append((String.valueOf(((int) (price / 1000)) + ",")));
                    if (price % 1000 >= 100)
                        str.append((String.valueOf(((price % 1000)) + " Dollars")));
                    else if (price % 100 >= 10)
                        str.append((String.valueOf(("0" + (price % 100)) + " Dollars")));
                    else
                        str.append((String.valueOf(("00" + (price % 100)) + " Dollars")));
                }

            } else {
                if(flag){
                    String strPrice=String.valueOf(price);
                    if(strPrice.contains(".0")){
                        price = Double.parseDouble(strPrice);
                        int newPrice=(int)price;
                        strPrice=String.valueOf(newPrice);

                    }
                    String fraction= getWord(words,index-1);
                    str.append((strPrice+" " ));
                    str.append((fraction +" Dollars"));
                }
                else{
                    if(getWord(words,previndex).charAt(0)=='$'){
                        String strPrice=String.valueOf(price);
                        if(strPrice.contains(".0")){
                            price = Double.parseDouble(strPrice);
                            int newPrice=(int)price;
                            strPrice=String.valueOf(newPrice);
                            str.append((strPrice + " Dollars"));
                        }
                        else
                            str.append((strPrice + " Dollars"));
                    }
                    else if(getWord(words,previndex+1).toLowerCase().equals("dollars")||getWord(words,previndex+1).toLowerCase().equals("dollar")){
                        str.append(""+(price) + " Dollars");

                    }
                    else
                        return results;
                }



            }
            addToDictionary(String.valueOf(str),key);
        }
        index++;
        results[0]=1;
        results[1]=index-previndex;
        return results;



        }

    }


