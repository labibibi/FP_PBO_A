package com.game;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameMenu {

	// Dapatkan ukuran screen
	private int screenWidth  = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

	Font GravityBold8;


	private Image gameTitle;

	private int gameTitleX;
	private int gameTitleY;

	private boolean isSettingsOpened    = false;
	private boolean isCreditOpened    = false;
	private boolean isBackButtonClicked = false;
	private boolean isMainMenuActive;

	private int buttonWidth  = 250;
	private int buttonHeight = 50;

	private int buttonX = (screenWidth / 2) - 100;

	private int startButtonY    = screenHeight - (screenHeight - ((45 * screenHeight) / 100));
	private int settingsButtonY = startButtonY + 70;
	private int exitButtonY     = settingsButtonY + 70;
	private int backButtonY     = screenHeight - 100;

	private Rectangle startButton    = new Rectangle(buttonX, startButtonY, buttonWidth, buttonHeight);
	private Rectangle settingsButton = new Rectangle(buttonX, settingsButtonY, buttonWidth, buttonHeight);
	private Rectangle exitButton     = new Rectangle(buttonX, exitButtonY, buttonWidth, buttonHeight);
	private Rectangle backButton     = new Rectangle(buttonX, backButtonY, buttonWidth, buttonHeight);

	public Rectangle startButton(){
		return startButton;
	}

	public Rectangle settingsButton(){
		return settingsButton;
	}

	public Rectangle exitButton(){
		return exitButton;
	}

	public Rectangle backButton(){
		return backButton;
	}

	public void setSettingsOpened(boolean is){
		isSettingsOpened = is;
	}
	public void setBackClicked(boolean is){
		isBackButtonClicked = is;
	}

	public boolean isMainMenuActive(){
		return isMainMenuActive;
	}

	public GameMenu(){	
		ImageIcon gameImg = new ImageIcon("images/gameTitle.png");
		gameTitle = gameImg.getImage();

		gameTitleX = (screenWidth / 2) - (gameTitle.getWidth(null) / 2) + 25;
		gameTitleY = screenHeight - (screenHeight - ((10 * screenHeight) / 100));
	}

	/* -- DRAW GAME MENU -- 
	 * Method untuk menggambar game menu
	 */
	public void drawGameMenu(Graphics g, Image background, int padding,
			boolean isStartHovered, boolean isExitHovered,
			boolean isSettingsHovered, boolean isBackHovered){
		if (!isSettingsOpened){

			try
			{
				GravityBold8 = Font.createFont(Font.TRUETYPE_FONT, new File("GravityBold8.ttf")).deriveFont(20f);
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("GravityBold8.ttf")));
			} catch(IOException | FontFormatException E)
			{

			}

			isMainMenuActive = true;

			g.setColor(Color.BLACK);
			g.fillRect(0, 0, screenWidth, screenHeight);
			g.drawImage(background, 0, 0, null);

			g.drawImage(gameTitle, gameTitleX, gameTitleY,
					gameTitle.getWidth(null), gameTitle.getHeight(null), null);

			// Draw frame di sekitar button
			g.setColor(new Color(1, 178, 241));
			g.drawRect(startButton.x - 2, startButton.y - 1, startButton.width + 2, startButton.height + 2);
			g.drawRect(settingsButton.x - 2, settingsButton.y - 1, settingsButton.width + 2, settingsButton.height + 2);
			g.drawRect(exitButton.x - 2, exitButton.y - 1, exitButton.width + 2, exitButton.height + 2);

			// Draw button
			if (!isStartHovered){
				g.setColor(new Color(1, 14, 22));
			} else {
				g.setColor(new Color(56, 14, 112, 223));
			}
			g.fillRect(startButton.x, startButton.y, startButton.width, startButton.height);

			if (!isSettingsHovered){
				g.setColor(new Color(1, 14, 22));
			} else {
				g.setColor(new Color(56, 14, 112, 223));
			}
			g.fillRect(settingsButton.x, settingsButton.y, settingsButton.width, settingsButton.height);

			if (!isExitHovered){
				g.setColor(new Color(1, 14, 22));
			} else {
				g.setColor(new Color(56, 14, 112, 223));
			}
			g.fillRect(exitButton.x, exitButton.y, exitButton.width, exitButton.height);

			// Draw nama button
			g.setFont(GravityBold8);
			g.setColor(Color.WHITE);

			g.drawString("START", buttonX + 80, startButtonY + padding);
			g.drawString("HOW TO PLAY", buttonX + 25, settingsButtonY + padding);
			g.drawString("EXIT", buttonX + 95, exitButtonY + padding);

		} else
		{  // -- DRAW HOW TO PLAY MENU --
			if (!isBackButtonClicked){

				isMainMenuActive = false;

				g.drawImage(background, 0, 0, null);
				g.setColor(new Color(1, 14, 22));
				int width = screenWidth - 400;
				g.fillRect(200, 0, width, screenHeight);

				if (!isBackHovered){
					g.setColor(new Color(1, 14, 22));
				} else {
					g.setColor(new Color(56, 14, 112, 223));
				}
				g.fillRect(backButton.x, backButton.y, backButton.width, backButton.height);
				g.setFont(GravityBold8);
				g.setColor(Color.WHITE);
				g.drawString("PLAYER ONE", 680, 40);
				g.setFont(GravityBold8);
				g.setColor(Color.WHITE);
				g.drawString("Move Forward 	 W_KEY", 680-85, 80+40);
				g.drawString("Move Back	       S_KEY", 680-85, 110+40);
				g.drawString("Move Right      D_KEY", 680-85, 140+40);
				g.drawString("Move Left       A_KEY", 680-85, 170+40);
				g.drawString("Blaster          LEFT CLICK", 680-85, 200+40);
				g.drawString("Laser             RIGHT CLICK", 680-85, 230+40);


				g.drawString("Pause Game : ESC", 650, 500);
				g.drawString("Restart Game After Death : ENTER", 520, 530);

				// Tombol kembali ke main menu
				g.drawRect(backButton.x - 2, backButton.y - 1, backButton.width + 2, backButton.height + 2);
				g.setFont(GravityBold8);
				g.setColor(Color.WHITE);
				g.drawString("BACK", buttonX + 85, backButtonY + padding);

			} else 
			{

				isMainMenuActive = true;

				g.setColor(Color.BLACK);
				g.fillRect(0, 0, screenWidth, screenHeight);
				g.drawImage(background, 0, 0, null);

				g.drawImage(gameTitle, gameTitleX, gameTitleY,
						gameTitle.getWidth(null), gameTitle.getHeight(null), null);

				// Draw frame di sekitar button
				g.setColor(new Color(1, 178, 241));
				g.drawRect(startButton.x - 2, startButton.y - 1, startButton.width + 2, startButton.height + 2);
				g.drawRect(settingsButton.x - 2, settingsButton.y - 1, settingsButton.width + 2, settingsButton.height + 2);
				g.drawRect(exitButton.x - 2, exitButton.y - 1, exitButton.width + 2, exitButton.height + 2);

				// Draw buttons
				g.setColor(new Color(1, 14, 22));
				g.fillRect(startButton.x, startButton.y, startButton.width, startButton.height);
				g.fillRect(settingsButton.x, settingsButton.y, settingsButton.width, settingsButton.height);
				g.fillRect(exitButton.x, exitButton.y, exitButton.width, exitButton.height);

				// Draw nama button
				g.setFont(GravityBold8);
				g.setColor(Color.WHITE);
				g.drawString("START", buttonX + 80, startButtonY + padding);
				g.drawString("HOW TO PLAY", buttonX + 25, settingsButtonY + padding);
				g.drawString("EXIT", buttonX + 95, exitButtonY + padding);

				isBackButtonClicked = false;
				isSettingsOpened    = false;
			}
		}  

	}
}
