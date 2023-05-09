package com.example.javagame.GameDesign.UI;

import com.almasb.fxgl.app.scene.StartupScene;
import javafx.scene.shape.Rectangle;

public class GameStartScene extends StartupScene {
    public GameStartScene(int appWidth, int appHeight) {
        super(appWidth, appHeight);
        Rectangle rectangle = new Rectangle(appWidth, appHeight);
        getContentRoot().getChildren().add(rectangle);
    }
}
