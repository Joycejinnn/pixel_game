package com.example.javagame.Login;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.module.ModuleDescriptor;

public class LoginApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        //use css to find the module and design the function
        TextField textField = (TextField) root.lookup(".text-field");
        Button btn1 = (Button) root.lookup("#exitBtn");
        btn1.setOnAction(actionEvent -> stage.close());
        Button btn2 = (Button) root.lookup("#signupBtn");
        btn2.setOnAction(actionEvent -> {
            try {
                Parent root1 = FXMLLoader.load(getClass().getResource("/fxml/SignUp.fxml"));
                TextField textField1 = (TextField)root1.lookup(".text-field");
                Button button = (Button)root1.lookup("#exitBtn");
                button.setOnAction(actionEvent1 -> {
                    stage.close();
                });
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        stage.setTitle("Java Game");
        stage.setScene(new Scene(root));
        stage.show();
    }




    public static void main(String[] args) {
        launch();
    }
}