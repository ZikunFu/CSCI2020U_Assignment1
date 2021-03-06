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
        //temp.add(new TestFile("100100100", 75.0f, 68.0f,54.25f));

        return temp;
    }

}
