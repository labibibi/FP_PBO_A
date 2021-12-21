package com.game;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PowerUp implements IEntity {

	private Image hkImg;
	private int x_pos;
	private int y_pos;
	private boolean alive;

	public PowerUp(int x, int y){
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

	public void setAlive(boolean is){
		this.alive = is;
	}

	public boolean isAlive() {
		return alive;
	}

	public Image getImage(){
		return hkImg;
	}

	public void setImage(String name){
		ImageIcon img = new ImageIcon(name);
		hkImg      = img.getImage();
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

	// Method untuk membuat bound di sekitar power up untuk mendeteksi collision
	public Rectangle getBounds(){
		// Ukuran sprite digunakan sebagai dasar untuk menentukan ukuran bound
		return new Rectangle(x_pos, y_pos, hkImg.getWidth(null), hkImg.getHeight(null));
	}
}
