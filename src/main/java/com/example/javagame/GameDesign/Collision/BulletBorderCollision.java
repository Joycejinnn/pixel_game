package com.example.javagame.GameDesign.Collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.javagame.GameDesign.GameType;

public class BulletBorderCollision extends CollisionHandler {
    public BulletBorderCollision() {
        super(GameType.BULLET, GameType.BORDER);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity border) {
        bullet.removeFromWorld();
    }
}
