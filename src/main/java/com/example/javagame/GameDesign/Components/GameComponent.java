package com.example.javagame.GameDesign.Components;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityGroup;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import com.example.javagame.GameDesign.Config;
import com.example.javagame.GameDesign.Direction;
import com.example.javagame.GameDesign.GameType;

import java.util.List;

public class GameComponent extends Component {
    private boolean moveFirst = false;
    private double distance;
    private Direction moveDirection = Direction.UP;
    //adjust the time interval
    private LocalTimer shootTimer;
    Direction getMoveDirection(){
        return moveDirection;
    }

    public void setMoveDirection(Direction moveDirection) {
        this.moveDirection = moveDirection;
    }

    @Override
    public void onAdded() {
        //initialize local timer when the character is added to the game
        shootTimer = FXGL.newLocalTimer();
    }

    private LazyValue<EntityGroup> barrierLazyValue
            = new LazyValue<>(() -> FXGL.getGameWorld().getGroup(
            GameType.BRICK, GameType.BORDER, GameType.STONE, GameType.PLAYER, GameType.ENEMY));

    @Override
    public void onUpdate(double tpf) {
        moveFirst = false;
        distance = tpf * Config.Character_Speed;
    }

    public void moveUp() {
        if (moveFirst) {
            return;
        }
        moveFirst = true;
        entity.setRotation(0);
        moveDirection = Direction.UP;
        move();
    }

    public void moveDown() {
        if (moveFirst) {
            return;
        }
        moveFirst = true;
        entity.setRotation(0);
        moveDirection = Direction.DOWN;
        move();
    }

    public void moveLeft() {
        if (moveFirst) {
            return;
        }
        moveFirst = true;
        entity.setRotation(0);
        moveDirection = Direction.LEFT;
        move();
        //turn around when turning left
        entity.setScaleX(-1);
    }

    public void moveRight() {
        if (moveFirst) {
            return;
        }
        moveFirst = true;
        entity.setRotation(0);
        moveDirection = Direction.RIGHT;
        move();
        entity.setScaleX(1);
    }

    //make entities stop when approaching barrier
    public void move() {
        int len = (int) distance;
        //define which is barrier
        List<Entity> barrierList = barrierLazyValue.get().getEntitiesCopy();
        //remove the moving entity themselves from the group
        barrierList.remove(entity);
        int size = barrierList.size();
        boolean isCollision = false;
        for (int i = 0; i < len; i++) {
            //detect the characters' moving one pixel by one pixel
            entity.translate(
                    moveDirection.getVector().getX(),
                    moveDirection.getVector().getY());
            //adjust whether player collide the barrier
            //if so, stop moving
            for(int j = 0; j< size; j++){
                if (entity.isColliding(barrierList.get(j))){
                    isCollision = true;
                    break;
                }
            }
            if (isCollision){
                entity.translate(
                        -moveDirection.getVector().getX(),
                        -moveDirection.getVector().getY()
                );
                break;
            }
        }
    }
        public void Charactershoot() {
        //adjust whether it is time to shoot
           if (shootTimer.elapsed(Config.shoot_Delay)){
               FXGL.spawn("shockwave", new SpawnData(
                       //set the location of where the shockwave comes out
                       entity.getCenter().subtract(29/2.0, 32/2.0)
               )
                       .put("dir", moveDirection.getVector())
                       .put("level", entity.getComponent(LevelComponent.class).getValue()));
               //after shooting, make the localtimer return to zero
               shootTimer.capture();
           }
        }

    public void Enemyshoot() {
        //adjust whether it is time to shoot
        if (shootTimer.elapsed(Config.shoot_Delay)){
            FXGL.spawn("bullet", new SpawnData(
                    //set the location of where the shockwave comes out
                    entity.getCenter().subtract(8/2.0, 10/2.0)
            )
                    .put("dir", moveDirection.getVector())
                    .put("ownerType", entity.getType())
                    .put("level", entity.getComponent(LevelComponent.class).getValue())
            );
            //after shooting, make the localtimer return to zero
            shootTimer.capture();
        }
    }

}

