package com.game;

public class SpywareAttack extends EnemyAttack {

	public SpywareAttack(int x, int y) {
		super(x, y);
		this.setFireDelay(300);
		this.setFireSpeed(3);
		this.setImage("images/spywareFire.png");
	}
	
}
