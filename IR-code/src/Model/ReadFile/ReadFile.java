package Model.ReadFile;
//////////////////////////////////////

import Model.CorpusStracture.CorpusDictenory;
import Model.Indexer.Indexer;
import Model.Indexer.UnPackingInvertedIndex;
import Model.Parser.Parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The class is responsible for reading the texts from the corpus files.
 */
public class ReadFile {

    Indexer index;
    LinkedHashMap<String, String[]> ArticleDic;
    int numOfArticls=0;
    ArrayList<String> ArticleIndex = new ArrayList<>();

    public ReadFile(String path,boolean withStem) {
        ArticleDic = new LinkedHashMap<>();
        index = new Indexer(path,withStem);
    }

    /**
     * The method gets a path string and reads what corpus the texts
     * and passes them to parser and then sends them to indexer.
     * @param path
     * @param withStemming
     */
    public void readFile(String path,boolean withStemming) {
        int counter = 0;
        String dancoBuffer = "";
        boolean ifwritetxt = false;
        String txtBuffer = "";
        String title="";
        int i = 1;
        boolean flag = false;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) { // Entering to main folder
            counter++;
            if (file.getName().equals("05 stop_words.txt"))

                continue;
            File subfolder = new File(path + "\\" + file.getName());//+
            File[] listOfsubFiles = subfolder.listFiles();
            for (File subfile : listOfsubFiles) {
                flag = true;
                if (subfile.isFile()) {
                    try {
                        BufferedReader in = new BufferedReader(new FileReader(subfile.toString()));
                        String str;
                        while ((str = in.readLine()) != null) { // reading the file line by line
                            if(str.contains("<TI>"))
                                title=str.replaceAll("\\<.*?\\>", "").replaceAll("  ","");
                            if (str.startsWith("<DOCNO>"))
                                dancoBuffer += str;
                            if (str.equals("</TEXT>"))
                                ifwritetxt = false;
                            if (ifwritetxt && str.length() > 0 && !str.matches("^[<]{1}.*[>]$")) {
                                txtBuffer += str + "\n";
                            }
                            if (ifwritetxt && str.matches("^[<]{1}.*[>].*[<]{1}.*[>]$")) {
                                str = str.replaceAll("\\<.*?\\>", "");
                                txtBuffer += str + "\n";
                            }
                            if (str.equals("<TEXT>"))
                                ifwritetxt = true;

                            if (str.equals("</DOC>")) {
                                dancoBuffer = dancoBuffer.substring(dancoBuffer.indexOf("<DOCNO>"), dancoBuffer.lastIndexOf("</DOCNO>"));
                                dancoBuffer = dancoBuffer.substring(7);
                                Parse parse = Parse.getInstance();
                                parse.initParse(path);
                                ArticleIndex.add(dancoBuffer);
                                parse.parseDoc("" + i, txtBuffer,withStemming);
                                CorpusDictenory aaa = CorpusDictenory.getInstance();
                                String [] toInsert ;
                                toInsert = aaa.getArticlea();
                                toInsert[3] = title;
                                ArticleDic.put(""+i, toInsert);
                                dancoBuffer = "";
                                txtBuffer = "";
                                numOfArticls++;
                                i++;
                            }
                        }
                        in.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }
            try {
                if (counter == 8) {
                    CorpusDictenory aaa = CorpusDictenory.getInstance();
                    index.createInvertedIndex(aaa.getDictenery(), ArticleDic);
                    ArticleDic.clear();
                    flag = false;
                    counter = 0;
                }
            } catch (Exception e) {

            }
        }
        if (flag == true) {
            try {
                CorpusDictenory aaa = CorpusDictenory.getInstance();
                index.createInvertedIndex(aaa.getDictenery(), ArticleDic);
                ArticleDic.clear();
            } catch (Exception e) {
            }
        }
        index.writeArticleIndex(ArticleIndex);
        index.marge();
        index.saveEntities(CorpusDictenory.getInstance().getEssenceDic());
        index.CreateDictionary();
        ArticleDic.clear();
        ArticleIndex.clear();
    }

    /**
     *
     * @returnThe the amount of articles that the ReadFile method has read.
     */
    public int getNumOfArticls(){
        return numOfArticls;
    }
}
