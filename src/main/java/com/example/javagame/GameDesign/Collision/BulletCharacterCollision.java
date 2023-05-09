package com.example.javagame.GameDesign.Collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.javagame.GameDesign.Effects.HelmetEffect;
import com.example.javagame.GameDesign.GameType;
import com.example.javagame.GameDesign.UI.FailedScene;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class BulletCharacterCollision extends CollisionHandler {
    public BulletCharacterCollision() {
        super(GameType.BULLET, GameType.PLAYER);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity player) {
        boolean a = player.getComponent(EffectComponent.class).hasEffect(HelmetEffect.class);
        if (a){
            bullet.removeFromWorld();
            return;
        }else {
            HealthDoubleComponent hp = player.getComponent(HealthDoubleComponent.class);
            hp.damage(1);
            if (hp.isZero()) {
                player.removeFromWorld();
                spawn("explode", player.getCenter().subtract(48 / 2.0, 48 / 2.0));
                //if the player has zero hp, then game over and turn back to the main manu
                FXGL.getSceneService().pushSubScene(new FailedScene());
            } else
                bullet.removeFromWorld();
        }
    }
}
