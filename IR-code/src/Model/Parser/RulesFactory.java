package Model.Parser;

import Model.Parser.ParserRuls.*;

/**
 * The class is implementing the Factory Design Pattern.
 * We use it in the parser class to read the rules for each word.
 */
public class RulesFactory {
    /**
     * The method returns  based on the string it receives from the parser.
     * @param RuleName
     * @return a new rule-type object
     */
    protected static IRuleChecker getRuleChecker(String RuleName){
        if(RuleName.equals("DayMonthRule"))
            return new DayMonthRule();
        else if(RuleName.equals("MothYearRule"))
            return new MonthYearRule();
        else if(RuleName.equals("NumberRepresentationRule"))
            return new NumberRepresentationRule();
        else if(RuleName.equals("PrecentageRepresentationRole"))
            return new PrecentageRepresentationRole();
        else if(RuleName.equals("RangeRule"))
            return new RangeRule();
        else if(RuleName.equals("SingleWordRule"))
            return new SingleWordRule();
        else if(RuleName.equals("PriceRepresentationRule"))
            return new PriceRepresentationRule();
        else if(RuleName.equals("PhoneNumberRepresentationRule"))
            return new PhoneNumberRepresentationRule();
        else if(RuleName.equals("KiloOrMeterRepresentationRule"))
            return new KiloOrMeterRepresentationRule();
        else if(RuleName.equals("ExpressionsRepresentationRule"))
            return new ExpressionsRepresentationRule();
        return null;
    }
}
