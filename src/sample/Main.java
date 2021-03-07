package sample;

import javafx.application.Application;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class Main extends Application {
    String location;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Scene scene1, scene2;
        primaryStage.setTitle("Spam Buster");

        //Scene 1
        Label s1_l1 = new Label("Directory:");
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
        Button s1_b2= new Button("Analyze");

        //Scene2
        FileReader f1 = new FileReader(location);
        ObservableList<TestFile> Data = f1.getDataList();

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
        tableView.getStylesheets().add("sample/resources/table-styles.css");
        tableView.setPrefWidth(700);
        tableView.setPrefHeight(600);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button s2_b1 = new Button("Open File");
        s2_b1.setOnAction(e->{
            ObservableList<TestFile> selectedRow= tableView.getSelectionModel().getSelectedItems();
            try {
                Desktop.getDesktop().open(selectedRow.get(0).getFile());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        Button s2_b2 = new Button("Open Directory");
        s2_b2.setOnAction(e->{
            try {
                Runtime.getRuntime().exec("explorer.exe /select," + location);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        Button s2_b3 = new Button("Export");
        s2_b3.setOnAction(e->{
            Exporter e1 = new Exporter(Data);
            try {
                e1.writeCSV();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("result.csv created in project directory");
            a.show();
        });
        Button s2_b4 = new Button("Back");

        //Scene Configuration
        VBox s2_v1 = new VBox(s2_b4,s2_b3);
        HBox s2_h1 = new HBox(s2_b1,s2_b2);
        s2_v1.setSpacing(20);
        BorderPane bPane = new BorderPane();
        bPane.setCenter(tableView);
        bPane.setRight(s2_v1);
        bPane.setBottom(s2_h1);
        scene2 = new Scene(bPane);
        s1_b2.setMaxSize(400, 200);
        s1_b2.setOnAction(e -> primaryStage.setScene(scene2));

        GridPane gridPane = new GridPane();
        gridPane.add(s1_l1, 0, 0, 1, 1);
        gridPane.add(directory, 1, 0, 1, 1);
        gridPane.setHgap(5);
        gridPane.add(s1_b1, 2, 0, 1, 1);
        gridPane.add(s1_b2, 1, 2, 1, 1);
        scene1= new Scene(gridPane, 490, 70);

        s2_b4.setOnAction(e->primaryStage.setScene(scene1));
        scene1.getStylesheets().add("sample/resources/scene1-styles.css");
        scene2.getStylesheets().add("sample/resources/scene2-styles.css");
        s1_b1.getStylesheets().add("sample/resources/button1-styles.css");
        s1_b2.getStylesheets().add("sample/resources/button2-styles.css");
        s2_b1.getStylesheets().add("sample/resources/button2-styles.css");
        s2_b2.getStylesheets().add("sample/resources/button2-styles.css");
        s2_b3.getStylesheets().add("sample/resources/button2-styles.css");
        s2_b4.getStylesheets().add("sample/resources/button2-styles.css");
        //Stage
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
