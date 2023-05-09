package com.example.javagame.GameDesign.Collision;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.javagame.GameDesign.Config;
import com.example.javagame.GameDesign.Effects.HelmetEffect;
import com.example.javagame.GameDesign.GameType;
import com.example.javagame.GameDesign.UI.FailedScene;
import javafx.geometry.Rectangle2D;

import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.inc;

public class ShockwaveEnemyCollision extends CollisionHandler {
    public ShockwaveEnemyCollision() {
        super(GameType.SHOCKWAVE, GameType.ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity shockwave, Entity enemy) {
        //judge the hp condition of the character
        boolean a = enemy.getComponent(EffectComponent.class).hasEffect(HelmetEffect.class);
        if (a){
            shockwave.removeFromWorld();
            return;
        }else {
            spawn("explode", enemy.getCenter()
                    .subtract(50/2.0, 50/2.0));
            shockwave.removeFromWorld();
            enemy.removeFromWorld();
        }
        spawn("explode", enemy.getCenter()
                .subtract(50/2.0, 50/2.0));
        shockwave.removeFromWorld();
        enemy.removeFromWorld();
        //if we destroy one enemy, the vars add one
        inc("destroyedEnemyAmount", 1);
        if (FXGLMath.randomBoolean(0.25)){
            FXGL.spawn("item", FXGLMath.randomPoint(new Rectangle2D(
                    Config.CELL_SIZE, Config.CELL_SIZE,
                    27*Config.CELL_SIZE-30,
                    27*Config.CELL_SIZE-28
            )));
        }

    }


}
