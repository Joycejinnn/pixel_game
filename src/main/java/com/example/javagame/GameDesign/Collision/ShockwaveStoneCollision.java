package com.example.javagame.GameDesign.Collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.javagame.GameDesign.Config;
import com.example.javagame.GameDesign.GameType;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class ShockwaveStoneCollision extends CollisionHandler {
    public ShockwaveStoneCollision() {
        super(GameType.SHOCKWAVE, GameType.STONE);
    }

    @Override
    protected void onCollisionBegin(Entity shockwave, Entity stone) {
        int value = shockwave.getInt("level");
        if (value == Config.Weapon_MaxLevel){
            spawn("explode", shockwave.getCenter().subtract(12/2.0, 12/2.0));
            stone.removeFromWorld();
        }
        shockwave.removeFromWorld();
    }
}
