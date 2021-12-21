package com.game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player {

    public static final int MAX_HEALTH_POINTS = 100;
	private int x_pos;
    private int y_pos;
    private Image spaceShip;

    // Mengatur kecepatan bergerak
    private int moveSpeedX;
    private int moveSpeedY;
    private int moveYdead;
    
    // Mengatur delay tembakan blaster
    private int blasterDelay = 20;
    
    // Mengatur damage serangan
	private int blasterDamage;
    private int laserDamage;
    private int collisionDamage;
	private int shieldCollisionDamage;

    // Container untuk menampung object serangan-serangan
    static ArrayList blasterShots;
    static ArrayList<Laser> laserShots;

    private boolean keyLeft;
    private boolean keyRight;
    private boolean keyUp;
    private boolean keyDown;
    private boolean blaster;
    private boolean laser;
    private boolean isAlive;
	private int healthPoints;

    public Player(){

        ImageIcon img = new ImageIcon("images/spacecraft.png");
        spaceShip     = img.getImage();

        this.x_pos = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
        this.y_pos = Toolkit.getDefaultToolkit().getScreenSize().height - spaceShip.getHeight(null) - 30;
        this.moveSpeedX = 3;
        this.moveSpeedY = 3;
        this.moveYdead = 2;
        this.healthPoints = 100;
        limitHealthPoints();
        this.isAlive = true;
        blasterShots = new ArrayList();
        laserShots   = new ArrayList<Laser>();
        this.blasterDamage = 5;
        this.laserDamage = 10;
        this.collisionDamage = 10;
        this.shieldCollisionDamage = 15;
    }

    public void moveRight(){
        x_pos += moveSpeedX;
    }

    public void moveLeft(){
        x_pos -= moveSpeedX;
    }

    public void moveForward(){
        y_pos -= moveSpeedY;
    }

    public void moveBack(){
        y_pos += moveSpeedY;
    }

    public Image getImage(){
        return spaceShip;
    }

    public void setImage(String name){
        ImageIcon img = new ImageIcon(name);
        spaceShip     = img.getImage();
    }
    public int getX(){
        return x_pos;
    }

    public int getY(){
        return y_pos;
    }

    public void setX(int x){
        this.x_pos = x;
    }

    public void setY(int y){
        this.y_pos = y;
    }
    
    public int getBlasterDamage() {
  		return blasterDamage;
  	}

  	public void setBlasterDamage(int blasterDamage) {
  		this.blasterDamage = blasterDamage;
  	}

  	public int getLaserDamage() {
  		return laserDamage;
  	}

  	public void setLaserDamage(int laserDamage) {
  		this.laserDamage = laserDamage;
  	}

  	public int getCollisionDamage() {
  		return collisionDamage;
  	}

  	public void setCollisionDamage(int collisionDamage) {
  		this.collisionDamage = collisionDamage;
  	}
  	
  	 public int getShieldCollisionDamage() {
 		return shieldCollisionDamage;
 	}

 	public void setShieldCollisionDamage(int shieldCollisionDamage) {
 		this.shieldCollisionDamage = shieldCollisionDamage;
 	}

    public void generateBlaster(){
          blasterShots.add(new Blaster(x_pos, y_pos));
          blasterDelay = 20;
    }

    public static ArrayList getBlasterShots(){
        return blasterShots;
    }

    public void generateLaser(){
        Laser laser = new Laser(x_pos, y_pos);

        while (laserShots.size() < 2){
            laserShots.add(laser);
        }
    }

    public void setLaserState(boolean laser)
    {
    	this.laser = laser;
    }
    
    public void setBlasterState(boolean blaster)
    {
    	this.blaster = blaster;
    }
    
    public boolean getLaserState()
    {
    	return this.laser;
    }
    
    public boolean getBlasterState()
    {
    	return this.blaster;
    }
    
    
    	
    public ArrayList<Laser> getLaserShots(){
        return laserShots;
    }

    // Method untuk menggambar bounds di sekitar player untuk deteksi collision
    public Rectangle getBounds(){
        return new Rectangle(x_pos, y_pos, spaceShip.getWidth(null), spaceShip.getHeight(null));
    }

    public int getBlasterDelay(){
        return this.blasterDelay;
    }

    public void setBlasterDelay(){
        --this.blasterDelay;
    }

    public boolean isKeyLeft(){
        return keyLeft;
    }

    public boolean isKeyRight(){
        return keyRight;
    }

    public boolean isKeyUp(){
        return keyUp;
    }

    public boolean isKeyDown(){
        return keyDown;
    }

    public boolean isBlaster(){
        return blaster;
    }

    public boolean isLaser(){
        return laser;
    }

    public boolean isAlive(){
        return isAlive;
    }

    public void setAlive(boolean is){
        this.isAlive = is;
    }

    public void moveDeadPlayer(){
        this.y_pos += moveYdead;
    }
    
    // Method untuk mendeteksi input keyboard untuk menggerakan player
    public void keyPressed(KeyEvent e){

        if (e.getKeyCode() == KeyEvent.VK_A){
            keyLeft = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D){
            keyRight = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_W){
            keyUp = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S){
            keyDown = true;
        }
    }

    public void keyReleased(KeyEvent e){

        if (e.getKeyCode() == KeyEvent.VK_A){
            keyLeft = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D){
            keyRight = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W){
            keyUp = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S){
            keyDown = false;
        }
    }

	public int getHealthPoints() {
		return healthPoints;
	}
	
	public void limitHealthPoints ()
	{
		if(healthPoints > MAX_HEALTH_POINTS)
		{
			this.healthPoints = MAX_HEALTH_POINTS;
		}
		
		if(healthPoints < 0)
		{
			this.healthPoints = 0;
		}
	}
	
	public int getMaxHealthPoints() {
		return MAX_HEALTH_POINTS;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}
	
	public void damaged(int damagePoints)
	{
		this.setHealthPoints(this.healthPoints - damagePoints);
		if(this.healthPoints <= 0)
			this.setAlive(false);
	}

}
