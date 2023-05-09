package com.example.javagame.GameDesign.Components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.example.javagame.GameDesign.Direction;


public class EnemyComponent extends Component {
    private GameComponent gameComponent;
    @Override
    public void onUpdate(double tpf) {
        //getb() method is used to gain boolean value
        if (FXGL.getb("freezeEnemy")){
            return;
        }
        //gain direction method from gameComponent
        Direction moveDirection = gameComponent.getMoveDirection();
        //every frame the enemies have chance to change direction
        //avoid them from keeping static
        if (FXGLMath.randomBoolean(0.05)){
            moveDirection = FXGLMath.random(Direction.values()).get();
        }

        switch (moveDirection){
            case UP -> gameComponent.moveUp();
            case DOWN -> gameComponent.moveDown();
            case LEFT -> gameComponent.moveLeft();
            case RIGHT -> gameComponent.moveRight();
            default -> {

            }
        }
        //randomly shoot
        if (FXGLMath.randomBoolean(0.03)){
            gameComponent.Enemyshoot();
        }
    }
}
