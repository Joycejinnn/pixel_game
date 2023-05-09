package com.example.javagame.GameDesign.Collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.javagame.GameDesign.Components.LevelComponent;
import com.example.javagame.GameDesign.Config;
import com.example.javagame.GameDesign.Effects.HelmetEffect;
import com.example.javagame.GameDesign.GameType;
import com.example.javagame.GameDesign.ItemType;
import com.example.javagame.GameDesign.UI.FailedScene;

import java.util.concurrent.CopyOnWriteArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class ItemEnemyCollision extends CollisionHandler {
    public ItemEnemyCollision() {
        super(GameType.ITEM, GameType.ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity item, Entity enemy) {
        ItemType itemType = item.getObject("itemType");
        switch (itemType) {
            //different items corresponding to different effects
            case STAR -> {
                enemy.getComponent(LevelComponent.class).upgrade();
                inc("weaponLevel", 1);
            }
            case GUN -> {
                enemy.getComponent(LevelComponent.class).restoreFully();
                getip("weaponLevel").addListener((ob, ov, nv) -> {
                    for (int i = ov.intValue(); i <= Config.Weapon_MaxLevel; i++) {
                        inc("weaponLevel", 1);
                    }
                });
            }
            case HEART -> {
                FXGL.getGameWorld().getEntitiesByType(GameType.PLAYER).forEach(player -> {
                    if (player.isActive()) {
                        HealthDoubleComponent hp = player.getComponent(HealthDoubleComponent.class);
                        hp.damage(hp.getValue() / 2);
                        if (hp.isZero()) {
                            player.removeFromWorld();
                            spawn("explode", player.getCenter().subtract(48 / 2.0, 48 / 2.0));
                            FXGL.getSceneService().pushSubScene(new FailedScene());
                        }
                    }
                });
            }
            case BOMB -> {
                FXGL.getGameWorld().getEntitiesByType(GameType.PLAYER).forEach(player -> {
                    if (player.isActive()) {
                        HealthDoubleComponent hp = player.getComponent(HealthDoubleComponent.class);
                        hp.setValue(1);
                    }
                });
            }

            case HELMET -> {
                enemy.getComponent(EffectComponent.class).startEffect(new HelmetEffect());
            }
        }
        item.removeFromWorld();
    }
}
