package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
public class FileReader {
    String dir;
    FileReader(String directory){
        dir=directory;
    }
    public static ObservableList<TestFile> getDataList(){
        ObservableList<TestFile> temp =
                FXCollections.observableArrayList();//String filename, double spamProbability, String actualClass
        temp.add(new TestFile("subject01", "filename", 0.001,"yes"));
        return temp;
    }

}
