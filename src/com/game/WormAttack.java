package com.game;

public class WormAttack extends EnemyAttack {

	public WormAttack(int x, int y) {
		super(x, y);
		this.setFireDelay(500);
		this.setFireSpeed(4);
		this.setImage("images/wormFire.png");
	}
	
}
