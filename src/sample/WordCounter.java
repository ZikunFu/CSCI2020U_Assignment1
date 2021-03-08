package sample;

import java.io.*;
import java.util.*;

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
/*
    private boolean isValidWord(String word){
        String allLetters = "^[a-zA-Z]+$";
        // returns true if the word is composed by only letters otherwise returns false;
        return word.matches(allLetters);
    }
*/

    private void countWord(String word){
        if(wordCounts.containsKey(word)){
            Double previous = wordCounts.get(word);
            wordCounts.put(word, previous+1);
        }else{
            wordCounts.put(word, 1.0);
        }
    }
    /*
    public void output(int minCount, File dir) throws IOException{
        System.out.println("Saving word counts to file:" + dir.getAbsolutePath());
        System.out.println("Total words:" + wordCounts.keySet().size());

        if (!dir.exists()){
            dir.createNewFile();
            if (dir.canWrite()){
                PrintWriter fileOutput = new PrintWriter(dir);

                Set<String> keys = wordCounts.keySet();
                Iterator<String> keyIterator = keys.iterator();

                while(keyIterator.hasNext()){
                    String key = keyIterator.next();
                    Double count = wordCounts.get(key);
                    // testing minimum number of occurances
                    if(count>=minCount){
                        fileOutput.println(key + ": " + count);
                    }
                }
                fileOutput.close();
            }
        }else{
            System.out.println("Error: the output file already exists: " + dir.getAbsolutePath());
        }
    }*/
}
