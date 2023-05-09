package com.example.javagame.GameDesign.Effects;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.List;

public class HelmetEffect extends Effect {
    private AnimatedTexture texture;
    public HelmetEffect(){
        super(Duration.seconds(12.0));
        texture = new AnimatedTexture(
                new AnimationChannel(FXGL.image("item/armed_helmet.png"), Duration.seconds(1.0), 4)
        );
    }

    @Override
    public void onStart(Entity entity) {
        texture.setTranslateX(entity.getWidth()/2.0-texture.getFitWidth()/2.0);
        texture.setTranslateY(entity.getHeight()/2.0-texture.getFitHeight()/2.0);
        texture.loop();
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onEnd(Entity entity) {
        texture.stop();
        entity.getViewComponent().removeChild(texture);
    }
}

