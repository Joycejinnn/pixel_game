package com.example.javagame.GameDesign.Collision;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.javagame.GameDesign.GameType;

public class ShockwaveBrickCollision extends CollisionHandler {
    public ShockwaveBrickCollision() {
        super(GameType.SHOCKWAVE, GameType.BRICK);
    }

    @Override
    protected void onCollisionBegin(Entity shockwave, Entity brick) {
        brick.removeFromWorld();
        shockwave.removeFromWorld();
    }
}
