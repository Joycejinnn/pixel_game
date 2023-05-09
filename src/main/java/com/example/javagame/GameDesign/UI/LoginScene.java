package com.example.javagame.GameDesign.UI;

import com.almasb.fxgl.app.MainWindow;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.example.javagame.GameDesign.DB.DB;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class LoginScene extends FXGLMenu {

    Button button;
    private final Pane defaultPane;
    private String genderChose;

    //check whether the user has signed up or not
    private boolean verify(String username, String password) throws SQLException {
        DB db = new DB();
        db.getConnection();
        //DB.getInstance();

        String sql = "select count(1) count from `user` where username = ? and password = ?";
        List<String> vars = new ArrayList<>();
        vars.add(username);
        vars.add(password);
        try {
            return db.count(sql, vars) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
        return false;
    }

    private boolean exists(String username) throws SQLException {
        DB db = new DB();
        db.getConnection();
        //DB.getInstance();

        String sql = "select count(1) count from `user` where username = ?";
        List<String> vars = new ArrayList<>();
        vars.add(username);
        try {
            return db.count(sql, vars) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
        return false;
    }

    private boolean signUp(String username, String password, String gender) throws SQLException {
        DB db = new DB();
        db.getConnection();
        //DB.getInstance();

        String sql = "insert into `user` (username, password, gender) values (?,?,?)";
        List<String> vars = new ArrayList<>();
        vars.add(username);
        vars.add(password);
        vars.add(gender);
        try {
            return db.insert(sql, vars) == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
        return false;
    }

    public LoginScene() {
        super(MenuType.MAIN_MENU);


        button = new Button("button");

        GridPane signUpBox = new GridPane();
        //signUpBox.setAlignment(Pos.CENTER_LEFT);
        signUpBox.setLayoutX(100);
        signUpBox.setLayoutY(90);
        signUpBox.setVisible(false);
        signUpBox.setHgap(10);
        signUpBox.setVgap(40);

        signUpBox.setPadding(new Insets(30, 30, 30, 30));

        GridPane loginBox = new GridPane();
        //loginBox.setAlignment(Pos.CENTER_LEFT);
        loginBox.setLayoutX(100);
        loginBox.setLayoutY(120);
        loginBox.setVisible(true);

        loginBox.setPadding(new Insets(30, 30, 30, 30));
        loginBox.setVgap(40);
        loginBox.setHgap(10);
        loginBox.getStyleClass().add("grid-pane");

        //title
        Label title = new Label("ESCAPE FROM THE LAB");
        title.getStyleClass().add("title");
        title.setTranslateX(100);
        title.setTranslateY(20);

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

        //signUpNameLabel
        Label signUpNameLabel = new Label("username:");
        signUpNameLabel.getStyleClass().add("label");
        GridPane.setConstraints(signUpNameLabel, 0, 1);

        //signUpPasswordLabel
        Label signUpPasswordLabel = new Label("password:");
        signUpPasswordLabel.getStyleClass().add("label");
        GridPane.setConstraints(signUpPasswordLabel, 0, 2);

        //signUpBox username
        TextField signUpUsername = new TextField();
        signUpUsername.setPrefSize(140, 50);
        signUpUsername.setPromptText("username");
        GridPane.setConstraints(signUpUsername, 1, 1);

        //signUpBox password
        TextField signUpPassword = new TextField();
        signUpPassword.setPrefSize(140, 50);
        signUpPassword.setPromptText("password");
        GridPane.setConstraints(signUpPassword, 1, 2);

        //user information
        Label gender = new Label("gender: ");
        gender.getStyleClass().add("label");
        GridPane.setConstraints(gender, 0, 3);
        ComboBox genderBox = new ComboBox();
        genderBox.getItems().addAll(
                "male",
                "female"
        );
        genderBox.setEditable(true);
        genderBox.setPromptText("gender");
        GridPane.setConstraints(genderBox, 1, 3);
        genderChose = new String();
        genderBox.valueProperty().addListener((ob, ov, nv)->{
            genderChose = nv.toString();
        });

        //wrong notice when log in
        Label loginBoxLabel = new Label();
        loginBoxLabel.setVisible(false);
        loginBoxLabel.getStyleClass().add("long-label");
        GridPane.setConstraints(loginBoxLabel, 0, 5);

        //wrong notice when sign up
        Label signUpBoxLabel = new Label();
        signUpBoxLabel.setVisible(false);
        signUpBoxLabel.getStyleClass().add("long-label");
        GridPane.setConstraints(signUpBoxLabel, 0, 5);

        Button signUpBoxBtn = new Button("Sign up");
        signUpBoxBtn.getStyleClass().add("menu-button");
        GridPane.setConstraints(signUpBoxBtn, 0, 4);
        signUpBoxBtn.setOnAction(e -> {
            try {
                if (exists(signUpUsername.getText())) {
                    signUpBoxLabel.setText("Username already exists.");
                    signUpBoxLabel.setVisible(true);
                } else {
                    if (signUp(signUpUsername.getText(), signUpPassword.getText(), genderChose)) {
                        signUpBox.setVisible(true);
                        FXGL.getGameController().startNewGame();
                    }

                };

            } catch(SQLException ex){
            throw new RuntimeException(ex);
        }
        //FXGL.getGameController().startNewGame();
    });

    Button signUpBtn = new Button("Sign up");
        signUpBtn.getStyleClass().add("menu-button");
        signUpBtn.setOnAction(actionEvent ->
    {
        loginBox.setVisible(false);
        signUpBox.setVisible(true);
    });
    //GridPane.setConstraints(signUpBtn, 0, 4);
        signUpBtn.setTranslateX(205);
        signUpBtn.setTranslateY(275);

    Button loginButton = new Button();
        loginButton.getStyleClass().

    add("menu-button");
        loginButton.setText("Log in");
        GridPane.setConstraints(loginButton,0,3);
        loginButton.setOnAction(actionEvent ->

    {
        try {
            if (verify(nameInput.getText(), passInput.getText())) {
                loginBox.setVisible(true);
                FXGL.getGameController().startNewGame();
            } else {
                loginBoxLabel.setText("Incorrect username or password.");
                loginBoxLabel.setVisible(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    });


    Button exitButton = new Button("Exit");
        exitButton.setOnAction(e ->

    {
        FXGL.getGameController().exit();
    });
        exitButton.getStyleClass().

    add("menu-button");
        GridPane.setConstraints(exitButton,1,3);

    Button signUpBoxexitButton = new Button("Exit");
        signUpBoxexitButton.setOnAction(e ->

    {
        FXGL.getGameController().exit();
    });
        signUpBoxexitButton.getStyleClass().add("menu-button");
        GridPane.setConstraints(signUpBoxexitButton,1,4);

    Button signUpBoxLoginBtn = new Button("Back");
    signUpBoxLoginBtn.setOnAction(e ->{
        loginBox.setVisible(true);
        signUpBox.setVisible(false);
    });
    signUpBoxLoginBtn.getStyleClass().add("menu-button");
        signUpBoxLoginBtn.setTranslateX(205);
        signUpBoxLoginBtn.setTranslateY(360);

        loginBox.getChildren().addAll(
            title,
            nameLabel,
            nameInput,
            passLabel,
            passInput,
            signUpBtn,
            loginButton,
            exitButton,
            loginBoxLabel);
        signUpBox.getChildren().

    addAll(
            signUpNameLabel,
            signUpPasswordLabel,
            signUpUsername,
            signUpPassword,
            signUpBoxLabel,
            signUpBoxBtn,
            signUpBoxexitButton,
            genderBox,
            gender,
            signUpBoxLoginBtn);

    defaultPane =new Pane(loginBox, signUpBox);

    getContentRoot().getChildren().addAll(defaultPane);
}
}
