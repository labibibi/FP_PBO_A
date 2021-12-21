package com.game;
/* Blaster adalah tipe serangan dari pemain yang 
 * bisa ditembakkan dengan cepat secara beruntun
 * tapi damagenya rendah dan gerakannya lambat
 */
import javax.swing.*;
import java.awt.*;

public class Blaster extends PlayerAttack {

	public Blaster(int x, int y) {
		super(x, y);
		this.setImage("images/blaster.png");
		this.setShotSpeed(-2);
	}

   
}
