package com.example.javagame.GameDesign;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;
import com.example.javagame.GameDesign.Collision.*;
import com.example.javagame.GameDesign.Components.GameComponent;
import com.example.javagame.GameDesign.UI.InfoPane;
import com.example.javagame.GameDesign.UI.LoginScene;
import com.example.javagame.GameDesign.UI.SuccessScene;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;


import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;


public class App extends GameApplication {
    private Entity player;
    private TimerAction freezingTimerAction;
    private TimerAction spawnEnemyTimerAction;
    private LazyValue<SuccessScene> successSceneLazyValue = new LazyValue<>(SuccessScene::new);


    @Override
    protected void initSettings(GameSettings gameSettings) {

        gameSettings.setHeight(28 * Config.CELL_SIZE);
        gameSettings.setWidth(34 * Config.CELL_SIZE);
        gameSettings.setTitle("Escape From the Lab");
        gameSettings.setVersion("0.1");
        //set game icon
        gameSettings.setAppIcon("appicon.png");
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setGameMenuEnabled(true);
        gameSettings.getCSSList().add("game.css");
        gameSettings.setDefaultCursor(new CursorInfo("cursor.png", 0, 0));

        gameSettings.setSceneFactory(new SceneFactory(){
            //insert a main menu scene
            @Override
            public FXGLMenu newMainMenu() {
                return new LoginScene();
            }
        });


    }


    @Override
    //game variables
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("freezeEnemy", false);
        vars.put("spawnedEnemyAmount", 0);
        vars.put("destroyedEnemyAmount", 0);
        vars.put("getKey", 0);
        vars.put("level", 1);
        vars.put("weaponLevel", 0);
    }

    public void freezeEnemy() {
        //check whether there is already a freezing timer
        // if so, we force the original timer to expire and restart one
        expireTimerAction(freezingTimerAction);
        FXGL.set("freezeEnemy", true);
        freezingTimerAction = runOnce(() ->
        {
            set("freezeEnemy", false);
            return null;
        }, Duration.seconds(10));
    }


    //extract the method of expiring TimerAction
    private void expireTimerAction(TimerAction freezingTimerAction) {
        if (freezingTimerAction != null && !freezingTimerAction.isExpired()) {
            freezingTimerAction.expire();
        }
    }

    //the method that can lead player to the next level
    public void startLevel() {


        //reset TimerAction
        expireTimerAction(spawnEnemyTimerAction);
        expireTimerAction(freezingTimerAction);
        //reset variables when starting a new level
        set("freezeEnemy", false);
        set("spawnedEnemyAmount", 0);
        set("destroyedEnemyAmount", 0);
        set("getKey", 0);
        //set("weaponLevel", 0);

        //set map(player should be generated after map)
        FXGL.setLevelFromMap("level" + geti("level") + ".tmx");



        player = FXGL.spawn("player", 220, 600);

        GameView view = new GameView(new InfoPane(),Integer.MAX_VALUE);
        getGameScene().addGameView(view);


        //generate enemies several times with a certain amount
        spawnEnemyTimerAction = run(() -> {
            //stop generating enemies when the enemy amount reached the maximum
            if (geti("spawnedEnemyAmount") == Config.MAX_ENEMY_AMOUNT) {
                if (spawnEnemyTimerAction != null && spawnEnemyTimerAction.isExpired()){
                    spawnEnemyTimerAction.expire();
                }
                return null;
            }
            Point2D point2D = FXGLMath.random(Config.spawnEnemyPosition).get();
            //check whether the generating point is blank or not
            //prevent the collision between enemies
            List<Entity> es = getGameWorld().getEntitiesInRange(new Rectangle2D(point2D.getX(), point2D.getY(), 65, 65));
            //filter STONE, BRICK, ENEMY, PLAYER, BORDER
            List<Entity> entities = es.stream().filter(entity -> entity.isType(GameType.PLAYER)
                    || entity.isType(GameType.ENEMY)
                    || entity.isType(GameType.BRICK)
                    || entity.isType(GameType.BORDER)
                    || entity.isType(GameType.STONE)).toList();
            if (entities.isEmpty()) {
                spawn("enemy", point2D);
                inc("spawnedEnemyAmount", 1);
            }
            return null;
        }, Duration.seconds(1.5));


        //set the two passing conditions
        getip("destroyedEnemyAmount").addListener((ob, ov, nv) -> {
                    int amount = nv.intValue();
                    int key = geti("getKey");

                    if (amount == Config.MAX_ENEMY_AMOUNT && key == geti("level")) {
                        FXGL.runOnce(() -> {
                            FXGL.getSceneService().pushSubScene(new SuccessScene());
                        }, Duration.seconds(1.5));
                    }
                });

        getip("getKey").addListener((ob, ov, nv) -> {
            int key = nv.intValue();
            int amount = geti("destroyedEnemyAmount");

            if (amount == Config.MAX_ENEMY_AMOUNT && key == geti("level")) {
                FXGL.runOnce(() -> {
                    FXGL.getSceneService().pushSubScene(new SuccessScene());
                }, Duration.seconds(1.5));
            }
        });
    }


        @Override
        protected void initGame() {
            //set background color
            FXGL.getGameScene().setBackgroundColor(Color.BLACK);
            //set game entity features
            FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());
            //invoke the method from startLevel
            startLevel();
        }




    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new ShockwaveEnemyCollision());
        getPhysicsWorld().addCollisionHandler(new BulletCharacterCollision());
        //getPhysicsWorld().addCollisionHandler(new BulletBrickCollision());
        //getPhysicsWorld().addCollisionHandler(new ShockwaveBrickCollision());
        BulletBrickCollision bulletBrickCollision = new BulletBrickCollision();
        getPhysicsWorld().addCollisionHandler(bulletBrickCollision);
        getPhysicsWorld().addCollisionHandler(bulletBrickCollision.copyFor(GameType.SHOCKWAVE, GameType.BRICK));
        getPhysicsWorld().addCollisionHandler(bulletBrickCollision.copyFor(GameType.SHOCKWAVE, GameType.STONE));
        getPhysicsWorld().addCollisionHandler(bulletBrickCollision.copyFor(GameType.BULLET, GameType.STONE));
        getPhysicsWorld().addCollisionHandler(bulletBrickCollision.copyFor(GameType.BULLET, GameType.GREENS));
        getPhysicsWorld().addCollisionHandler(bulletBrickCollision.copyFor(GameType.SHOCKWAVE, GameType.GREENS));
        getPhysicsWorld().addCollisionHandler(new ItemPlayerCollision());
        getPhysicsWorld().addCollisionHandler(new PlayerKeyCollision());
        getPhysicsWorld().addCollisionHandler(new ItemEnemyCollision());
        getPhysicsWorld().addCollisionHandler(new BulletBorderCollision());
        getPhysicsWorld().addCollisionHandler(new ShockwaveBorderCollision());
    }

    @Override
    protected void initInput() {
        //set keyboard control
        FXGL.onKey(KeyCode.UP, () -> {
            GameComponent playerComponent = player.getComponent(GameComponent.class);
            playerComponent.moveUp();
        });
        FXGL.onKey(KeyCode.DOWN, () -> {
            GameComponent playerComponent = player.getComponent(GameComponent.class);
            playerComponent.moveDown();
        });
        FXGL.onKey(KeyCode.LEFT, () -> {
            GameComponent playerComponent = player.getComponent(GameComponent.class);
            playerComponent.moveLeft();
        });
        FXGL.onKey(KeyCode.RIGHT, () -> {
            GameComponent playerComponent = player.getComponent(GameComponent.class);
            playerComponent.moveRight();
        });
        FXGL.onKey(KeyCode.SPACE, () -> {
            GameComponent playerComponent = player.getComponent(GameComponent.class);
            playerComponent.Charactershoot();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
