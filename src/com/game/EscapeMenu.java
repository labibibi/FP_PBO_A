package com.game;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

public class EscapeMenu {
	
	// Dapatkan ukuran screen
    private int screenWidth  = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    
    Font GravityBold8;
    
    private boolean isContinue = false;
    private boolean isBackButtonClicked = false;
    
    private int buttonWidth = 250;
    private int buttonHeight = 50;
    boolean isEscapeMenuOpen ;
    private int buttonX = (screenWidth/2)-100;
    GameMenu menu = new GameMenu();
    private int continueButtonY = screenHeight - (screenHeight - ((45 * screenHeight) / 100));
    private int backButtonY = continueButtonY + 70;
    
    private Rectangle continueButton = new Rectangle(buttonX, continueButtonY, buttonWidth, buttonHeight);
    private Rectangle backButton     = new Rectangle(buttonX, backButtonY, buttonWidth, buttonHeight);
    
    public Rectangle continueButton() {
    	return continueButton;
    }
    public Rectangle backButton() {
    	return backButton;
    }
    
    public void setContinueOpened(boolean is) {
    	isContinue = is;
    }
    public void setBackClicked(boolean is){
        isBackButtonClicked = is;
    }
    public boolean isEscapeMenuOpen(){
        return isEscapeMenuOpen;
    }
    public EscapeMenu() {
    	
    }
    public void drawEscapeMenu(Graphics g, Image background, int padding, boolean isContinueHovered, boolean isBackHovered) {
    	Graphics2D graphics2D = (Graphics2D)g;
    	if (!isBackButtonClicked){
    		
    		 try
         	{
         		GravityBold8 = Font.createFont(Font.TRUETYPE_FONT, new File("GravityBold8.ttf")).deriveFont(20f);
         		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         		ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("GravityBold8.ttf")));
         	} catch(IOException | FontFormatException E)
         	{
         		
         	}
    		 
    		isEscapeMenuOpen = true;
            g.drawImage(background, 0, 0, null);
        	g.setColor(new Color(1, 14, 22, 200));
        	int width = screenWidth;
        	int height = screenHeight;
        	g.fillRect(0, 250, width, height/2);
        	
 
            g.setColor(new Color(1, 178, 241));
            g.drawRect(continueButton.x - 2, continueButton.y - 1, continueButton.width + 2, continueButton.height + 2);
            g.drawRect(backButton.x - 2, backButton.y - 1, backButton.width + 2, backButton.height + 2);
            
            if (!isContinueHovered){
                g.setColor(new Color(1, 14, 22));
            } else {
                g.setColor(new Color(56, 14, 112, 223));
            }
            g.fillRect(continueButton.x, continueButton.y, continueButton.width, continueButton.height);
            if (!isBackHovered){
                g.setColor(new Color(1, 14, 22));
            } else {
                g.setColor(new Color(56, 14, 112, 223));
            }
            g.fillRect(backButton.x, backButton.y, backButton.width, backButton.height);

            // -- DRAW BUTTONS --
            g.setFont(GravityBold8);
            graphics2D.setColor(Color.WHITE);
            
            g.drawString("CONTINUE", buttonX + 58, continueButtonY + padding);
            g.drawString("BACK", buttonX + 85, backButtonY + padding);
    	}
    	else {
    		isEscapeMenuOpen = false;
    	}
	
    }


}
