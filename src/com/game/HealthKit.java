package com.game;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HealthKit extends PowerUp {
	private int healingPoints;

	public HealthKit(int x, int y) {
		super(x, y);
		this.healingPoints = 5;
		this.setImage("images/healthKit.png");
	}

	public int getHealingPoints() {
		return healingPoints;
	}

	public void setHealingPoints(int healingPoints) {
		this.healingPoints = healingPoints;
	}


}
