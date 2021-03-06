package sample;
import java.text.DecimalFormat;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class TestFile {
    private SimpleStringProperty filename,actualClass;
    private SimpleDoubleProperty  spamProbability;

    public TestFile(String filename, double spamProbability, String actualClass) {
        this.filename = new SimpleStringProperty(filename);
        this.spamProbability = new SimpleDoubleProperty(spamProbability);
        this.actualClass = new SimpleStringProperty(actualClass);
    }

    //Getter Setters
    public String getFilename(){ return filename.get(); }
    public double getSpamProbability(){ return spamProbability.get(); }
    public String getSpamProbRounded(){
        DecimalFormat df = new DecimalFormat("0.00000");
        return df.format(this.spamProbability.get());
    }
    public String getActualClass(){return this.actualClass.get();}
    public void setFilename(String val) { filename = new SimpleStringProperty(val); }
    public void setSpamProbability(double val) { spamProbability = new SimpleDoubleProperty(val); }
    public void setActualClass(String val) { filename = new SimpleStringProperty(val); }
}
