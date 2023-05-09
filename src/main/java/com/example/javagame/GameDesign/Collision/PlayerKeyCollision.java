package com.example.javagame.GameDesign.Collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.javagame.GameDesign.GameType;

public class PlayerKeyCollision extends CollisionHandler {
    public PlayerKeyCollision(){
        super(GameType.PLAYER, GameType.KEY);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity key) {
        key.removeFromWorld();
        FXGL.inc("getKey", 1);
    }
}
