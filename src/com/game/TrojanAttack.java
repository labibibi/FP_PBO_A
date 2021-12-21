package com.game;

import java.awt.Image;

public class TrojanAttack extends EnemyAttack {

	public TrojanAttack(int x, int y) {
		super(x, y);
		this.setFireDelay(500);
		this.setFireSpeed(3);
		this.setImage("images/trojanFire.png");
	}

	
}
