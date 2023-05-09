package com.example.javagame.GameDesign.Collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.javagame.GameDesign.Config;
import com.example.javagame.GameDesign.GameType;

import java.util.List;

public class BulletBrickCollision extends CollisionHandler {
    public BulletBrickCollision() {
        super(GameType.BULLET, GameType.BRICK);
    }

    @Override
    protected void onCollision (Entity bullet, Entity brick) {
        List<Entity> entityList = FXGL.getGameWorld().getEntitiesFiltered(entity ->
                bullet.isColliding(entity)
                        && (entity.isType(GameType.STONE) || entity.isType(GameType.BRICK) || entity.isType(GameType.GREENS))
        );
        for (int i = 0; i < entityList.size(); i++) {
            Entity entity = entityList.get(i);
            GameType type = (GameType) entity.getType();
            switch (type) {
                case BRICK -> {
                    entity.removeFromWorld();
                    FXGL.spawn("explode", bullet.getPosition());
                    bullet.removeFromWorld();
                }
                case STONE -> {
                    int value = bullet.getInt("level");
                    if (value == Config.Weapon_MaxLevel) {
                        entity.removeFromWorld();
                        FXGL.spawn("explode", bullet.getPosition());
                    } bullet.removeFromWorld();

                }
                case GREENS -> {
                    int value = bullet.getInt("level");
                    if (value == Config.Weapon_MaxLevel) {
                        entity.removeFromWorld();
                    }
                }
            }
        }
    }
}