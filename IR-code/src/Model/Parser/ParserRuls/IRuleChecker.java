package Model.Parser.ParserRuls;


import java.util.ArrayList;

/**
 * The interface defines a Generic function
 * that the rules in the package need to implement.
 * Every law according to its purpose.
 */
public interface IRuleChecker {

    int[] roleChecker(String[] words, String key,int index);
}
