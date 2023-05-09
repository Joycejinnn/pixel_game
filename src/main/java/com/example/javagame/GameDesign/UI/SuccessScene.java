package com.example.javagame.GameDesign.UI;

import com.almasb.fxgl.app.scene.FXGLLoadingScene;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.FXGLScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.tiled.TextData;
import com.almasb.fxgl.scene.SubScene;
import com.example.javagame.GameDesign.App;
import com.example.javagame.GameDesign.Config;
import com.example.javagame.GameDesign.GameType;
import javafx.animation.PauseTransition;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;


public class SuccessScene extends SubScene {

    private final PauseTransition pauseTransition;


    public SuccessScene() {
        if (FXGL.geti("level") < Config.MAX_LEVEL_AMOUNT) {
            Text text = new Text("Score: " + 2000 * FXGL.geti("level") + "\nNext level->");
            text.setFill(Color.WHITE);
            text.setFont(Font.font(35));
            StackPane pane = new StackPane(text);
            pane.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
            //use css method to set background color
            pane.setStyle("-fx-background-color: black");

            getContentRoot().getChildren().add(pane);
            pauseTransition = new PauseTransition(Duration.seconds(2));

            pauseTransition.setOnFinished(actionEvent -> {
                //pop out of the scene and get to next level
                FXGL.getSceneService().popSubScene();
                FXGL.inc("level", 1);
                FXGL.<App>getAppCast().startLevel();
            });
        } else {
            Text text = new Text("Score: " + 2000 * FXGL.geti("level") + "\n   Victory!");
            text.setFill(Color.WHITE);
            text.setFont(Font.font(35));
            StackPane pane = new StackPane(text);
            pane.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
            //use css method to set background color
            pane.setStyle("-fx-background-color: black");

            getContentRoot().getChildren().add(pane);
            pauseTransition = new PauseTransition(Duration.seconds(2));
            pauseTransition.setOnFinished(actionEvent -> {
                //the users pass the game
                //inform and transfer them to the menu interface
                getDialogService().showConfirmationBox("WIN! Passed all levels. Continue?", result -> {
                    if (result) {
                        while(FXGL.getSceneService().getCurrentScene().isSubState()){
                            FXGL.getSceneService().popSubScene();
                        }
                        getGameController().gotoMainMenu();
                    } else {
                        getGameController().exit();
                    }
                });
            });
        }
        /*
            //jump to next level
            pauseTransition.setOnFinished(actionEvent -> {
                if (FXGL.geti("level") < Config.MAX_LEVEL_AMOUNT) {
                    //pop out of the scene and get to next level
                    FXGL.getSceneService().popSubScene();
                    FXGL.inc("level", 1);
                    FXGL.<App>getAppCast().startLevel();
                } else {
                    //the users pass the game
                    //inform and transfer them to the menu interface
                    FXGL.getNotificationService().pushNotification("Victory!");
                    FXGL.getGameController().gotoMainMenu();
                }
            });*/
        }

        @Override
        public void onCreate() {
            getGameWorld().getEntitiesByType(GameType.BULLET, GameType.ENEMY, GameType.SHOCKWAVE, GameType.PLAYER).forEach(Entity::removeFromWorld);
            pauseTransition.play();
        }
}
