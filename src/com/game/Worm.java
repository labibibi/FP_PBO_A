package com.game;

public class Worm extends Enemy {

	public Worm(int x, int y) {
		super(x, y);
		this.setHealthPoints(10);
		this.setDamagePoints(10);
		this.setImage("images/worm.png");
	}
	
	@Override
	 public WormAttack shoot(){
	        WormAttack wormShot = new WormAttack(this.getX(), this.getY());
	        enemyShots.add(wormShot);

	        return wormShot;
	    }
}
