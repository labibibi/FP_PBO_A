package com.game;

public class Trojan extends Enemy {
	
	private int collisionDamagePoints;

	public Trojan(int x, int y) {
		super(x, y);
		this.setHealthPoints(20);
		this.setDamagePoints(20);
		this.setCollisionDamagePoints(10);
		this.setImage("images/trojan.png");
	}
	
	@Override
	 public TrojanAttack shoot(){
	        TrojanAttack trojanShot = new TrojanAttack(this.getX(), this.getY());
	        enemyShots.add(trojanShot);

	        return trojanShot;
	    }
	
	public int getCollisionDamagePoints() {
		return collisionDamagePoints;
	}

	public void setCollisionDamagePoints(int collisionDamagePoints) {
		this.collisionDamagePoints = collisionDamagePoints;
	}


}
