package Model.Parser.ParserRuls;

public class
PhoneNumberRepresentationRule extends ANumberRules {
    //(703) 733-6097
    //703 733-6097

    /**
     * The method implements a rule we added for expressions containing
     * phone numbers of format (xxx) xxxxxx where x is a number.
     * The law takes care to keep the phone number as one phrase.
     * @param words
     * @param key
     * @param index
     * @return
     */
    public int[] roleChecker(String[] words, String key,int index) {
        results[0] = 0;
        results[1] = 0;
        String checkWord = getWord(words,index);
        if (checkWord.matches("^[0-9]{3}")& index+1<words.length) {
            String checkWord2 = getWord(words,index+1);
            if ((checkWord2.matches("^[0-9]{6}")||checkWord2.matches("^[0-9]{3}[-][0-9]{4}")) ) {
                String tmp = "PN:"+"("+checkWord+") "+checkWord2;
                results[0] = 1;
                results[1] = 2;
                addToDictionary(tmp, key);
                return results;
            }
        }

        return results;
    }
}
