package Model.Indexer;
import Model.CorpusStracture.CorpusDictenory;
import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;


/**
 * The department is responsible for passing all the terms that are received from the parser
 * and inserting them into the posting file and from which
 * to produce the dictionary file.
 */
public class Indexer {
    public int postFileCounter = 1;
    public int counter = 0;
    String path;
    int counterArray;
    int name=400;
    String title;

    public Indexer(String path,boolean withStemming) {
        this.path = path;
        title="";
        if(withStemming)
            title="Stem";
    }

    /**
     * The method receives the data structures created in the parser containing the data
     * about the various terms and documents
     * and produces the posting files of the term and documentInfo
     * @param termDictionary
     * @param docDictionary
     * @throws IOException
     */
    public void createInvertedIndex(TreeMap<String, LinkedList<String>> termDictionary, LinkedHashMap<String, String[]> docDictionary) throws IOException {

        File f = new File(path);
        if (!f.exists() && !f.isDirectory()) {
            path = path + "\\" + postFileCounter + "_Term"+title+".txt";
            File f1 = new File(path);
            f1.getParentFile().mkdirs();
            f1.createNewFile();
        }

        insertTextFirstIterate(termDictionary);
        //save to documentFile information about the file
        savetDocInfo(docDictionary);
    }

    /**
     * The method gets from the createInvertedIndex function the TreeMap created in the parser for the terms
     * and generates for it for all terms a posting file
     * @param termDictionary
     */
    private void insertTextFirstIterate(TreeMap<String, LinkedList<String>> termDictionary) {
        counterArray = 0;
        File f1 = new File(path + "\\" + postFileCounter + "_Term"+title+".txt");
        try {
            FileWriter fw = new FileWriter(f1, true);
            BufferedWriter bw = new BufferedWriter(fw);
            //   PrintWriter out = new PrintWriter(bw);

            for (Map.Entry<String, LinkedList<String>> entry : termDictionary.entrySet()) {
                LinkedList<String> value = entry.getValue();
                String valutToAdd = "";
                for (String str : value) {
                    valutToAdd += str + "#";
                }
                String key = entry.getKey() + "@";
                String toadd = key + valutToAdd;
                if(!toadd.equals(""))
                bw.write(toadd + "\n");

            }

            postFileCounter++;
            bw.close();
        } catch (IOException e1) {
            //exception handling left as an exercise for the reader
        }
    }

    /**
     *The method takes two posting files created in the iterations of the posting process
     *  and merges them into one file using the mergePostingFile focussing.
     */
    public void marge() {
        int level = (postFileCounter - 1);
        int countOfLevel = (int) Math.ceil((Math.log(postFileCounter - 1) / Math.log(2)));
        Queue<String> names = new LinkedList<>();
        for (int i = 0; i < postFileCounter - 1; i++) {
            names.add("" + (i + 1));
        }
        while (names.size() != 1) {
            int numberOfText = names.size();
            int k =0;
            for (int j = 0; j < Math.pow(countOfLevel, 2) / 2; j++) {
                String path1 = "";
                String path2 = "";
                if (k+2 <= numberOfText) {
                    path1 = names.remove();
                    path2 = names.remove();
                    k+=2;
                }

                if (path1.length() != 0 && path2.length() != 0) {
                    String newpath = margePostingFiles(path1, path2,true);
                    names.add(newpath);
                }

            }
            countOfLevel = countOfLevel - 1;
        }
    }

    /**
     * The method accepts paths into two temporary posting files
     * and unifies them into a single posting file.
     * @param pathFileOne
     * @param pathFileTwo
     * @param flag
     * @return
     */
    private String margePostingFiles(String pathFileOne, String pathFileTwo,boolean flag) {
        String newPath;
        name++;
        newPath = path + "\\" + name + "_Term"+title+".txt";
        File f1 = new File(newPath);
        try {
            FileWriter pw = new FileWriter(f1, true);
            // BufferedReader object for file1.txt
            BufferedReader br1 = new BufferedReader(new FileReader(path + "\\" + pathFileOne + "_Term"+title+".txt"));
            BufferedReader br2;
            if(flag)
             br2 = new BufferedReader(new FileReader(path + "\\" + pathFileTwo + "_Term"+title+".txt"));
            else
                br2 = new BufferedReader(new FileReader(path + "\\Entities"+title+".txt"));


            String line1 = br1.readLine();
            String line2 = br2.readLine();
            while (line1 != null && line2 != null) {
                String key1 = line1.substring(0, line1.indexOf('@'));
                String key2 = line2.substring(0, line2.indexOf('@'));

                if (key1.toLowerCase().compareTo(key2.toLowerCase()) > 0) {
                    pw.write(line2 + "\n");
                    line2 = br2.readLine();
                } else if (key1.toLowerCase().compareTo(key2.toLowerCase()) < 0) {
                    pw.write(line1 + "\n");
                    line1 = br1.readLine();
                } else {
                    if (key1.charAt(0) >= 'a' && key1.charAt(0) <= 'z' || key2.charAt(0) >= 'a' && key2.charAt(0) <= 'z')
                        key1 = key1.toLowerCase();
                    String value = key1 + line1.substring(line1.indexOf('@')) + "" + line2.substring(line2.indexOf('@') + 1) + "\n";
                    pw.write(value);
                    line1 = br1.readLine();
                    line2 = br2.readLine();
                }
            }
            while (line1 != null) {
                pw.write(line1 + "\n");
                line1 = br1.readLine();
            }
            while (line2 != null) {
                pw.write(line2 + "\n");
                line2 = br2.readLine();
            }
            pw.close();
            br1.close();
            br2.close();
        } catch (Exception e) {
        }
        File toDelete = new File(path + "\\" + pathFileOne + "_Term"+title+".txt");
        File toDelete2;
        if(flag)
         toDelete2 = new File(path + "\\" + pathFileTwo + "_Term"+title+".txt");
        else
            toDelete2 = new File(path + "\\Entities"+title+".txt");
        toDelete.delete();
        toDelete2.delete();
        return ""+name;
    }

    /**
     * The method gets the TreeMap from the parser that saves the data for each document
     * and creates a posting file containing the information for all documents.
     * @param docDictionary
     */
    private void savetDocInfo(HashMap<String, String[]> docDictionary) {
        Writer writer = null;
        try {
            File f1 = new File(path + "\\ DocumentInfo"+title+".txt");
            FileWriter fw = new FileWriter(f1, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Map.Entry<String, String[]> entry : docDictionary.entrySet()) {
                String key = entry.getKey();
                String[] value = entry.getValue();
                StringBuilder sb = new StringBuilder();
                sb.append(key + "@");
                for (int i = 0; i < value.length; i++) {
                    sb.append(value[i] + "||");
                }
                String toAdd = sb.toString() + "\n";
                bw.write(toAdd);
            }
            bw.close();
            fw.close();
        } catch (Exception e) {

        }

    }

    private TreeMap CreateDic(String path) {
        TreeMap<String,String> dicArray = new TreeMap<>(new Comperator());
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String term = giveTermFromAllLine(line);
                String [] infoValue = (line.substring(line.indexOf('@'))).split("#");
                int counter=0;
                for(String str:infoValue){
                  String [] info = str.split(",");
                  counter+=Integer.parseInt(info[1]) ;
                }
                dicArray.put(term,""+counter);
            }

            bufferedReader.close();
        } catch (Exception e) {

        }
        return dicArray;
    }

    /**
     *
     * @param lineFromOldText
     * @return only the term from the row with all the data on it.
     */
    private String giveTermFromAllLine(String lineFromOldText) {
        String justTerm = "";
        for (int i = 0; i < lineFromOldText.length(); i++) {
            if (lineFromOldText.charAt(i) != '@')
                justTerm = justTerm + lineFromOldText.charAt(i);
            else
                break;
        }
        return justTerm;
    }

    /**
     * The method creates an entity file for all entities that we found in the corpus.
     * @param EntitiesDic
     */
    public void saveEntities(TreeMap<String, LinkedList<String>> EntitiesDic) {
        Writer writer = null;
        //init array of linkedlist for value from treemap
        List<List<String>> listOfEn = new ArrayList<>(EntitiesDic.values());

        //String Path = this.path + "\\Entities.txt";
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path + "\\Entities"+title+".txt"), "utf-8"));
            for (Map.Entry<String, LinkedList<String>> entry : EntitiesDic.entrySet()) {
                String key = entry.getKey();
                LinkedList<String> value = entry.getValue();
                String toAdd = key + "@";
                for(String str : value){
                    toAdd=toAdd+str+"#";
                }

                if (value.size() == 1)
                    continue;
                writer.write(toAdd + "\n");

            }
        } catch (IOException ex) {
            // Report
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
            }
        }
    }

    public void CreateDictionary() {
        counter = 0;
        if(name==400)
            name=1;
        margePostingFiles(""+name,"",false);
      File f = new File(path + "\\" + name + "_Term"+title+".txt");
      f.renameTo(new File(path + "\\" +"InvertedIndex"+title+".txt"));
        TreeMap<String,String> Dic =CreateDic(path + "\\" +"InvertedIndex"+title+".txt");
        CorpusDictenory corpusDictenory = CorpusDictenory.getInstance();
        corpusDictenory.addCorpusDic(Dic);
        SaveDictionary();

    }

    /**
     * The method creates a dictionary.txt file and records
     * all the information that exists in corpusDictionary.
     */
    private void SaveDictionary(){
        int counter=1;
        CorpusDictenory corpusDictenory = CorpusDictenory.getInstance();
        TreeMap<String, String> finalDic = corpusDictenory.finalDic;
        String Path = this.path + "\\dictionary"+title+".txt";
        Writer writer = null;
        File f = new File(Path);
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(Path), "utf-8"));
            for (Map.Entry<String, String> entry : finalDic.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                writer.write(key+">"+value+"|"+counter+ "\n");
                counter++;
            }
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * The method produces a txt file for a dictionary with a document name
     * and the unique key we give it.
     * @param array
     */
    public void writeArticleIndex(ArrayList<String > array){
        String Path = this.path + "\\articleIndex"+title+".txt";
        Writer writer = null;
        File f = new File(Path);
        try {
            int i=1;
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(Path), "utf-8"));
            for (String str : array) {
                String key = ""+i;
                String value = str;
                writer.write(key+"-"+value+ "\n");
                i++;
            }
        } catch (IOException ex) {
            // Report
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
            }
        }
    }
    static class Comperator implements Comparator<String>{
        @Override
        public int compare(String t1,String t2){
            return t1.toLowerCase().compareTo(t2.toLowerCase());
        }
    }
}
