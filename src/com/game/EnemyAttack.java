package com.game;
import javax.swing.*;
import java.awt.*;

public class EnemyAttack implements IAttack {

    private int x_pos;
    private int y_pos;
    private boolean visible;
    private Image enemyAttackImg;

	private int fireDelay;
	private int fireSpeed;

	private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    public EnemyAttack(int x, int y){
        this.x_pos = x + 20;
        this.y_pos = y + 20;
        visible    = true;
    }

    public int getXPos(){
        return this.x_pos;
    }

    public int getYPos(){
        return this.y_pos;
    }

    public void moveShot(){
        y_pos += fireSpeed;
        if (y_pos > screenHeight){
            visible = false;
        }
    }
    	
    public int getFireSpeed() {
 		return fireSpeed;
 	}

 	public void setFireSpeed(int fireSpeed) {
 		this.fireSpeed = fireSpeed;
 	}
 	
 	 public void setFireDelay(int fireDelay) {
 		this.fireDelay = fireDelay;
 	}
 	
    public int getFireDelay(){
        return fireDelay;
    }

    public boolean isVisible(){
        return visible;
    }

    public void setVisible(boolean is){
        this.visible = is;
    }

    public Image getEnemyAttackImg(){
        return enemyAttackImg;
    }
    
    public void setImage(String name){
        ImageIcon img = new ImageIcon(name);
        enemyAttackImg     = img.getImage();
    }

    /* Method untuk membuat bounds di sekitar serangan musuh, untuk mendeteksi collision
     * Ukuran dari bounds didasarkan pada ukuran gambar yang digunakan 
     */
    public Rectangle getBounds(){
        return new Rectangle(x_pos, y_pos, enemyAttackImg.getWidth(null), enemyAttackImg.getHeight(null));
    }
}
