package com.example.javagame.GameDesign.UI;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.example.javagame.GameDesign.Components.LevelComponent;
import com.example.javagame.GameDesign.Config;
import com.example.javagame.GameDesign.GameType;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class InfoPane extends VBox {

    private final Entity entity;
    private final HBox weaponBox;

    public InfoPane() {
        setSpacing(50);
        setPrefSize(6 * Config.CELL_SIZE, FXGL.getAppHeight());
        setTranslateX(28 * Config.CELL_SIZE);
        setAlignment(Pos.TOP_CENTER);

        //enemy information
        TilePane tilePane = new TilePane();
        tilePane.setStyle("-fx-border-color: red");
        tilePane.setVgap(6);
        tilePane.setHgap(10);
        for (int i = 0; i < Config.MAX_ENEMY_AMOUNT; i++) {
            tilePane.getChildren().add(FXGL.texture("EnemyLogo.png"));
        }

        getChildren().add(tilePane);

        //level information
        Texture texture = FXGL.texture("KEY1.png");
        Text text = getUIFactoryService().newText("", Color.WHITE, 26);
        text.textProperty().bind(getip("level").asString());
        HBox levelBox = new HBox(texture, text);
        levelBox.setMaxWidth(80);
        levelBox.setAlignment(Pos.BOTTOM_CENTER);
        getChildren().add(levelBox);

        //weapon information
        weaponBox = new HBox();
        weaponBox.setMaxWidth(32*3+5);
        for (int i = 0; i <= Config.Weapon_MaxLevel; i ++){
            Texture tt = FXGL.texture("attack/sword1.png");
            tt.setVisible(i == 0);
            weaponBox.getChildren().add(tt);
        }
        entity = FXGL.getGameWorld().getEntitiesByType(GameType.PLAYER).get(0);
        entity.getComponent(LevelComponent.class).valueProperty().addListener((ob, ov, nv)->{
            for (int i = 0; i <= nv.intValue();i++){
                weaponBox.getChildren().get(i).setVisible(true);
            }
        });
        getChildren().add(weaponBox);


        FXGL.getip("spawnedEnemyAmount").addListener((ob, ov, nv) -> {
            ObservableList<Node> nodes = tilePane.getChildren();
            for (int i = nodes.size() - 1; i >= Config.MAX_ENEMY_AMOUNT - nv.intValue(); i--) {
                nodes.get(i).setVisible(false);
            }
        });
    }
}
