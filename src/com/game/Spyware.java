package com.game;

public class Spyware extends Enemy {

	public Spyware(int x, int y) {
		super(x, y);
		this.setHealthPoints(30);
		this.setDamagePoints(10);
		this.setImage("images/spyware.png");
	}
	
	@Override
	 public SpywareAttack shoot(){
	        SpywareAttack spywareShot = new SpywareAttack(this.getX(), this.getY());
	        enemyShots.add(spywareShot);

	        return spywareShot;
	    }

}
