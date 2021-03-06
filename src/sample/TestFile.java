package sample;
import java.text.DecimalFormat;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.File;
public class TestFile {
    private SimpleStringProperty filename,actualClass,subject;
    private SimpleDoubleProperty  spamProbability;
    private File file;

    public TestFile(String title, String filename, double spamProbability, String actualClass) {
        this.filename = new SimpleStringProperty(filename);
        this.subject = new SimpleStringProperty(title);
        this.spamProbability = new SimpleDoubleProperty(spamProbability);
        this.actualClass = new SimpleStringProperty(actualClass);
    }

    //Getter Setters
    public String getFilename(){ return filename.get(); }
    public String title(){ return subject.get(); }
    public double getSpamProbability(){ return spamProbability.get(); }
    public String getSpamProbRounded(){
        DecimalFormat df = new DecimalFormat("0.00000");
        return df.format(this.spamProbability.get());
    }
    public String getActualClass(){return this.actualClass.get();}
    public File getFile(){ return file; }
    public void setFilename(String val) { filename = new SimpleStringProperty(val); }
    public void setSpamProbability(double val) { spamProbability = new SimpleDoubleProperty(val); }
    public void setActualClass(String val) { filename = new SimpleStringProperty(val); }
}
