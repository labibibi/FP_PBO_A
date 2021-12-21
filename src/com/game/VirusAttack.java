package com.game;

public class VirusAttack extends EnemyAttack {

	public VirusAttack(int x, int y) {
		super(x, y);
		this.setFireDelay(500);
		this.setFireSpeed(3);
		this.setImage("images/virusFire.png");
	}
	
}
