package sample;

import java.io.*;
import java.util.*;
/*
this code is the word counter to count the number of each word appearing in each file
reference is the tutorial from the module 5
 */
public class WordCounter{
    File[] content;
    private Map<String, Double> wordCounts;

    public WordCounter(){
        wordCounts = new TreeMap<>();
    }
// count the probability of the words
    public Map<String, Double> calcProb(Map<String, Double> map){
        Map<String, Double> result = new TreeMap<String, Double>();
        Set<String> keys = map.keySet();
        Iterator<String> keyIterator = keys.iterator();
        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            result.put(key,map.get(key)/ content.length);
        }
        return result;
    }
    /*
    read the files until it reads the file called cmds
    and it will return null
    *@param content the lists of the file
    *@return the words count
     */
    public Map<String, Double> parseFile(File file) throws IOException{
        if(file.getName()=="cmds"){
            return null;
        }
        if(file.isDirectory()){
            //parse each file inside the directory
            content = file.listFiles();
            for(File current: content){
                parseFile(current);
            }
        }else{
            Scanner scanner = new Scanner(file);
            // scanning token by token
            while (scanner.hasNext()){
                String  token = scanner.next();
                countWord(token);
            }
        }
        return wordCounts;
    }

    /*
    * this is used to count the words
    * count the total of the counts
    * */
    private void countWord(String word){
        if(wordCounts.containsKey(word)){
            Double previous = wordCounts.get(word);
            wordCounts.put(word, previous+1);
        }else{
            wordCounts.put(word, 1.0);
        }
    }

}
