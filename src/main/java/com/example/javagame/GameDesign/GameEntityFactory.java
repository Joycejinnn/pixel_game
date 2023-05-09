package com.example.javagame.GameDesign;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.ui.ProgressBar;
import com.example.javagame.GameDesign.Components.EnemyComponent;
import com.example.javagame.GameDesign.Components.LevelComponent;
import com.example.javagame.GameDesign.Components.GameComponent;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Locale;


public class GameEntityFactory implements EntityFactory {
    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        //give the player a hp level
        HealthDoubleComponent hpComponent = new HealthDoubleComponent(10);
        hpComponent.setValue(10);
        //set a progress bar of hp level
        ProgressBar hpBar = new ProgressBar(false);
        hpBar.setLabelVisible(false);
        hpBar.setWidth(45);
        hpBar.setTranslateY(42);
        //ob: object being observed
        hpBar.currentValueProperty().addListener((ob, oldvalue, newvalue)->{
            if (newvalue.intValue()<4){
                hpBar.setFill(Color.RED);
            }else if(newvalue.intValue()<=7){
                hpBar.setFill(Color.YELLOW);
            }else{
                hpBar.setFill(Color.LIGHTGREEN);
            }
        });
        hpBar.maxValueProperty().bind(hpComponent.maxValueProperty());
        hpBar.currentValueProperty().bind(hpComponent.valueProperty());

        return FXGL.entityBuilder(data)
                .type(GameType.PLAYER)
                .bbox(BoundingShape.box(40, 32))
                .viewWithBBox("people/main3.png")
                .view(hpBar)
                .with(new GameComponent())
                .with(new LevelComponent())
                .with(new CollidableComponent(true))
                .with(new ViewComponent())
                .with(hpComponent)
                .with(new EffectComponent())
                .collidable()
                .build();
    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(GameType.ENEMY)
                .bbox(BoundingShape.box(50, 50))
                .viewWithBBox("people/Enemy.png")
                .with(new GameComponent())
                .with(new LevelComponent())
                .with(new CollidableComponent(true))
                .with(new EnemyComponent())
                .with(new EffectComponent())
                .with(new ViewComponent())
                .collidable()
                .build();
    }

    @Spawns("brick")
    public Entity newBrick(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(GameType.BRICK)
                .bbox(BoundingShape.box(24, 24))
                .collidable()
                .neverUpdated()
                .zIndex(500)
                .build();
    }

    @Spawns("stone")
    public Entity newStone(SpawnData data){

        return FXGL.entityBuilder(data)
                .type(GameType.STONE)
                .bbox(BoundingShape.box(24, 24))
                .collidable()
                .build();
    }

    @Spawns("greens")
    public  Entity newGreens(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(GameType.GREENS)
                .bbox(BoundingShape.box(24, 24))
                .collidable()
                .zIndex(1000)
                .build();
    }

    @Spawns("border")
    public Entity newBorder(SpawnData data){
        double height = data.<Integer>get("height");
        double width = data.<Integer>get("width");
        return FXGL.entityBuilder(data)
                .type(GameType.BORDER)
                .viewWithBBox(new Rectangle(width, height, Color.LIGHTGREY))
                .collidable()
                .neverUpdated()
                .build();
    }
    @Spawns("shockwave")
    public Entity newShockWave(SpawnData data){
        //the direction of the shock wave (the same as the character)
        Point2D dir = data.get("dir");
        return FXGL.entityBuilder(data)
                .type(GameType.SHOCKWAVE)
                .bbox(BoundingShape.box(29, 32))
                .viewWithBBox("attack/shockWave1.png")
                .with(new ProjectileComponent(dir,  Config.ShockWave_Speed))
                .collidable()
                .build();
    }

    @Spawns("bullet")
    public Entity newBullet(SpawnData data){
        //the direction of the shock wave (the same as the character)
        Point2D dir = data.get("dir");
        //recognize their partners
        //stop destroying themselves
        CollidableComponent CollidableComponent = new CollidableComponent(true);
        CollidableComponent.addIgnoredType(data.<GameType>get("ownerType"));

        return FXGL.entityBuilder(data)
                .type(GameType.BULLET)
                .bbox(BoundingShape.box(8, 10))
                .viewWithBBox("attack/bullet.png")
                .with(new ProjectileComponent(dir,  Config.ShockWave_Speed))
                .with(CollidableComponent)
                .build();
    }

    @Spawns("explode")
    public Entity newExplode(SpawnData data){
        //add a soundtrack of exploding
        //FXGL.play("normalBomb.wav");
        //animation of explodes
        AnimationChannel ac = new AnimationChannel(FXGL.image("explode.png"),
                Duration.seconds(0.35), 9);
        AnimatedTexture at =new AnimatedTexture(ac);
        return FXGL.entityBuilder(data)
                .view(at.play())
                //make this texture disappear after exploding
                .with(new ExpireCleanComponent(Duration.seconds(0.35)))
                .build();
    }

    @Spawns("item")
    public Entity newItem(SpawnData data){
        ItemType itemType = FXGLMath.random(ItemType.values()).get();
        data.put("itemType", itemType);
        return FXGL.entityBuilder(data)
                .type(GameType.ITEM)
                //make the entity name in the itemtype lower case to corresponding to its picture name
                .viewWithBBox("item/"+itemType.toString().toLowerCase(Locale.ROOT)+".png")
                .collidable()
                .build();
    }

    @Spawns("key")
    public Entity newKey(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(GameType.KEY)
                .collidable()
                .viewWithBBox("KEY1.png")
                .bbox(BoundingShape.box(30, 30))
                .build();
    }

}
