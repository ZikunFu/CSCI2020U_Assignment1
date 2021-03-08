package sample;
import java.text.DecimalFormat;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.File;
/*
* build a class describe the file characteristics
* add the new feature "File file" which can save the file in the class
* can immediately open the file after saving
* return a class
* */
public class TestFile {
    private SimpleStringProperty filename,actualClass,subject;
    private SimpleDoubleProperty  spamProbability;
    private File file;

    // constructor
    public TestFile(String title, String filename, double spamProbability, String actualClass, File file) {
        this.filename = new SimpleStringProperty(filename);
        this.subject = new SimpleStringProperty(title);
        this.spamProbability = new SimpleDoubleProperty(spamProbability);
        this.actualClass = new SimpleStringProperty(actualClass);
        this.file=file;
    }

    //Getter Setters
    public String getFilename(){ return filename.get(); }
    public String getSubject(){ return subject.get(); }
    public double getSpamProbability(){ return spamProbability.get(); }
    public String getSpamProbRounded(){
        DecimalFormat df = new DecimalFormat("0.00000");
        return df.format(spamProbability.get());
    }
    public String getActualClass(){return actualClass.get();}
    public File getFile(){ return file; }
    public void setFilename(String val) { filename = new SimpleStringProperty(val); }
    public void setSpamProbability(double val) { spamProbability = new SimpleDoubleProperty(val); }
    public void setActualClass(String val) { filename = new SimpleStringProperty(val); }
    public String toString()
    {
        return "title: "+subject + " filename: " + file.getName() + "Prob: " + spamProbability + "class: " + actualClass+"\n";
    }
}
