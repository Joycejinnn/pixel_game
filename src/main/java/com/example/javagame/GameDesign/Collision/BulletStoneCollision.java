package com.example.javagame.GameDesign.Collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.javagame.GameDesign.Config;
import com.example.javagame.GameDesign.GameType;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class BulletStoneCollision extends CollisionHandler {
    public BulletStoneCollision() {
        super(GameType.BULLET, GameType.STONE);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity stone) {
        int value = bullet.getInt("level");
        if (value == Config.Weapon_MaxLevel){
            spawn("explode", bullet.getCenter().subtract(12/2.0, 12/2.0));
            stone.removeFromWorld();
        }
        bullet.removeFromWorld();
    }
}
