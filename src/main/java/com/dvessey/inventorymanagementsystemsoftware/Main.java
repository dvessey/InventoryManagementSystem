package com.dvessey.inventorymanagementsystemsoftware;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class of the Inventory Management Application
 * @author Damon Vessey
 */
public class Main extends Application {
    /**
     * start method calls the main screen
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainForm.fxml"));
        // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * main method launches the application
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}