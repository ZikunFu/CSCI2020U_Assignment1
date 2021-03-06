package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
public class Main extends Application {
    String location;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene1, scene2;
        primaryStage.setTitle("Spam Buster");

        //Scene 1
        Label s1_l1 = new Label("Directory");
        TextField directory = new TextField();
        directory.setPrefWidth(350);
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));

        Button s1_b1 =new Button("Browse");
        s1_b1.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            directory.setText(selectedDirectory.getAbsolutePath());
            location=selectedDirectory.getAbsolutePath();
            primaryStage.setTitle("Spam Buster - "+location);
        });

        //Scene2
        FileReader f1 = new FileReader(location);
        ObservableList Data = f1.getDataList();

        TableView tableView = new TableView();
        tableView.setItems(Data);
        TableColumn col1 = new TableColumn("Subject");
        col1.setCellValueFactory(new PropertyValueFactory("subject"));
        TableColumn col2 = new TableColumn("Class");
        col2.setCellValueFactory(new PropertyValueFactory("actualClass"));
        TableColumn col3 = new TableColumn("Probability");
        col3.setCellValueFactory(new PropertyValueFactory("spamProbability"));
        TableColumn col4 = new TableColumn("File Name");
        col4.setCellValueFactory(new PropertyValueFactory("filename"));

        tableView.getColumns().setAll(col1, col2,col3,col4);
        tableView.setPrefWidth(700);
        tableView.setPrefHeight(600);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Scene Configuration
        BorderPane bPane = new BorderPane();
        bPane.setCenter(tableView);
        scene2 = new Scene(bPane);

        Button s1_b2= new Button("Analyze");
        s1_b2.setMaxSize(400, 200);
        s1_b2.setOnAction(e -> primaryStage.setScene(scene2));

        GridPane gridPane = new GridPane();
        gridPane.add(s1_l1, 0, 0, 1, 1);
        gridPane.add(directory, 1, 0, 1, 1);
        gridPane.setHgap(5);
        gridPane.add(s1_b1, 2, 0, 1, 1);
        gridPane.add(s1_b2, 1, 2, 1, 1);
        scene1= new Scene(gridPane, 470, 60);

        //Stage
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
