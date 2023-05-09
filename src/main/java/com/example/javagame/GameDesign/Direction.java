package com.example.javagame.GameDesign;

import javafx.geometry.Point2D;

import java.util.Vector;

public enum Direction {
    UP(new Point2D(0, -1)),
    DOWN(new Point2D(0, 1)),
    LEFT(new Point2D(-1, 0)),
    RIGHT(new Point2D(1, 0));

    private Point2D vector;
    Direction(Point2D vector){
        this.vector = vector;
    }

    public Point2D getVector() {
        return vector;
    }
}
