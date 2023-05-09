package com.example.javagame.Login;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends FXGLMenu {
    Button button;

    public Login() {
        super(MenuType.GAME_MENU);
        button = new Button("button");

        GridPane gridPane = new GridPane();

        gridPane.setLayoutX(168);
        gridPane.setLayoutY(200);

        gridPane.setPadding(new Insets(30, 30, 30, 30));
        gridPane.setVgap(40);
        gridPane.setHgap(20);
        gridPane.getStyleClass().add("grid-pane");

        //title
        Label title = new Label("ESCAPE FROM THE LAB");
        title.getStyleClass().add("title");

        //name label
        Label nameLabel = new Label("username:");
        nameLabel.getStyleClass().add("label");
        GridPane.setConstraints(nameLabel, 0, 1);

        //name input
        TextField nameInput = new TextField();
        nameInput.setPromptText("username");
        nameInput.setPrefSize(140, 50);
        GridPane.setConstraints(nameInput, 1, 1);

        //pass label
        Label passLabel = new Label("password:");
        passLabel.getStyleClass().add("label");
        GridPane.setConstraints(passLabel, 0, 2);

        //pass input
        TextField passInput = new TextField();
        passInput.setPromptText("password");
        passInput.setPrefSize(140, 50);
        GridPane.setConstraints(passInput, 1, 2);

        Button loginButton = new Button("log in");
        loginButton.setOnAction(e -> {
            FXGL.getGameController().startNewGame();
        });
        loginButton.getStyleClass().add("menu-button");
        GridPane.setConstraints(loginButton, 0, 3);

        Button exitButton = new Button("exit");
        exitButton.setOnAction(e -> {
            FXGL.getGameController().exit();
        });
        exitButton.getStyleClass().add("menu-button");
        GridPane.setConstraints(exitButton, 1, 3);

        gridPane.getChildren().addAll(
                 title, nameLabel, nameInput, passLabel, passInput, loginButton, exitButton);


        getContentRoot().getChildren().addAll(gridPane);
    }

}