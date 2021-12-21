package com.game;

/* Interface untuk serangan-serangan dalam game
 * Meliputi
 * - Blaster
 * - Laser
 * - Serangan-serangan musuh
 */
public interface IAttack {

    int getYPos();
    int getXPos();
    void moveShot();
}
