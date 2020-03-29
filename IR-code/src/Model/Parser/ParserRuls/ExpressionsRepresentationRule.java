package Model.Parser.ParserRuls;

import Model.Parser.Parse;


public class ExpressionsRepresentationRule extends AWordsRule {
    /**
     * The method adds expression to the dictionary for phrases
     * in which all words begin with a capital letter.
     * The method adds phrases containing at most 3 words.
     * @param words
     * @param key
     * @param index
     * @return
     */
    public int[] roleChecker(String[] words, String key, int index) {
        results[0] = 0;
        results[1] = 0;
        String checkWord = getWord(words, index);
        if (checkWord.contains("="))
            return results;
        if (!checkWord.equals("") && index + 1 < words.length && checkWord.charAt(0) >= 'A' && checkWord.charAt(0) <= 'Z' && !checkWord.endsWith(".")) {
            String checkWord2 = getWord(words, index + 1);
            if (!checkWord2.equals("") && index + 2 < words.length && checkWord2.charAt(0) >= 'A' && checkWord2.charAt(0) <= 'Z' && !checkWord2.endsWith(".")) {
                String checkWord3 = getWord(words, index + 2);
                if (!checkWord3.equals("") && checkWord3.charAt(0) >= 'A' && checkWord3.charAt(0) <= 'Z' && !checkWord3.endsWith(".")) {
                    //   results[0] = 1;
                    //    results[1] = 3;
                    if (!checkStopWord()) {
                        deleteDelimitors(checkWord);
                        deleteDelimitors(checkWord2);
                        deleteDelimitors(checkWord3);
                        addToessenceDic(checkWord + " " + checkWord2 + " " + checkWord3, key);
                        return results;
                    }
                }
            }
            if (!checkWord2.equals("") &&  checkWord2.charAt(0) >= 'A' && checkWord2.charAt(0) <= 'Z' && !checkWord2.endsWith(".")) {
                // results[0] = 1;
                // results[1] = 2;
                if (!checkStopWord())
                    deleteDelimitors(checkWord);
                deleteDelimitors(checkWord2);
                addToessenceDic(checkWord + " " + checkWord2, key);

            }
            return results;

        }
        return results;
    }

}
