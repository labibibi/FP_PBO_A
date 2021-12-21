package com.game;
import javax.swing.*;
import java.awt.*;

public class PlayerAttack implements IAttack {

	private int x_pos;
	private int y_pos;
	private boolean visible;
	private Image attackImg;
	private int shotSpeed;

	public PlayerAttack(int x, int y){
		this.x_pos = x + 25;
		this.y_pos = y + 20;
		visible    = true;
	}

	public int getShotSpeed() {
		return shotSpeed;
	}

	public void setShotSpeed(int shotSpeed) {
		this.shotSpeed = shotSpeed;
	}
	
	public Image getAttackImg(){
		return attackImg;
	}

	public void setImage(String name){
		ImageIcon img = new ImageIcon(name);
		attackImg    = img.getImage();
	}

	public boolean isVisible(){
		return visible;
	}

	public void setVisible(boolean is){
		this.visible = is;
	}

	public int getYPos(){
		return this.y_pos;
	}

	public int getXPos(){
		return this.x_pos;
	}

	public void moveShot(){
		y_pos += shotSpeed;
		if (y_pos < 0){
			visible = false;
		}
	}

	/* Method untuk membuat bounds di sekitar player attack, untuk mendeteksi collision
	 * Ukuran dari bounds didasarkan pada ukuran gambar yang digunakan 
	 */
	public Rectangle getBounds(){
		return new Rectangle(x_pos, y_pos, attackImg.getWidth(null), attackImg.getHeight(null));
	}
}
