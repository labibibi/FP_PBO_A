package com.game;
import javax.swing.*;
import java.awt.*;

public class Laser extends PlayerAttack {

	public Laser(int x, int y) {
		super(x, y);
		this.setImage("images/laser.png");
		this.setShotSpeed(-10);
	}

   
}
