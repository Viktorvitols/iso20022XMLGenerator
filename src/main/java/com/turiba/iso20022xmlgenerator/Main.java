package com.turiba.iso20022xmlgenerator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/turiba/iso20022xmlgenerator/layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 660, 925);
        stage.setTitle("ISOGen 1.0");
        Image icon = new Image("iso.png");

        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}