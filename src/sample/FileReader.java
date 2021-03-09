package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.IOException;
import java.util.*;
/*
* FileReader contains method analyze()
* which reads all the files inside training folders
* and counts the words in each file
* loads the data into different TreeMaps
* then it will read the file in the designated directory
* then calculates the probability and extract subjects of emails
* with the formula to count the possibility
* then returns an ObservableList<TestFile>
*/
public class FileReader {
    // initialize map to associates each word to its probability
    Map<String, Double> hamProb = new TreeMap<>();
    Map<String, Double> hamProb2 = new TreeMap<>();
    Map<String, Double> spamProb = new TreeMap<>();
    Map<String, Double> allProb = new TreeMap<>();
    ObservableList<TestFile> result = FXCollections.observableArrayList();;
    File dir;

    FileReader(String directory){
        dir= new File(directory);
    }
    /*
    @ param ham, ham2, spam uses WordCounter to count the occurrences of words
     */
    public ObservableList<TestFile> analyze() throws IOException {
        String title="no subject";
        File[] fileList;
        WordCounter ham = new WordCounter();
        WordCounter ham2 = new WordCounter();
        WordCounter spam = new WordCounter();

        hamProb=ham.parseFile(new File("data/train/ham"));
        hamProb2=ham2.parseFile(new File("data/train/ham2"));
        spamProb=spam.parseFile(new File("data/train/spam"));

        //merge key sets for setting up All-Probability map
        System.out.print("merging key sets\n");
        Set<String> keys1 = hamProb.keySet();
        Set<String> keys2 = spamProb.keySet();
        Set<String> keys3 = hamProb2.keySet();
        Set<String> allKeys = new HashSet<String>() {{
            addAll(keys1);
            addAll(keys2);
            addAll(keys3);
        } };

        //converting word count to probability
        hamProb=ham.calcProb(hamProb);
        hamProb2=ham2.calcProb(hamProb2);
        spamProb=spam.calcProb(spamProb);

        //Merging probability map
        /*
        @param sP : possibility of spam
        @param hP : possibility of ham
        @return follow the formula to get the possibility
         */
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


        //Main loop
        /*
        the following code used to read the files from the
        designated directory with for loop.
        It retrieve the subject, count the words and uses
        spam Formula to determine a file's spam probability
        then store the variables into result (ObservableList<TestFile>)
        @break Avoiding "cmds" files
         */
        fileList = dir.listFiles();
        for(File current: fileList){
            System.out.print("\nFile: "+current.getName()+"\n");

            if(current.getName()=="cmds"){
                break;
            }

            //find title
            /*
            get the subject in each file
             */
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
            /*
            use the scanner to count the words
             */
            Scanner scanner = new Scanner(current);
            Set<String> temp = new HashSet<String>();

            while (scanner.hasNext()){
                String token = scanner.next();
                temp.add(token);
            }

            //calcProb with formula
            Iterator it = temp.iterator();
            double fileProb;
            double n=0.0;
            /*
            *@param part1 follow by the formula given in the assignment
            *@param part2 another formula in the assignment
            *@return the list of all the outcomes for each file
             */
            while(it.hasNext()){
                String token= (String)it.next();
                if(allProb.containsKey(token)){
                    double part1=1.0-allProb.get(token);
                    double part2=allProb.get(token);
                    n+=Math.log(part1)-Math.log(part2);
                }
            }
            fileProb=1.0/(1.0+Math.pow(Math.E,n));
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