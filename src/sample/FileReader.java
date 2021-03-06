package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.FilenameFilter;

public class FileReader {
    String dir;
    FileReader(String directory){
        dir=directory;
    }
    public ObservableList<TestFile> getDataList(){
        ObservableList<TestFile> temp =
                FXCollections.observableArrayList();//String filename, double spamProbability, String actualClass
        temp.add(new TestFile("subject01", "00006.654c4ec7c059531accf388a807064363", 0.001,"yes"));
        return temp;
    }

}
