package com.example.javagame.GameDesign.UI;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import com.example.javagame.GameDesign.Config;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class FailedScene extends SubScene {
    private final TranslateTransition translateTransition;
    private final Texture texture;
    public FailedScene(){
        texture = FXGL.texture("GameOver.png");
        //set game over picture's location
        texture.setLayoutX(28* Config.CELL_SIZE /2.0-texture.getWidth()/2.0);
        texture.setLayoutY(FXGL.getAppHeight());
        //use animation to pop up the picture
        translateTransition = new TranslateTransition(Duration.seconds(2.6), texture);
        translateTransition.setInterpolator(Interpolators.ELASTIC.EASE_OUT());
        translateTransition.setFromY(0);
        translateTransition.setToY(-(FXGL.getAppHeight()-260));
        translateTransition.setOnFinished(actionEvent -> {
            //turn off the failed scene
            FXGL.getSceneService().popSubScene();
            texture.setTranslateY(0);
            FXGL.getGameController().gotoMainMenu();
        });
        getContentRoot().getChildren().add(texture);
    }

    @Override
    public void onCreate() {
        translateTransition.play();
    }
}
