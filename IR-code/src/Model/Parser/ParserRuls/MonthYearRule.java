package Model.Parser.ParserRuls;

import java.util.ArrayList;

import static jdk.nashorn.internal.runtime.JSType.isNumber;

public class MonthYearRule extends ADatesRule {

    /**
     * The method gets various formats of months and years
     * and transforms them into a uniform YYYY-MM format.
     * @param words
     * @param key
     * @param index
     * @return
     */
    public int[] roleChecker(String[] words, String key,int index)
    {
        results[0]=0;
        results[1]=0;
        if(getWord(words,index).endsWith("%")){
            return results;
        }
        if(words.length<2+index)
            return results;
        String month = getWord(words,index).toLowerCase();
        String year =getWord(words,index+1).toLowerCase();
        if(month.equals("")||year.equals(""))
            return results;
        if(!year.matches("^[0-9]{4}")||!month.matches("[a-z,A-Z]+"))
            return results;
        int intYear;
            intYear = Integer.parseInt(year);


        int intMonth=-1;
        String result2;
        if (intYear > 0 && d_months.contains(month))
        {
            intMonth=changeMonthToNumber(month);
            if (intMonth < 10)
                result2 = "0" + intMonth;
            else
                result2 = intMonth + "";
            String result = year + "-" + result2;
            addToDictionary(result,key);
            results[0] = 1;
            results[1] = 2;
            return results;
        }
        return results;
    }

}
