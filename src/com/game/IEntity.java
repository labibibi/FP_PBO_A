package com.game;

import java.awt.Image;

public interface IEntity {

    void moveRight();
    void moveLeft();

    Image getImage();

    int getX();
    int getY();

    boolean isAlive();
}
