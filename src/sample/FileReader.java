package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.IOException;
import java.util.*;
/*
* This code is used to read all the files and count the words in each file
* offer the data which can be used in formula
* then it will read the file in test
* with the formula to count the possibility
* compare them to get result if it is spam or ham
*/
public class FileReader {
    // set the map for convenience
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
    @ param hamProb, hamProb2, spamProb they are for reading the train folder
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
        the following code used to read the files you chose in directory
        with for loop, get the subject, file name, and count the words
        one by one
        @break when the file name is cmds, it will break
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
            //System.out.print("Counting word\n");
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
            //System.out.print("Calculating probability\n");
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