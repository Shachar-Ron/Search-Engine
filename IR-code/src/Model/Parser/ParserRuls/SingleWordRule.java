package Model.Parser.ParserRuls;


import Model.Parser.Parse;

public class SingleWordRule extends AWordsRule{
    /**
     * The method adds to the dictionary a few words that contain English language letters
     * and makes sure the word is not stopWords.
     * @param words
     * @param key
     * @param index
     * @return
     */
    public int[] roleChecker(String[] words, String key,int index){
        results[0] = 0;
        results[1] = 0;
        String word = getWord(words,index);
        if(((word == null)
                || (word.equals(""))
               || ((!word.matches("[a-zA-Z]+\\'[S,s]"))&&
        (!word.matches("[a-zA-Z]+" )))))
            return results;
        if(checkStopWord()){
            return results;
        }
        results[0] = 1;
        results[1] = 1;
        if(word.toLowerCase().equals("between"))
            return results;
        Parse parse = Parse.getInstance();
        if(parse.withStemming) {
            char firstChar = word.charAt(0);
            word = word.toLowerCase();
            word = stem(word);
            if(firstChar>='A'&&firstChar<='Z')
                word= word.toUpperCase();
        }
        addToDictionaryWord(word,key);
        return results;
    }

    private String stem(String term){
        for(int i=0;i<term.length();i++){
            if(term.charAt(i)!='\'')
            stemmer.add(term.charAt(i));
        }
        String str = stemmer.stem();
        stemmer.clear();
        return str;

    }
}
