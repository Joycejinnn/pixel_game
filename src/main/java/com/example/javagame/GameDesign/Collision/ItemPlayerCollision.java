package com.example.javagame.GameDesign.Collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.javagame.GameDesign.App;
import com.example.javagame.GameDesign.Components.EnemyComponent;
import com.example.javagame.GameDesign.Components.LevelComponent;
import com.example.javagame.GameDesign.Config;
import com.example.javagame.GameDesign.Effects.HelmetEffect;
import com.example.javagame.GameDesign.GameType;
import com.example.javagame.GameDesign.ItemType;
import kotlin.SinceKotlin;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getip;
import static com.almasb.fxgl.dsl.FXGLForKtKt.inc;

public class ItemPlayerCollision extends CollisionHandler {
    public ItemPlayerCollision(){
        super(GameType.ITEM, GameType.PLAYER);
    }

    @Override
    protected void onCollisionBegin(Entity item, Entity player) {
        ItemType itemType = item.getObject("itemType");
        switch (itemType){
            //different items corresponding to different effects
            case STAR -> {
                player.getComponent(LevelComponent.class).upgrade();
                inc("weaponLevel", 1);
            }
            case GUN -> {
                player.getComponent(LevelComponent.class).restoreFully();
                getip("weaponLevel").addListener((ob, ov, nv) -> {
                    for (int i = ov.intValue(); i <= Config.Weapon_MaxLevel; i++) {
                        inc("weaponLevel", 1);
                    }
                });
            }
            case HEART -> player.getComponent(HealthDoubleComponent.class).restore(3);
            case BOMB -> {
                FXGL.getGameWorld().getEntitiesByType(GameType.ENEMY).forEach(entity -> {
                    FXGL.spawn("explode", entity.getCenter().subtract(50/2.0, 50/2.0));
                    entity.removeFromWorld();
                    FXGL.inc("destroyedEnemyAmount", 1);
                });
            }
            case TIME -> {
                App app = (App) FXGL.getApp();
                app.freezeEnemy();
            }
            case HELMET -> {
                player.getComponent(EffectComponent.class).startEffect(new HelmetEffect());
            }
        }
        item.removeFromWorld();

    }
}
