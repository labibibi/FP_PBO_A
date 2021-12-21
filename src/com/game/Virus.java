package com.game;

public class Virus extends Enemy {

	public Virus(int x, int y) {
		super(x, y);
		this.setHealthPoints(5);
		this.setDamagePoints(5);
		this.setImage("images/virus.png");
	}
	
	@Override
	 public VirusAttack shoot(){
	        VirusAttack virusShot = new VirusAttack(this.getX(), this.getY());
	        enemyShots.add(virusShot);
	        return virusShot;
	    }

}
