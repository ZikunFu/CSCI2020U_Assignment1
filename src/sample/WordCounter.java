package sample;

import java.io.*;
import java.util.*;

/*
 * Credit: WordCounter was modified based on Professor Mariana Shimabukuro
 * With addition of method calcProb which receive map of word occurences
 * and divide by the number of files contained in the directory
 * Also minor adjustment of variables from type int to type double
 * */

public class WordCounter{
    File[] content;
    private Map<String, Double> wordCounts;

    public WordCounter(){
        wordCounts = new TreeMap<>();
    }

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

    private void countWord(String word){
        if(wordCounts.containsKey(word)){
            Double previous = wordCounts.get(word);
            wordCounts.put(word, previous+1);
        }else{
            wordCounts.put(word, 1.0);
        }
    }

}
