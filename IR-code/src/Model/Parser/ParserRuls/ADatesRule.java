package Model.Parser.ParserRuls;

import java.util.ArrayList;
import java.util.Arrays;

public abstract  class ADatesRule extends ARuleChecker {


    protected static ArrayList<String> d_months;
    protected  int[] results = new int[2];


    public ADatesRule(){
        d_months= new ArrayList<String >( Arrays.asList("january","february","march","april","may","june","july","august",

                "september","october","november","december","jan","feb","mar","apr","may","jun","jul","aug","sep","oct","nov","dec"));
        results[0] = 0;
        results[1] = 0;
    }

    /**
     *
     *
     * @param str month's name
     * @return the number that the month indicates
     */
    protected static int changeMonthToNumber(String str) {
        if(str.toLowerCase().equals("january")||str.equals("jan")){
            return 1;
        }
        else if(str.toLowerCase().equals("february")||str.equals("feb")){
            return 2;
        }
        else if(str.toLowerCase().equals("march")||str.equals("mar")){
            return 3;
        }
        else if(str.toLowerCase().equals("april")||str.equals("apr")){
            return 4;
        }
        else if(str.toLowerCase().equals("may")){
            return 5;
        }
        else if (str.toLowerCase().equals("june")||str.equals("jun")){
            return 6;
        }
        else if(str.toLowerCase().equals("july")||str.equals("jul")){
            return 7;
        }
        else if(str.toLowerCase().equals("august")||str.equals("aug")){
            return 8;
        }
        else if(str.toLowerCase().equals("september")||str.equals("sep")){
            return 9;
        }
        else if(str.toLowerCase().equals("october")||str.equals("oct")){
            return 10;
        }
        else if(str.toLowerCase().equals("november")||str.equals("nov")){
            return 11;
        }
        else if(str.toLowerCase().equals("december")||str.equals("dec")){
            return 12;
        }
        return -1;

    }


    /**
     *
     * @param str String
     * @return if str is number
     */
    protected static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }

    }

    protected  static boolean NumberCheck(String str){
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)<'0'||str.charAt(i)>'9')
                return false;
            if(i>3)
                return false;
        }
        return true;
    }

}
