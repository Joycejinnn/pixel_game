package com.example.javagame.GameDesign;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public interface Config {
    int CELL_SIZE = 24;
    int Character_Speed = 180;
    int ShockWave_Speed = 400;
    //shooting time interval
    Duration shoot_Delay = Duration.seconds(0.35);
    Duration explode_Time = Duration.seconds(0.35);

    int Weapon_MaxLevel = 2;

    Point2D[] spawnEnemyPosition = new Point2D[]{
            new Point2D(30, 30),
            new Point2D(330, 30),
            new Point2D(580, 200)
    };

    int MAX_ENEMY_AMOUNT = 1;

    int MAX_LEVEL_AMOUNT = 2;

}
