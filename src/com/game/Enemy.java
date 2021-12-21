package com.game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Enemy implements IEntity {

    private Image EnemyImg;
    private int x_pos;
    private int y_pos;
    private int healthPoints;
    private int damagePoints;
  
	private boolean alive;

    private int currentFireDelay = 0;

    public ArrayList<EnemyAttack> enemyShots = new ArrayList<EnemyAttack>();

    public Enemy(int x, int y){
        this.x_pos = x;
        this.y_pos = y;
        this.alive = true;
    }

    public void moveRight(){
        x_pos += 1;
    }

    public void moveLeft(){
        x_pos -= 1;
    }

    public void moveForward(int moveSpeed){
        y_pos += moveSpeed;
    }
    
    public int getHealthPoints() {
  		return healthPoints;
  	}

  	public void setHealthPoints(int healthPoints) {
  		this.healthPoints = healthPoints;
  	}
  	
  	public void damaged(int damagePoints) {
  		setHealthPoints(this.healthPoints -= damagePoints);
  		if(this.healthPoints <= 0)
  		{
  			this.setAlive(false);
  		}
  	}
  	
  	public boolean isAlive()
  	{ 
  		return this.alive;
  	}
  	
    public void setAlive(boolean is){
        this.alive = is;
    }
    
    public int getDamagePoints() {
		return damagePoints;
	}

	public void setDamagePoints(int damagePoints) {
		this.damagePoints = damagePoints;
	}

    public Image getImage(){
        return EnemyImg;
    }

    public void setImage(String name){
        ImageIcon img = new ImageIcon(name);
        EnemyImg      = img.getImage();
    }

    public int getX(){
        return this.x_pos;
    }

    public int getY(){
        return this.y_pos;
    }

    public void setX(int x){
        this.x_pos = x;
    }

    public void setY(int y){
        this.y_pos = y;
    }

    public EnemyAttack shoot(){
        EnemyAttack enemyShot = new EnemyAttack(x_pos, y_pos);
        enemyShots.add(enemyShot);

        return enemyShot;
    }

    public int getCurrentFireDelay() {
        return currentFireDelay;
    }

    public void setCurrentFireDelay(int delay) {
        currentFireDelay = delay;
    }
   

    // Method yang membuat bounds di sekitar enemy untuk mengecek collision 
    public Rectangle getBounds(){
    	// Ukuran sprite digunakan sebagai basis untuk ukuran bounds
        return new Rectangle(x_pos, y_pos, EnemyImg.getWidth(null), EnemyImg.getHeight(null));
    }
}
