open module com.example.javagame {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.almasb.fxgl.all;
    requires java.sql;


    /*opens com.example.javagame;
    exports com.example.javagame;*/
    exports com.example.javagame.Login;
    //opens com.example.javagame.login;
    //opens com.example.javagame.GameDesign;
    exports com.example.javagame.GameDesign;
    exports com.example.javagame.GameDesign.Components;
    //opens com.example.javagame.GameDesign.components;
}