package com.example.javagame.GameDesign.Collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.javagame.GameDesign.GameType;

public class ShockwaveBorderCollision extends CollisionHandler {
    public ShockwaveBorderCollision() {
        super(GameType.SHOCKWAVE, GameType.BORDER);
    }

    @Override
    protected void onCollisionBegin(Entity shockwave, Entity border) {
        shockwave.removeFromWorld();
    }
}
