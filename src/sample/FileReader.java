package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileReader {
    Map<String, Double> hamProb = new TreeMap<>();
    Map<String, Double> hamProb2 = new TreeMap<>();
    Map<String, Double> spamProb = new TreeMap<>();
    Map<String, Double> allProb = new TreeMap<>();
    ObservableList<TestFile> result = FXCollections.observableArrayList();;
    File dir;

    FileReader(String directory){
        dir= new File(directory);
    }

    public ObservableList<TestFile> analyze() throws IOException {
        String title="no subject";
        File[] fileList;
        WordCounter ham = new WordCounter();
        WordCounter ham2 = new WordCounter();
        WordCounter spam = new WordCounter();
        hamProb=ham.parseFile(new File("C:/Users/Voice/IdeaProjects/2020U_Assignment1/data/train/ham"));
        hamProb2=ham2.parseFile(new File("C:/Users/Voice/IdeaProjects/2020U_Assignment1/data/train/ham2"));
        spamProb=spam.parseFile(new File("C:/Users/Voice/IdeaProjects/2020U_Assignment1/data/train/spam"));

        //merge key sets
        System.out.print("merging key sets\n");
        Set<String> keys1 = hamProb.keySet();
        Set<String> keys2 = spamProb.keySet();
        Set<String> keys3 = hamProb2.keySet();
        Set<String> allKeys = new HashSet<String>() {{
            addAll(keys1);
            addAll(keys2);
            addAll(keys3);
        } };

        //converting wordcount to probability
        hamProb=ham.calcProb(hamProb);
        hamProb2=ham2.calcProb(hamProb2);
        spamProb=spam.calcProb(spamProb);

        //Merging probability map
        System.out.print("Merging Probability map\n");
        Iterator<String> iter = allKeys.iterator();
        while(iter.hasNext()){
            String key = iter.next();
            double sP,hP;
            if(hamProb.containsKey(key)&&spamProb.containsKey(key)){
                sP=spamProb.get(key);
                hP=hamProb.get(key);
                allProb.put(key,(sP/(hP+sP)));
            }
            else if(hamProb2.containsKey(key)&&spamProb.containsKey(key)){
                sP=spamProb.get(key);
                hP=hamProb2.get(key);
                allProb.put(key,(sP/(hP+sP)));
            }
        }

        /*
        System.out.print("Printing All-Probability map: \n");
        for (Map.Entry<String,Double> token : allProb.entrySet()){
            System.out.print(token.getKey()+" "+token.getValue()+"\n");
        }
         */

        //Main loop
        fileList = dir.listFiles();
        for(File current: fileList){
            //System.out.print("\nFile: "+current.getName()+"\n");

            if(current.getName()=="cmds"){
                break;
            }

            //find title
            Scanner linescan = new Scanner(current);
            while (linescan.hasNextLine()){
                String tempS = linescan.nextLine();
                if(tempS.indexOf("Subject:")!=-1){
                    String[] line = tempS.split("Subject:");
                    title=line[1];
                    //System.out.print("Subject: "+title+"\n");
                    break;
                }
            }

            //count word in each file
            //System.out.print("Counting word\n");
            Scanner scanner = new Scanner(current);
            Set<String> temp = new HashSet<String>();

            while (scanner.hasNext()){
                String token = scanner.next();
                temp.add(token);
            }

            //calcProb with formula
            //System.out.print("Calculating probability\n");
            Iterator it = temp.iterator();
            double fileProb;
            double n=0.0;

            while(it.hasNext()){
                String token= (String)it.next();
                if(allProb.containsKey(token)){
                    double part1=1.0-allProb.get(token);
                    double part2=allProb.get(token);
                    n+=Math.log(part1)-Math.log(part2);
                    //System.out.print(" 1-Pr(S|W)= "+ part1 + " Pr(S|W): "+part2+" n: "+n+"\n");
                }
            }
            fileProb=1.0/(1.0+Math.pow(Math.E,n));
            //System.out.print("Words matched in map: "+ c + " n: "+n+" probability: "+fileProb);
            String aClass="ham";

            //tag Class
            if(fileProb>=0.5){
                aClass="spam";
            }

            //add Data to list
            result.add(new TestFile(title, current.getName(), fileProb,aClass,current));
        }
        return result;
    }
}