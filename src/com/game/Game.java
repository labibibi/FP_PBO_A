package com.game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements ActionListener {

	private Player player;
	private Shield shield;
	private Image background;
	private Image backgroundMenu;
	private Timer time;
	JPanel healthBarPanel;
	private int backgroundY;
	private int bgMotion;
	private int bgMotionSec;
	private int playerScore;
	int healthKitCount=0;
	Font GravityBold8;

	// Variabel terkait High Score
	private int highScore;
	private Font scoreFont;
	private String highScore1 = "";

	private int escapeCounter = 0;
	private int keyTwoCounter = 0;
	private int keyStat = 0;
	
	// Variabel-variabel boolean
	private boolean isGameEnd;
	private boolean isGameStarted = false;
	private boolean isGamePaused;
	
	// Untuk mendapatkan kuran screen
	private int screenWidth  = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

	/* -- ARRAYLIST ENTITY -- 
	 * --- ENEMIES --- 
	 */
	public ArrayList<Virus> viruses = new ArrayList<Virus>();
	public ArrayList<VirusAttack> virusShots = new ArrayList<VirusAttack>();

	public ArrayList<Worm> worms = new ArrayList<Worm>();
	public ArrayList<WormAttack> wormShots = new ArrayList<WormAttack>();

	public ArrayList<Trojan> trojans = new ArrayList<Trojan>();
	public ArrayList<TrojanAttack> trojanShots = new ArrayList<TrojanAttack>();

	public ArrayList<Spyware> spywares = new ArrayList<Spyware>();
	public ArrayList<SpywareAttack> spywareShots = new ArrayList<SpywareAttack>();

	// -- POWER UPS ---
	public ArrayList<HealthKit> healthKits = new ArrayList<HealthKit>();
	private int hkSpawnTick = 0;

	public ArrayList<ShieldKit> shieldKits = new ArrayList<ShieldKit>();
	private int skSpawnTick = 0;

	Random randGen = new Random();

	/* GAME CONSTANTS */
	// Mengatur kecepatan gerak enemy dan power up
	private final int defaultSpeed        = 2;
	private final int virusSpeed        = 2;
	private final int wormSpeed        = 2;
	private final int trojanSpeed        = 2;
	private final int BUTTON_PADDING_TOP = 35;
	private final int ENTITY_SPAWN_Y    = 6000;

	/* MENU BUTTONS AND VALUES*/
	GameMenu menu = new GameMenu();
	EscapeMenu esMenu = new EscapeMenu();
	private boolean isStartButtonHovered;
	private boolean isExitButtonHovered;
	private boolean isSettingsButtonHovered;
	private boolean isBackButtonHovered;
	private boolean isBackButtonHovered1;
	private boolean isContinueButtonHovered;

	// Class untuk meregister keypress
	private class MyActionListener extends KeyAdapter implements ActionListener {

		@Override
		public void keyPressed(KeyEvent e){
			player.keyPressed(e);

			if (e.getKeyCode() == KeyEvent.VK_ESCAPE && escapeCounter < 1){
				isGameStarted = false;
				keyStat = 1;
			}

			else if (isGameEnd && e.getKeyCode() == KeyEvent.VK_ENTER){
				isGameStarted = true;
				gameReset();
				isGameStarted = true;
				gameReset();
				isGameStarted = true;
				keyStat = 1;
			}
		}

		public void gameReset(){
			if(playerScore > highScore) {
				highScore = playerScore;
			}
			player    = new Player();
			shield    = new Shield(player.getX(), player.getY());
			viruses.clear();
			virusShots.clear();

			worms.clear();
			wormShots.clear();

			trojans.clear();
			trojanShots.clear();

			spywares.clear();
			spywareShots.clear();

			healthKits.clear();
			hkSpawnTick = 0;

			shieldKits.clear();
			skSpawnTick = 0;

			// Spawn Enemies
			//Virus
			spawnViruses();
			// Worms
			spawnWorms();
			// Trojans
			spawnTrojans();
			// Spywares
			spawnSpywares();

			// Spawn Power Ups
			// Health Kits
			spawnHealthKits();
			// Shield Kits
			spawnShieldKits();

			addKeyListener(new MyActionListener());
			addMouseListener(new MouseEvents());
			addMouseMotionListener(new MouseEvents());

			ImageIcon img = new ImageIcon("images/background.jpg");
			background    = img.getImage();

			bgMotion    = background.getHeight(null);
			bgMotionSec = 0;
			backgroundY = 0;

			playerScore = 0;
			isGameEnd = false;

			setFocusable(true);
		}

		@Override
		public void keyReleased(KeyEvent e){
			player.keyReleased(e);
			if (player.isAlive()){
			}

			if (e.getKeyCode() == KeyEvent.VK_ESCAPE && escapeCounter > 0){
				isGameStarted = false;
				keyStat = 1;
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}

	// Class untuk register mouse event
	private class MouseEvents extends MouseAdapter {

		@Override
		public void mouseMoved(MouseEvent e){
			int mouseX = e.getX();
			int mouseY = e.getY();

			if (mouseX > menu.startButton().x && mouseX < menu.startButton().x + menu.startButton().width &&
					mouseY > menu.startButton().y && mouseY < menu.startButton().y + menu.startButton().height &&
					!isGameStarted && menu.isMainMenuActive()&&keyStat == 0){

				isStartButtonHovered = true;

			} else {
				isStartButtonHovered = false;
			}

			if (mouseX > menu.settingsButton().x && mouseX < menu.settingsButton().x + menu.settingsButton().width &&
					mouseY > menu.settingsButton().y && mouseY < menu.settingsButton().y + menu.settingsButton().height &&
					!isGameStarted && menu.isMainMenuActive()&&keyStat == 0){

				isSettingsButtonHovered = true;

			} else {
				isSettingsButtonHovered = false;
			}

			if (mouseX > menu.exitButton().x && mouseX < menu.exitButton().x + menu.exitButton().width &&
					mouseY > menu.exitButton().y && mouseY < menu.exitButton().y + menu.exitButton().height &&
					!isGameStarted && menu.isMainMenuActive()&&keyStat == 0){

				isExitButtonHovered = true;

			} else {
				isExitButtonHovered = false;
			}

			if (mouseX > menu.backButton().x && mouseX < menu.backButton().x + menu.backButton().width &&
					mouseY > menu.backButton().y && mouseY < menu.backButton().y + menu.backButton().height &&
					!isGameStarted && keyStat == 0){

				isBackButtonHovered = true;

			} else {
				isBackButtonHovered = false;
			}
			if (mouseX > esMenu.continueButton().x && mouseX < esMenu.continueButton().x + esMenu.continueButton().width &&
					mouseY > esMenu.continueButton().y && mouseY < esMenu.continueButton().y + esMenu.continueButton().height &&
					!isGameStarted&&esMenu.isEscapeMenuOpen()&&keyStat == 1){

				isContinueButtonHovered = true;

			} else {
				isContinueButtonHovered = false;
			}
			if (mouseX > esMenu.backButton().x && mouseX < esMenu.backButton().x + esMenu.backButton().width &&
					mouseY > esMenu.backButton().y && mouseY < esMenu.backButton().y + esMenu.backButton().height &&
					!isGameStarted&&esMenu.isEscapeMenuOpen()&&keyStat==1){

				isBackButtonHovered1 = true;

			} else {
				isBackButtonHovered1 = false;
			}
		}

		@Override
		public void mousePressed(MouseEvent e){
			int mouseX = e.getX();
			int mouseY = e.getY();

			if(isGameStarted)
			{
				if(e.getButton() == MouseEvent.BUTTON1)
				{
					if(player.getLaserState() == false)
					{
						player.setBlasterState(true);
					}
				}
				else if(e.getButton() == MouseEvent.BUTTON3)
				{
					if(player.getBlasterState() == false)
					{
						player.setLaserState(true);
					}
				}
			}

			if (mouseX > menu.startButton().x && mouseX <  menu.startButton().x +  menu.startButton().width &&
					mouseY >  menu.startButton().y && mouseY <  menu.startButton().y +  menu.startButton().height &&
					!isGameStarted && menu.isMainMenuActive()&&keyStat == 0){
				keyStat = 1;
				isGameStarted = true;
			}
			if (mouseX > menu.settingsButton().x && mouseX < menu.settingsButton().x + menu.settingsButton().width &&
					mouseY > menu.settingsButton().y && mouseY < menu.settingsButton().y + menu.settingsButton().height &&
					!isGameStarted && menu.isMainMenuActive()&&keyStat == 0){

				menu.setSettingsOpened(true);
			}

			if (mouseX > menu.backButton().x && mouseX < menu.backButton().x + menu.backButton().width &&
					mouseY > menu.backButton().y && mouseY < menu.backButton().y + menu.backButton().height &&
					!isGameStarted&&keyStat == 0){

				menu.setBackClicked(true);

			}
			if (mouseX > menu.exitButton().x && mouseX < menu.exitButton().x + menu.exitButton().width &&
					mouseY > menu.exitButton().y && mouseY < menu.exitButton().y + menu.exitButton().height &&
					!isGameStarted && menu.isMainMenuActive()&&keyStat == 0){

				System.out.println("Health Kit Count : " + healthKitCount);
				System.exit(0);
			}
			
			// -- ESCAPE MENU -- 
			if (mouseX > esMenu.continueButton().x && mouseX <  esMenu.continueButton().x +   esMenu.continueButton().width &&
					mouseY >  esMenu.continueButton().y && mouseY <  esMenu.continueButton().y +  esMenu.continueButton().height &&
					!isGameStarted &&keyStat == 1){

				isGameStarted = true;
				keyStat = 1;
			}
			if (mouseX > esMenu.backButton().x && mouseX < esMenu.backButton().x + esMenu.backButton().width &&
					mouseY > esMenu.backButton().y && mouseY < esMenu.backButton().y + esMenu.backButton().height &&
					!isGameStarted&&keyStat ==1){
				menu.setBackClicked(true);
				keyStat = 0;

			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(isGameStarted)
			{
				if(e.getButton() == MouseEvent.BUTTON1)
				{
					player.setBlasterState(false);
				}
				else if(e.getButton() == MouseEvent.BUTTON3)
				{
					player.setLaserState(false);
				}
			}
		}
	}

	public Game(){
		
		// -- GAME SCORE MANAGEMENT -- 
		if(playerScore > highScore) {
			this.highScore = this.playerScore;
		}

		player    = new Player();
		shield    = new Shield(player.getX(), player.getY());

		// -- SPAWN ENEMIES -- 
		// Virus
		spawnViruses();
		// Worms
		spawnWorms();
		// Trojans
		spawnTrojans();
		// Spywares
		spawnSpywares();

		// -- SPAWN POWER UPS -- 
		// Health Kits
		spawnHealthKits();
		// Shield Kits
		spawnShieldKits();

		// -- KEY DAN MOUSE LISTENER -- 
		addKeyListener(new MyActionListener());
		addMouseListener(new MouseEvents());
		addMouseMotionListener(new MouseEvents());

		ImageIcon img = new ImageIcon("images/background.jpg");
		background    = img.getImage();

		String menuBackground = "images/gameMenuBG.jpg";
		if (screenWidth == 1366 && screenHeight == 768){
			menuBackground = "images/gameMenu2.jpg";
		} else if (screenWidth == 1440 && screenHeight == 900){
			menuBackground = "images/gameMenu3.jpg";
		}
		ImageIcon bgImg = new ImageIcon(menuBackground);
		backgroundMenu  = bgImg.getImage();

		bgMotion    = background.getHeight(null);
		bgMotionSec = 0;
		backgroundY = 0;

		playerScore = 0;
		isGameEnd = false;
		setFocusable(true);

		time = new Timer(2, this);
		time.start();
	}

	// -- HIGH SCORE -- 
	public String GetHighScore() {
		FileReader readFile = null;
		BufferedReader reader = null;
		try {
			readFile = new FileReader("highscore.dat");
			reader = new BufferedReader(readFile);
			return reader.readLine();
		}catch(Exception e) {
			return "0";
		}
		finally {
			try {
				if(reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void CheckScore() {
		if(playerScore > Integer.parseInt(highScore1)) {
			highScore1 = ""+playerScore;

			File scoreFile = new File("highscore.dat");
			if(!scoreFile.exists()) {
				try {
					scoreFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			FileWriter writeFile = null;
			BufferedWriter writer = null;
			try {
				writeFile = new FileWriter(scoreFile);
				writer = new BufferedWriter(writeFile);
				writer.write(this.highScore1);
			}catch(Exception e) {
				//errors
			}
			finally {
				try {

					if(writer != null){
						writer.close();
					}
				}catch(Exception e) {

				}
			}
		}
	}

	// -- SPAWNING --
	// --- POWER-UPS ---
	public void spawnHealthKits()
	{
		for (int i = 0; i < 10 ; ++i){
			// Dalam posisi random dalam screen
			int x_position = 50 + randGen.nextInt(screenWidth - 100);
			int y_position = -randGen.nextInt(ENTITY_SPAWN_Y);

			hkSpawnTick++;
			if(hkSpawnTick == 10)
			{
				// Tambahkan objek health kit baru ke dalam ArrayList
				healthKits.add(new HealthKit(x_position, y_position));
				hkSpawnTick = 0;

			}			
		}
	}

	public void spawnShieldKits()
	{
		for (int i = 0; i < 10 ; ++i){
			// Dalam posisi random dalam screen
			int x_position = 50 + randGen.nextInt(screenWidth - 100);
			int y_position = -randGen.nextInt(ENTITY_SPAWN_Y);

			skSpawnTick++;
			if(skSpawnTick == 10)
			{
				// Tambahkan objek shield kit baru ke dalam ArrayList
				shieldKits.add(new ShieldKit(x_position, y_position));
				skSpawnTick = 0;

			}			
		}
	}

	/* --- ENEMIES ---
	 * Di posisi random dalam screen
	 * Tambah objek enemy baru ke dalam ArrayList
	 */
	public void spawnViruses()
	{
		for (int i = 0; i < 10 ; ++i){
			int x_position = 50 + randGen.nextInt(screenWidth - 100);
			int y_position = -randGen.nextInt(ENTITY_SPAWN_Y);
			viruses.add(new Virus(x_position, y_position));
		}
	}

	public void spawnWorms()
	{
		for (int i = 0; i < 10 ; ++i){
			int x_position = 50 + randGen.nextInt(screenWidth - 100);
			int y_position = -randGen.nextInt(ENTITY_SPAWN_Y);
			worms.add(new Worm(x_position, y_position));
		}
	}

	public void spawnTrojans()
	{
		for (int i = 0; i < 10 ; ++i){
			int x_position = 50 + randGen.nextInt(screenWidth - 100);
			int y_position = -randGen.nextInt(ENTITY_SPAWN_Y);
			trojans.add(new Trojan(x_position, y_position));
		}

	}

	public void spawnSpywares()
	{
		for (int i = 0; i < 10 ; ++i){
			int x_position = 50 + randGen.nextInt(screenWidth - 100);
			int y_position = -randGen.nextInt(ENTITY_SPAWN_Y);
			spywares.add(new Spyware(x_position, y_position));
		}
	}


	/* -- BACKGROUND MOVEMENT -- 
	 * Method untuk menggerakan background
	 */
	public void backgroundMovement(){
		bgMotion    -= 1;
		bgMotionSec += 1;
		backgroundY += 1;
	}


	/* -- COLLISION --
	 * --- ENEMIES --- 
	 */
	public void spywareCollisions()
	{
		if(playerScore >= 300) {

			ArrayList blasterShots = player.getBlasterShots();

			Rectangle playerBounds = player.getBounds();

			// Menangani collision dengan spywares
			ArrayList<Rectangle> spywaresBounds = new ArrayList<Rectangle>();
			for (int i = 0; i < spywares.size(); ++i){
				spywaresBounds.add(spywares.get(i).getBounds());
			}

			// Jika terkena blaster
			for (int i = 0; i < blasterShots.size(); ++i){
				Blaster temp = (Blaster)blasterShots.get(i);
				Rectangle blasterBounds = temp.getBounds();

				for (int j = 0; j < spywares.size(); ++j){
					if (spywaresBounds.get(j).intersects(blasterBounds) && spywares.get(j).isAlive()){
						temp.setVisible(false);
						// HP spyware berkurang
						spywares.get(j).damaged(player.getBlasterDamage());
						// Jika spyware sudah mati
						if(spywares.get(j).isAlive() == false)
						{
							// Player mendapat score 30
							playerScore += 30;
						}
					}
				}
			}


			// Jika terkena laser
			ArrayList<Laser> laserShots = player.getLaserShots();
			for (int i = 0; i < laserShots.size(); ++i){
				Laser temp = laserShots.get(i);
				Rectangle laserBounds = temp.getBounds();

				for (int j = 0; j < spywares.size(); ++j){
					if (spywaresBounds.get(j).intersects(laserBounds) && spywares.get(j).isAlive()){

						// HP spyware berkurang
						spywares.get(j).damaged(player.getLaserDamage());
						// Jika spyware sudah mati
						if(spywares.get(j).isAlive() == false)
						{
							// Player mendapat score 30
							playerScore += 30;
						}
					}
				}
			}

			// Jika menabrak player
			for (int i = 0; i < spywares.size(); ++i){
				// Jika shield tidak aktif
				if (playerBounds.intersects(spywares.get(i).getBounds()) &&
						!shield.isShieldActive() && spywares.get(i).isAlive()){

					// Kurangi HP player
					player.damaged(spywares.get(i).getDamagePoints());
					if(player.isAlive() == false)
					{
						CheckScore();
					}

					// Kurangi HP spyware
					spywares.get(i).damaged(player.getCollisionDamage());
					// Jika spyware sudah mati
					if(spywares.get(i).isAlive() == false)
					{
						// Player mendapat score 30
						playerScore += 30;
					}
				}
				
				// Jika shield aktif
				if (playerBounds.intersects(spywares.get(i).getBounds()) &&
						shield.isShieldActive() && spywares.get(i).isAlive()){

					// Kurangi HP spyware
					spywares.get(i).damaged(player.getShieldCollisionDamage());
					// Jika spyware sudah mati
					if(spywares.get(i).isAlive() == false)
					{
						// Player mendapat score 30
						playerScore += 30;
					}
					// Nonaktfikan shield
					shield.shieldActive = false;

				}
			}


			// Buat bounds di sekitar serangan spywares
			ArrayList<Rectangle> spywaresFire = new ArrayList<Rectangle>();
			for (int i = 0; i < spywareShots.size(); ++i){
				spywaresFire.add(spywareShots.get(i).getBounds());
			}

			// Cek collisions antara player dan serangan virus
			for (int i = 0; i < spywareShots.size(); ++i){

				// Jika player terkena serangan musuh saat shield tidak aktif
				if (playerBounds.intersects(spywareShots.get(i).getBounds()) &&
						!shield.isShieldActive() && spywareShots.get(i).isVisible()){
					// Maka, pertama, proyektil serangan musuh akan berhenti ditampilkan
					spywareShots.get(i).setVisible(false);
					// Lalu HP pemain akan berkurang
					player.damaged(spywares.get(i).getDamagePoints());
					if(player.isAlive() == false)
					{
						CheckScore();
					}
				}

				// Jika player terkena serangan musuh saat shield aktif
				if (playerBounds.intersects(spywareShots.get(i).getBounds()) &&
						shield.isShieldActive() && spywareShots.get(i).isVisible()){
					// Maka, pertama, proyektil serangan musuh akan berhenti ditampilkan
					spywareShots.get(i).setVisible(false);
					// Lalu shield akan berhenti aktif
					shield.shieldActive = false;
				}
			}
		}

	}

	public void virusCollisions()
	{
		ArrayList blasterShots = player.getBlasterShots();

		Rectangle playerBounds = player.getBounds();

		// Menangani collision dengan virus
		ArrayList<Rectangle> virusesBounds = new ArrayList<Rectangle>();
		for (int i = 0; i < viruses.size(); ++i){
			virusesBounds.add(viruses.get(i).getBounds());
		}

		// Jika terkena blaster
		for (int i = 0; i < blasterShots.size(); ++i){
			Blaster temp = (Blaster)blasterShots.get(i);
			Rectangle blasterBounds = temp.getBounds();

			for (int j = 0; j < viruses.size(); ++j){
				if (virusesBounds.get(j).intersects(blasterBounds) && viruses.get(j).isAlive()){
					temp.setVisible(false);
					// HP Virus berkurang
					viruses.get(j).damaged(player.getBlasterDamage());
					// Jika virus sudah mati
					if(viruses.get(j).isAlive() == false)
					{
						// Player mendapat score 5
						playerScore += 5;
					}
				}
			}
		}

		// Jika terkena laser
		ArrayList<Laser> laserShotsV = player.getLaserShots();
		for (int i = 0; i < laserShotsV.size(); ++i){
			Laser temp = laserShotsV.get(i);
			Rectangle laserBounds = temp.getBounds();

			for (int j = 0; j < viruses.size(); ++j){
				if (virusesBounds.get(j).intersects(laserBounds) && viruses.get(j).isAlive()){
					temp.setVisible(false);
					// HP Virus berkurang
					viruses.get(j).damaged(player.getLaserDamage());
					// Jika virus sudah mati
					if(viruses.get(j).isAlive() == false)
					{
						// Player mendapat score 5
						playerScore += 5;
					}
				}
			}
		}

		// Jika menabrak player
		for (int i = 0; i < viruses.size(); ++i){

			// Jika shield tidak aktif
			if (playerBounds.intersects(viruses.get(i).getBounds()) &&
					!shield.isShieldActive() && viruses.get(i).isAlive()){

				// HP player berkurang
				player.damaged(viruses.get(i).getDamagePoints());
				if(player.isAlive() == false)
				{
					CheckScore();
				}

				// HP virus berkurang
				viruses.get(i).damaged(player.getCollisionDamage());
				// Jika virus sudah mati
				if(viruses.get(i).isAlive() == false)
				{
					// Player mendapat score 5
					playerScore += 5;
				}

			}

			// Jika shield aktif
			if (playerBounds.intersects(viruses.get(i).getBounds()) &&
					shield.isShieldActive() && viruses.get(i).isAlive()){

				// HP virus berkurang
				viruses.get(i).damaged(player.getShieldCollisionDamage());
				// Jika virus sudah mati
				if(viruses.get(i).isAlive() == false)
				{
					// Player mendapat score 5
					playerScore += 5;
				}
				// Shield dinonaktifkan
				shield.shieldActive = false;

			}
		}

		// Buat bounds di sekitar serangan virus
		ArrayList<Rectangle> virusesFire = new ArrayList<Rectangle>();
		for (int i = 0; i < virusShots.size(); ++i){
			virusesFire.add(virusShots.get(i).getBounds());
		}

		// Cek collisions antara player dan serangan virus
		for (int i = 0; i < virusShots.size(); ++i){

			// Jika player terkena serangan saat shield tidak aktif
			if (playerBounds.intersects(virusShots.get(i).getBounds()) &&
					!shield.isShieldActive() && virusShots.get(i).isVisible()){
				// Maka, pertama, proyektil serangan akan berhenti ditampilkan
				virusShots.get(i).setVisible(false);
				// Lalu HP pemain akan berkurang
				player.damaged(viruses.get(i).getDamagePoints());
				if(player.isAlive() == false)
				{
					CheckScore();
				}
			}

			// Jika player terkena serangan musuh saat shield aktif
			if (playerBounds.intersects(virusShots.get(i).getBounds()) &&
					shield.isShieldActive() && virusShots.get(i).isVisible()){
				// Maka, pertama, proyektil serangan akan berhenti ditampilkan
				virusShots.get(i).setVisible(false);
				// Lalu shield akan berhenti aktif
				shield.shieldActive = false;
			}
		}

	}

	public void wormCollisions()
	{
		ArrayList blasterShots = player.getBlasterShots();

		Rectangle playerBounds = player.getBounds();

		// Untuk worms
		if(playerScore >= 100)
		{
			ArrayList<Rectangle> wormsBounds = new ArrayList<Rectangle>();

			for (int i = 0; i < worms.size(); ++i){
				wormsBounds.add(worms.get(i).getBounds());
			}

			// Jika terkena blaster
			for (int i = 0; i < blasterShots.size(); ++i){
				Blaster temp = (Blaster)blasterShots.get(i);
				Rectangle blasterBounds = temp.getBounds();

				for (int j = 0; j < worms.size(); ++j){
					if (wormsBounds.get(j).intersects(blasterBounds) && worms.get(j).isAlive()){
						// HP worm berkurang
						temp.setVisible(false);
						worms.get(j).damaged(player.getBlasterDamage());
						// Jika worm sudah mati
						if(worms.get(j).isAlive() == false)
						{
							// Player dapat skor 10
							playerScore += 10;
						}

					}
				}
			}

			// Jika terkena laser
			ArrayList<Laser> laserShotsW = player.getLaserShots();
			for (int i = 0; i < laserShotsW.size(); ++i){
				Laser temp = laserShotsW.get(i);
				Rectangle laserBounds = temp.getBounds();

				for (int j = 0; j < worms.size(); ++j){
					if (wormsBounds.get(j).intersects(laserBounds) && worms.get(j).isAlive()){
						// HP worm berkurang
						worms.get(j).damaged(player.getLaserDamage());
						temp.setVisible(false);
						// Jika worm sudah mati
						if(worms.get(j).isAlive() == false)
						{
							// Player dapat skor 10
							playerScore += 10;
						}
					}
				}
			}

			// Jika menabrak player
			for (int i = 0; i < worms.size(); ++i){

				// Jika shield tidak aktif
				if (playerBounds.intersects(worms.get(i).getBounds()) &&
						!shield.isShieldActive() && worms.get(i).isAlive()){

					// Kurangi HP Player
					player.damaged(worms.get(i).getDamagePoints());
					if(player.isAlive() == false)
					{
						CheckScore();
					}

					// Kurangi HP Worm
					worms.get(i).damaged(player.getCollisionDamage());
					// Jika worm sudah mati
					if(worms.get(i).isAlive() == false)
					{
						// Player dapat skor 10
						playerScore += 10;
					}

				}

				// Jika shield aktif
				if (playerBounds.intersects(worms.get(i).getBounds()) &&
						shield.isShieldActive() && worms.get(i).isAlive()){

					// Kurangi HP worm
					worms.get(i).damaged(player.getShieldCollisionDamage());
					// Jika worm mati
					if(worms.get(i).isAlive() == false)
					{
						// Player dapat skor 10
						playerScore += 10;
					}
					// Nonaktifkan shield
					shield.shieldActive = false;
				}
			}

			// Buat bounds disekitar serangan worm
			if(playerScore >= 100)
			{
				ArrayList<Rectangle> wormsFire = new ArrayList<Rectangle>();
				for (int i = 0; i < wormShots.size(); ++i){
					wormsFire.add(wormShots.get(i).getBounds());
				}

				// Cek collision antara player dengan serangan worm
				for (int i = 0; i < wormShots.size(); ++i){

					// Jika player terkena serangan worm ketika shield tidak aktif
					if (playerBounds.intersects(wormShots.get(i).getBounds()) &&
							!shield.isShieldActive() && wormShots.get(i).isVisible()){
						// Maka, pertama, proyektil serangan worm akan berhenti ditampilkan
						wormShots.get(i).setVisible(false);
						// Kurangi HP player
						player.damaged(worms.get(i).getDamagePoints());
						if(player.isAlive() == false)
						{
							CheckScore();
						}
					}

					// Jika player terkena tembakan worm ketika shield masih aktif
					if (playerBounds.intersects(wormShots.get(i).getBounds()) &&
							shield.isShieldActive() && wormShots.get(i).isVisible()){
						// Maka, pertama, proyektil serangan worm akan berhenti ditampilkan
						wormShots.get(i).setVisible(false);
						// Lalu shield akan berhenti aktif
						shield.shieldActive = false;
					}
				}
			}

		}
	}

	public void trojanCollisions()
	{

		ArrayList blasterShots = player.getBlasterShots();
		Rectangle playerBounds = player.getBounds();
		ArrayList<Laser> laserShots = player.getLaserShots();

		// Untuk trojans
		if(playerScore >= 200)
		{
			ArrayList<Rectangle> trojansBounds = new ArrayList<Rectangle>();

			for (int i = 0; i < trojans.size(); ++i){
				trojansBounds.add(trojans.get(i).getBounds());
			}

			// Jika terkena blaster
			for (int i = 0; i < blasterShots.size(); ++i){
				Blaster temp = (Blaster)blasterShots.get(i);
				Rectangle blasterBounds = temp.getBounds();

				for (int j = 0; j < trojans.size(); ++j){
					if (trojansBounds.get(j).intersects(blasterBounds) && trojans.get(j).isAlive()){
						temp.setVisible(false);
						// HP trojan berkurang
						trojans.get(j).damaged(player.getBlasterDamage());
						// Jika trojan sudah mati
						if(trojans.get(j).isAlive() == false)
						{
							// Player dapat skor 20
							playerScore += 20;
						}

					}
				}
			}

			// Jika terkena laser

			for (int i = 0; i < laserShots.size(); ++i){
				Laser temp = laserShots.get(i);
				Rectangle laserBounds = temp.getBounds();

				for (int j = 0; j < trojans.size(); ++j){
					if (trojansBounds.get(j).intersects(laserBounds) && trojans.get(j).isAlive()){
						// HP trojan berkurang
						trojans.get(j).damaged(player.getLaserDamage());
						temp.setVisible(false);
						// Jika trojan sudah mati
						if(trojans.get(j).isAlive() == false)
						{
							// Player dapat skor 20
							playerScore += 20;
						}
					}
				}
			}

			// Jika menabrak player
			for (int i = 0; i < trojans.size(); ++i){

				// Jika shield tidak aktif
				if (playerBounds.intersects(trojans.get(i).getBounds()) &&
						!shield.isShieldActive() && trojans.get(i).isAlive()){

					// Kurangi HP Player
					player.damaged(trojans.get(i).getCollisionDamagePoints());
					if(player.isAlive() == false)
					{
						CheckScore();
					}

					// Kurangi HP Trojan
					trojans.get(i).damaged(player.getCollisionDamage());
					// Jika trojan sudah mati
					if(trojans.get(i).isAlive() == false)
					{
						// Player dapat skor 20
						playerScore += 20;
					}

				}

				// Jika shield aktif
				if (playerBounds.intersects(trojans.get(i).getBounds()) &&
						shield.isShieldActive() && trojans.get(i).isAlive()){

					// Kurangi HP trojan
					trojans.get(i).damaged(player.getShieldCollisionDamage());
					// Jika trojan mati
					if(trojans.get(i).isAlive() == false)
					{
						// Player dapat skor 20
						playerScore += 20;
					}
					// Nonaktifkan shield
					shield.shieldActive = false;

				}


			}

			// Buat bounds disekitar serangan trojan
			if(playerScore >= 200)
			{
				ArrayList<Rectangle> trojansFire = new ArrayList<Rectangle>();
				for (int i = 0; i < trojanShots.size(); ++i){
					trojansFire.add(trojanShots.get(i).getBounds());
				}

				// Cek collision antara player dengan serangan trojan
				for (int i = 0; i < trojanShots.size(); ++i){

					// Jika player terkena serangan trojan ketika shield tidak aktif
					if (playerBounds.intersects(trojanShots.get(i).getBounds()) &&
							!shield.isShieldActive() && trojanShots.get(i).isVisible()){
						// Maka, pertama, proyektil serangan trojan akan berhenti ditampilkan
						trojanShots.get(i).setVisible(false);
						// Kurangi HP player
						player.damaged(trojans.get(i).getDamagePoints());
						if(player.isAlive() == false)
						{
							CheckScore();
						}
					}

					// Jika player terkena serangan trojan ketika shield masih aktif
					if (playerBounds.intersects(trojanShots.get(i).getBounds()) &&
							shield.isShieldActive() && trojanShots.get(i).isVisible()){
						// Maka, pertama, proyektil serangan trojan akan berhenti ditampilkan
						trojanShots.get(i).setVisible(false);
						// Lalu shield berhenti aktif
						shield.shieldActive = false;
					}
				}
			}

		}

	}

	public void detectCollisions(){

		ArrayList blasterShots = player.getBlasterShots();

		Rectangle playerBounds = player.getBounds();

		ArrayList<Rectangle> hkBounds = new ArrayList<Rectangle>();

		// Menangani collision dengan health kit
		for (int i = 0; i < healthKits.size(); ++i){
			hkBounds.add(healthKits.get(i).getBounds());
		}

		for (int i = 0; i < healthKits.size(); ++i){
			if (playerBounds.intersects(healthKits.get(i).getBounds()) && healthKits.get(i).isAlive() && player.getHealthPoints() < player.getMaxHealthPoints()){
				player.setHealthPoints(player.getHealthPoints() + healthKits.get(i).getHealingPoints());
				healthKits.get(i).setAlive(false);
			}
			else if (playerBounds.intersects(healthKits.get(i).getBounds()) && healthKits.get(i).isAlive() && player.getHealthPoints() == player.getMaxHealthPoints()){
				healthKits.get(i).setAlive(false);
			}

		}

		// Menangani collision dengan shield kit
		for (int i = 0; i < shieldKits.size(); ++i){
			hkBounds.add(shieldKits.get(i).getBounds());
		}

		for (int i = 0; i < shieldKits.size(); ++i){
			if (playerBounds.intersects(shieldKits.get(i).getBounds()) &&
					!shield.isShieldActive() && shieldKits.get(i).isAlive()){
				shield.shieldActive = true;
				shieldKits.get(i).setAlive(false);
			}

			if (playerBounds.intersects(shieldKits.get(i).getBounds()) &&
					shield.isShieldActive() && shieldKits.get(i).isAlive()){
				shieldKits.get(i).setAlive(false);
			}

		}


		spywareCollisions();
		virusCollisions();
		wormCollisions();
		trojanCollisions();


		if (!player.isAlive()){
			isGameEnd = true;
		}



	}
	
	/* Method ini mengulang semua action yang dilakukan dalam game tiap x ms
	 * dimana x adalah nilai dalam Timer(x,this)
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		if ((isGameStarted || !isGamePaused) && keyStat == 1){
			if (player.isAlive()){
				if (player.isKeyLeft() && player.getX() > 20){
					player.moveLeft();
				}
				if (player.isKeyRight() && player.getX() < (screenWidth - 116)){
					player.moveRight();
				}
				if (player.isKeyUp() && player.getY() > 20){
					player.moveForward();
				}
				if (player.isKeyDown() && player.getY() < (screenHeight - 118)){
					player.moveBack();
				}

				if (player.getBlasterDelay() > 0){
					player.setBlasterDelay();
				}
				if (player.isBlaster() && player.getBlasterDelay() == 0){
					player.generateBlaster();
				}
				if (player.isLaser()){
					player.generateLaser();
				}

				if (player.isKeyLeft()){
					player.setImage("images/spacecraft-turn-left.png");
				}
				else if (player.isKeyRight()){
					player.setImage("images/spacecraft-turn-right.png");
				}
				else {
					player.setImage("images/spacecraft.png");
				}
			}


			backgroundMovement();

			if (player.isAlive()){
				shield.moveShield(player);
			}

			if (player.isAlive()){
				detectCollisions();
				movePlayerAttacks();
			}

			if (!player.isAlive()){
				player.moveDeadPlayer();
			}

			moveViruses();
			virusesFire();

			moveWorms();
			wormsFire();

			moveTrojans();
			trojansFire();

			moveSpywares();
			spywaresFire();

			moveHealthKits();
			moveShieldKits();

			repaint();
		}
	}

	/* -- ENTITY MOVEMENT -- 
	 * Method-method untuk mengdendalikan pergerakan entity: PowerUp dan Enemy
	 */

	// --- POWER UP
	public void moveHealthKits(){
		Random rand = new Random();

		for (int i = 0; i < healthKits.size(); ++i){
			HealthKit hk = healthKits.get(i);

			if (healthKits.get(i).isAlive()){
				healthKits.get(i).moveForward(defaultSpeed);
				if (i % 2 == 0 && healthKits.get(i).getY() > 0){
					if (healthKits.get(i).getX() > 0 && healthKits.get(i).getX() < 400){
						healthKits.get(i).moveRight();
					}
					if (healthKits.get(i).getX() > 500 && healthKits.get(i).getX() < screenWidth - 60){
						healthKits.get(i).moveLeft();
					}
				}
			}

			if (!hk.isAlive() || hk.getY() > screenHeight){
				int x_position = 50 + rand.nextInt(screenWidth - 100);
				int y_position = -rand.nextInt(ENTITY_SPAWN_Y);
				hk = new HealthKit(x_position, y_position);

				healthKits.set(i, hk);
			}
		}
	}

	public void moveShieldKits(){
		Random rand = new Random();

		for (int i = 0; i < shieldKits.size(); ++i){
			ShieldKit sk = shieldKits.get(i);

			if (shieldKits.get(i).isAlive()){
				shieldKits.get(i).moveForward(defaultSpeed);
				if (i % 2 == 0 && shieldKits.get(i).getY() > 0){
					if (shieldKits.get(i).getX() > 0 && shieldKits.get(i).getX() < 400){
						shieldKits.get(i).moveRight();
					}
					if (shieldKits.get(i).getX() > 500 && shieldKits.get(i).getX() < screenWidth - 60){
						shieldKits.get(i).moveLeft();
					}
				}
			}

			if (!sk.isAlive() || sk.getY() > screenHeight){
				int x_position = 50 + rand.nextInt(screenWidth - 100);
				int y_position = -rand.nextInt(ENTITY_SPAWN_Y);
				sk = new ShieldKit(x_position, y_position);

				shieldKits.set(i, sk);
			}
		}
	}

	// --- ENEMY -- 
	public void moveSpywares(){
		Random rand = new Random();

		for (int i = 0; i < spywares.size(); ++i){
			Spyware spyware = spywares.get(i);

			if (spywares.get(i).isAlive()){
				spywares.get(i).moveForward(defaultSpeed);
				if (i % 2 == 0 && spywares.get(i).getY() > 0){
					if (spywares.get(i).getX() > 0 && spywares.get(i).getX() < 400){
						spywares.get(i).moveRight();
					}
					if (spywares.get(i).getX() > 500 && spywares.get(i).getX() < screenWidth - 60){
						spywares.get(i).moveLeft();
					}
				}
			}

			if (!spyware.isAlive() || spyware.getY() > screenHeight){
				int x_position = 50 + rand.nextInt(screenWidth - 100);
				int y_position = -rand.nextInt(ENTITY_SPAWN_Y);
				spyware = new Spyware(x_position, y_position);

				spywares.set(i, spyware);
			}
		}


	}

	public void moveViruses(){
		Random rand = new Random();

		for (int i = 0; i < viruses.size(); ++i){
			Virus virus = viruses.get(i);

			if (viruses.get(i).isAlive()){
				viruses.get(i).moveForward(virusSpeed);
				if (i % 2 == 0 && viruses.get(i).getY() > 0){
					if (viruses.get(i).getX() > 0 && viruses.get(i).getX() < 400){
						viruses.get(i).moveRight();
					}
					if (viruses.get(i).getX() > 500 && viruses.get(i).getX() < screenWidth - 60){
						viruses.get(i).moveLeft();
					}
				}
			}

			if (!virus.isAlive() || virus.getY() > screenHeight){
				int x_position = 50 + rand.nextInt(screenWidth - 100);
				int y_position = -rand.nextInt(ENTITY_SPAWN_Y);
				virus = new Virus(x_position, y_position);

				viruses.set(i, virus);
			}
		}
	}

	public void moveWorms(){
		Random rand = new Random();

		for (int i = 0; i < worms.size(); ++i){
			Worm worm = worms.get(i);

			if (worms.get(i).isAlive()){
				worms.get(i).moveForward(wormSpeed);
				if (i % 2 == 0 && worms.get(i).getY() > 0){
					if (worms.get(i).getX() > 0 && worms.get(i).getX() < 400){
						worms.get(i).moveRight();
					}
					if (worms.get(i).getX() > 500 && worms.get(i).getX() < screenWidth - 60){
						worms.get(i).moveLeft();
					}
				}
			}

			if (!worm.isAlive() || worm.getY() > screenHeight){
				int x_position = 50 + rand.nextInt(screenWidth - 100);
				int y_position = -rand.nextInt(ENTITY_SPAWN_Y);
				worm = new Worm(x_position, y_position);

				worms.set(i, worm);
			}
		}
	}

	public void moveTrojans(){
		Random rand = new Random();

		for (int i = 0; i < trojans.size(); ++i){
			Trojan trojan = trojans.get(i);

			if (trojans.get(i).isAlive()){
				trojans.get(i).moveForward(trojanSpeed);
				if (i % 2 == 0 && trojans.get(i).getY() > 0){
					if (trojans.get(i).getX() > 0 && trojans.get(i).getX() < 400){
						trojans.get(i).moveRight();
					}
					if (trojans.get(i).getX() > 500 && trojans.get(i).getX() < screenWidth - 60){
						trojans.get(i).moveLeft();
					}
				}
			}

			if (!trojan.isAlive() || trojan.getY() > screenHeight){
				int x_position = 50 + rand.nextInt(screenWidth - 100);
				int y_position = -rand.nextInt(ENTITY_SPAWN_Y);
				trojan = new Trojan(x_position, y_position);

				trojans.set(i, trojan);
			}
		}
	}

	/* -- ATTACK --
	 * Method-method untuk mengatur serangan-serangan enemy dan player
	 */

	private static Random rand = new Random();
	
	// --- ENEMIES ---
	public void spywaresFire(){
		int countTime = 2000;
		if (countTime - 10 > 1){
			int randIndex = rand.nextInt(spywares.size());
			randIndex     = Math.abs(randIndex);
			Spyware spyware = spywares.get(randIndex);
			int fireDelay = spyware.getCurrentFireDelay();

			if (fireDelay == 0 && spyware.isAlive()){
				SpywareAttack fire = spywares.get(randIndex).shoot();
				spywareShots.add(fire);
				spyware.setCurrentFireDelay(fire.getFireDelay());
			} else {
				spyware.setCurrentFireDelay(fireDelay - 1);
			}
			countTime = 0;
		}
		countTime++;

		for (int i = 0; i < spywareShots.size(); ++i){
			SpywareAttack fireShot = spywareShots.get(i);
			if (fireShot.isVisible()){
				fireShot.moveShot();
			} else {
				spywareShots.remove(i);
			}
		}
	}

	public void virusesFire(){
		int countTime = 2000;
		if (countTime - 10 > 1){
			int randIndex = rand.nextInt(viruses.size());
			randIndex     = Math.abs(randIndex);
			Virus enemy = viruses.get(randIndex);
			int fireDelay = enemy.getCurrentFireDelay();

			if (fireDelay == 0 && enemy.isAlive()){
				VirusAttack fire = viruses.get(randIndex).shoot();
				virusShots.add(fire);
				enemy.setCurrentFireDelay(fire.getFireDelay());
			} else {
				enemy.setCurrentFireDelay(fireDelay - 1);
			}
			countTime = 0;
		}
		countTime++;

		for (int i = 0; i < virusShots.size(); ++i){
			VirusAttack fireShot = virusShots.get(i);
			if (fireShot.isVisible()){
				fireShot.moveShot();
			} else {
				virusShots.remove(i);
			}
		}
	}

	public void wormsFire(){
		int countTime = 2000;
		if (countTime - 10 > 1){
			int randIndex = rand.nextInt(worms.size());
			randIndex     = Math.abs(randIndex);
			Worm worm = worms.get(randIndex);
			int fireDelay = worm.getCurrentFireDelay();

			if (fireDelay == 0 && worm.isAlive()){
				WormAttack fire = worms.get(randIndex).shoot();
				wormShots.add(fire);
				worm.setCurrentFireDelay(fire.getFireDelay());
			} else {
				worm.setCurrentFireDelay(fireDelay - 1);
			}
			countTime = 0;
		}
		countTime++;
		for (int i = 0; i < wormShots.size(); ++i){
			WormAttack fireShot = wormShots.get(i);
			if (fireShot.isVisible()){
				fireShot.moveShot();
			} else {
				wormShots.remove(i);
			}
		}
	}

	public void trojansFire(){
		int countTime = 2000;
		if (countTime - 10 > 1){
			int randIndex = rand.nextInt(trojans.size());
			randIndex     = Math.abs(randIndex);
			Trojan trojan = trojans.get(randIndex);
			int fireDelay = trojan.getCurrentFireDelay();

			if (fireDelay == 0 && trojan.isAlive()){
				TrojanAttack fire = trojans.get(randIndex).shoot();
				trojanShots.add(fire);
				trojan.setCurrentFireDelay(fire.getFireDelay());
			} else {
				trojan.setCurrentFireDelay(fireDelay - 1);
			}
			countTime = 0;
		}
		countTime++;

		for (int i = 0; i < trojanShots.size(); ++i){
			TrojanAttack fireShot = trojanShots.get(i);
			if (fireShot.isVisible()){
				fireShot.moveShot();
			} else {
				trojanShots.remove(i);
			}
		}
	}
	
	// --- PLAYER --- 
	public void movePlayerAttacks(){
		ArrayList blasterShots = player.getBlasterShots();
		for (int i = 0; i < blasterShots.size(); ++i){
			Blaster temp = (Blaster)blasterShots.get(i);
			if (temp.isVisible() == true){
				temp.moveShot();
			} else {
				blasterShots.remove(i);
			}
		}

		ArrayList<Laser> laserShots = player.getLaserShots();
		for (int i = 0; i < laserShots.size(); ++i){
			Laser temp = laserShots.get(i);
			if (temp.isVisible() == true){
				temp.moveShot();
			} else {
				laserShots.remove(i);
			}
		}

	}

	// -- DRAWING METHODS -- 
	@Override
	public void paint(Graphics g){

		super.paint(g);
		Graphics2D graphics2D = (Graphics2D)g;

		if (!isGameStarted && keyStat == 0){
			drawGameMenu(graphics2D);

		} 
		else if(!isGameStarted && keyStat == 1) {
			drawEscapeMenu(graphics2D);
			backgroundMovement();

		}

		else {

			drawBackground(graphics2D);

			drawAttacks(graphics2D);


			drawHealthKits(graphics2D);
			drawShieldKits(graphics2D);

			drawViruses(graphics2D);
			if(playerScore >= 100)
			{
				drawWorms(graphics2D);
			}

			if(playerScore >= 200)
			{
				drawTrojans(graphics2D);
			}

			if(playerScore >= 300)
			{
				drawSpywares(graphics2D);
			}

			drawPlayers(graphics2D);

			if (shield.isShieldActive()){
				graphics2D.drawImage(shield.getImage(), shield.shieldX(), shield.shieldY(), null);
			}

			drawStats(graphics2D);
			if (isGameEnd){
				// Buat font baru untuk HUD
				try
				{
					GravityBold8 = Font.createFont(Font.TRUETYPE_FONT, new File("GravityBold8.ttf")).deriveFont(50f);
					GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
					ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("GravityBold8.ttf")));
				} catch(IOException | FontFormatException E)
				{

				}

				graphics2D.setColor(new Color(0, 0, 0, 150));
				int width = screenWidth;
				int height = screenHeight;
				graphics2D.fillRect(0, 0, width, height);

				graphics2D.setFont(GravityBold8);
				graphics2D.setColor(Color.WHITE);
				graphics2D.drawString("YOU DIED!", (screenWidth - 200) / 2 - 75, screenHeight - 490);
				graphics2D.setColor(new Color(255, 20, 0));
				graphics2D.drawString("PRESS ENTER TO RESTART", (screenWidth - 200) / 2 - 375, screenHeight - 425);

				graphics2D.setFont(GravityBold8);
				graphics2D.setColor(Color.WHITE);
				graphics2D.drawString("SCORE: " + playerScore, (screenWidth - 90) / 2 - 150, screenHeight / 2 + 100);
				g.setColor(new Color(255, 214, 0));
				graphics2D.drawString("LATEST HIGH SCORE: " + highScore1, (screenWidth - 300) / 2 - 300, screenHeight / 2 + 165);
				isGameStarted = false;
				keyStat = 2;


			}
		}
		if(highScore1.equals("")) {
			highScore1 = this.GetHighScore();
		}
	}

	// --- DRAWING MENUS -- 
	public void drawGameMenu(Graphics g){
		menu.drawGameMenu(g, backgroundMenu, BUTTON_PADDING_TOP,
				isStartButtonHovered, isExitButtonHovered,
				isSettingsButtonHovered, isBackButtonHovered);

		repaint();
	}
	public void drawEscapeMenu(Graphics g) {
		esMenu.drawEscapeMenu(g, background,BUTTON_PADDING_TOP, isContinueButtonHovered, isBackButtonHovered1);

		repaint();
	}
	
	/* -- DRAWING BACKGROUND --
	 * Method untuk menggambar dan mengatur gerakan background 
	 */
	public void drawBackground(Graphics g){
		if ((backgroundY - 0) % (background.getHeight(null) * 2) == 0){
			bgMotionSec = 0;
		} else if ((backgroundY - background.getHeight(null)) % (background.getHeight(null) * 2) == 0){
			bgMotion = (background.getHeight(null) * 2);
		}
		g.drawImage(background, 0, background.getHeight(null) - bgMotion, null);
		if (backgroundY > 0){
			g.drawImage(background, 0, -(background.getHeight(null) - bgMotionSec), null);
		}
	}

	/* -- DRAWING ATTACKS --
	 * Method untuk draw serangan-serangan player dan enemy dalam game
	 */
	public void drawAttacks(Graphics g){
		if (player.isAlive()){
			/* paint blaster beams */
			/* create arraylist to store blaster shots array */
			ArrayList blasterShots = player.getBlasterShots();
			for (int i = 0; i < blasterShots.size(); ++i){
				Blaster temp = (Blaster)blasterShots.get(i);
				g.drawImage(temp.getAttackImg(), temp.getXPos(), temp.getYPos(), null);
			}
			// paint laser
			ArrayList<Laser> laserShots = player.getLaserShots();
			for (int i = 0; i < laserShots.size(); ++i){
				Laser temp = laserShots.get(i);
				g.drawImage(temp.getAttackImg(), temp.getXPos(), temp.getYPos(), null);
			}
		}


		// -- VIRUS --- 
		for (int i = 0; i < virusShots.size(); ++i){
			VirusAttack fire = virusShots.get(i);
			if (fire.isVisible()){
				g.drawImage(fire.getEnemyAttackImg(), fire.getXPos(), fire.getYPos(), null);
			}
		}

		// --- WORM --- 
		if(playerScore >= 100)
		{
			for (int i = 0; i < wormShots.size(); ++i){
				WormAttack fire = wormShots.get(i);
				if (fire.isVisible()){
					g.drawImage(fire.getEnemyAttackImg(), fire.getXPos(), fire.getYPos(), null);
				}
			}

		}

		if(playerScore >= 200)
		{
			//trojan's weapon
			for (int i = 0; i < trojanShots.size(); ++i){
				TrojanAttack fire = trojanShots.get(i);
				if (fire.isVisible()){
					g.drawImage(fire.getEnemyAttackImg(), fire.getXPos(), fire.getYPos(), null);
				}
			}

		}

		if(playerScore >= 300)
		{
			for (int i = 0; i < spywareShots.size(); ++i){
				SpywareAttack fire = spywareShots.get(i);
				if (fire.isVisible()){
					g.drawImage(fire.getEnemyAttackImg(), fire.getXPos(), fire.getYPos(), null);
				}
			}
		}



	}

	/* -- DRAWING ENTITY -- 
	 * Method-method untuk draw entity (Power Up, Enemy) dan player dalam game
	 */

	// --- POWER UP ---
	public void drawHealthKits(Graphics g){
		for (int i = 0; i < healthKits.size(); ++i){
			if (healthKits.get(i).isAlive()){
				g.drawImage(healthKits.get(i).getImage(), healthKits.get(i).getX(), healthKits.get(i).getY(), null);
			}else {
				g.drawImage(healthKits.get(i).getImage(), healthKits.get(i).getX(), healthKits.get(i).getY(), null);
			}
		}
	}

	public void drawShieldKits(Graphics g){
		for (int i = 0; i < shieldKits.size(); ++i){
			if (shieldKits.get(i).isAlive()){
				g.drawImage(shieldKits.get(i).getImage(), shieldKits.get(i).getX(), shieldKits.get(i).getY(), null);
			}else {
				g.drawImage(shieldKits.get(i).getImage(), shieldKits.get(i).getX(), shieldKits.get(i).getY(), null);
			}
		}
	}

	// --- ENEMY --- 
	public void drawSpywares(Graphics g){
		for (int i = 0; i < spywares.size(); ++i){
			if (spywares.get(i).isAlive()){
				g.drawImage(spywares.get(i).getImage(), spywares.get(i).getX(), spywares.get(i).getY(), null);
			}else {
				g.drawImage(spywares.get(i).getImage(), spywares.get(i).getX(), spywares.get(i).getY(), null);
			}
		}
	}

	public void drawViruses(Graphics g){
		for (int i = 0; i < viruses.size(); ++i){
			if (viruses.get(i).isAlive()){
				g.drawImage(viruses.get(i).getImage(), viruses.get(i).getX(), viruses.get(i).getY(), null);
			}else {
				g.drawImage(viruses.get(i).getImage(), viruses.get(i).getX(), viruses.get(i).getY(), null);
			}
		}
	}

	public void drawWorms(Graphics g){
		for (int i = 0; i < worms.size(); ++i){
			if (worms.get(i).isAlive()){
				g.drawImage(worms.get(i).getImage(), worms.get(i).getX(), worms.get(i).getY(), null);
			}else {
				g.drawImage(worms.get(i).getImage(), worms.get(i).getX(), worms.get(i).getY(), null);
			}
		}
	}

	public void drawTrojans(Graphics g){
		for (int i = 0; i < trojans.size(); ++i){
			if (trojans.get(i).isAlive()){
				g.drawImage(trojans.get(i).getImage(), trojans.get(i).getX(), trojans.get(i).getY(), null);
			}else {
				g.drawImage(trojans.get(i).getImage(), trojans.get(i).getX(), trojans.get(i).getY(), null);
			}
		}
	}

	// --- PLAYER ---- 
	public void drawPlayers(Graphics g){
		if (player.isAlive()){
			g.drawImage(player.getImage(), player.getX(), player.getY(), null);
		} else {
			player.setImage("images/explosion.gif");
			g.drawImage(player.getImage(), player.getX(), player.getY(), null);
		}

	}

	/* Method untuk draw stat player, meliputi
	 * Health Point
	 * Score
	 * Shield
	 */
	public void drawStats(Graphics g){	

		// Buat font baru untuk HUD
		try
		{
			GravityBold8 = Font.createFont(Font.TRUETYPE_FONT, new File("GravityBold8.ttf")).deriveFont(20f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("GravityBold8.ttf")));
		} catch(IOException | FontFormatException E)
		{

		}

		// Untuk membantu mengatur posisi HUD
		int baseY = 60;
		int interval = 30;
		int baseX = 1500;

		g.setColor(new Color(1, 14, 22, 0));
		int width = screenWidth;
		int height = screenHeight;
		g.fillRect(screenWidth - baseX - 100, baseY + interval - 100, 525, 250);

		// Tampilkan teks player
		g.setFont(GravityBold8);
		g.setColor(Color.WHITE);
		g.drawString("PLAYER", screenWidth - baseX, baseY);

		// Tampilkan teks Health Point
		g.setColor(new Color(255, 0, 202));
		if(player.getHealthPoints() < player.getMaxHealthPoints()) g.drawString("HEALTH POINT: " + player.getHealthPoints(), screenWidth - baseX, baseY + interval);
		else g.drawString("HEALTH POINT: (MAX) " + player.getHealthPoints(), screenWidth - baseX, baseY + interval);

		// Tampilkan teks Score
		g.setColor(Color.WHITE);
		g.drawString("HIGH SCORE: " + highScore1, screenWidth - baseX, baseY + 2 * interval + 20);
		g.setColor(new Color(255, 214, 0));
		g.drawString("SCORE: " + playerScore, screenWidth - baseX, baseY + 3 * interval + 20);

		// Tampilkan teks Shield
		g.setColor(Color.WHITE);
		g.drawString("SHIELD", screenWidth - baseX, baseY + 4 * interval + 40);
		if (shield.isShieldActive()){
			g.setColor(new Color(0, 218, 251));
			g.drawString("ACTIVE", screenWidth - baseX, baseY + 5 * interval + 40);
		} else {
			g.setColor(new Color(255, 20, 0));
			g.drawString("INACTIVE", screenWidth - baseX, baseY + 5 * interval + 40);
		}
	}


}
