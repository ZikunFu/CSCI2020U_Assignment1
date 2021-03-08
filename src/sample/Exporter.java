package sample;

import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;
//this code is used to save data in csv file
/*
*fill all the data in a csv file called result.csv
*return a full csv file
 */
public class Exporter {
    ObservableList<TestFile> list;
    Exporter(ObservableList<TestFile> list){ this.list=list; }

    public void writeCSV() throws IOException {
        FileWriter csvWriter = new FileWriter("result.csv");
        csvWriter.append("Subject");
        csvWriter.append(",");
        csvWriter.append("Class");
        csvWriter.append(",");
        csvWriter.append("Probability");
        csvWriter.append(",");
        csvWriter.append("File Name");
        csvWriter.append("\n");
        /*
        fill the data with for-loop in the .csv file
         */
        for(int i=0;i< list.size();i++){
            csvWriter.append(list.get(i).getSubject());
            csvWriter.append(",");
            csvWriter.append(list.get(i).getActualClass());
            csvWriter.append(",");
            csvWriter.append(list.get(i).getSpamProbRounded());
            csvWriter.append(",");
            csvWriter.append(list.get(i).getFilename());
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }
}
