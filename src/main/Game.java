package main;

// java imports
import java.awt.Graphics;
import gamestates.GameState;
import gamestates.Menu;
import gamestates.Playing;
import java.util.Random;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import background.BackgroundManager;

// character imports
import entities.*;
import entities.zombies.*;
import entities.zombies.parent.*;

public class Game implements Runnable {
	
	// variables for game scene
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

	private Playing playing;
	private Menu menu;


	// public final static int TILE_DEFAULT_SIZE = 32;
	private Player player;
	
	// static declarations 
	private final static long RESPAWN_TIME = 3;
	private final int RESPAWN_COUNT = 3;		
	private final static int TILE_DEFAULT_SIZE = 32;
	private final static float ENEMY_ENTITY_GRAPHICS_MULTIPLIER = 1.3f;
	public final static int NORMAL_ENTITY_WIDTH = 65;
	public final static int NORMAL_ENTITY_HEIGHT = 70;
	public final static float SCALE = 1.3f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 20;
	public final static int TILES_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE *TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	public final static int MAX_ENEMY_COUNT = 100;
	
	// variables for enemies
	public Zombie[] enemies = new Zombie[900];
	private Random rand = new Random();
	private int i;
	private Font fontStyle = new Font(Font.SANS_SERIF,  Font.BOLD, 20);
	public int currentEnemyIndex = 0;
	public JLabel menuText;

	// function that is called when an enemy has been hit
	public void hitEnemy(int index) {
		
		// kill enemy for now
		for (i=index; i<currentEnemyIndex-1; i++) {
			enemies[i] = enemies[i+1];
		}
		enemies[currentEnemyIndex--] = null;
		player.addPower();
		gamePanel.menuText.setText("Health: " + player.HPvalue + ", Power: " + player.powerValue);
	}
	
	// variables for background manager
	private BackgroundManager bgManager;
	
	public Game() {
		initialize();
		menuText = new JLabel("Health: " + player.HPvalue + ", Power: " + player.powerValue, SwingConstants.CENTER);
		menuText.setFont(fontStyle);
		menuText.setAlignmentX(Component.CENTER_ALIGNMENT);
		gamePanel = new GamePanel(this, menuText);
		gameWindow = new GameWindow(gamePanel, player);
		gamePanel.requestFocus();

		startGameLoop();
	}

	private void initialize() {
		menu = new Menu(this);
		playing = new Playing(this);
		bgManager = new BackgroundManager(this);
		player = new Player(200, 150, NORMAL_ENTITY_WIDTH, NORMAL_ENTITY_HEIGHT);
		player.loadBgData(bgManager.getCurrBg().getBgData());
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	

	// function that generates enemies according to the set RESPAWN_COUNT
	private void generateEnemy() {
		if(!playing.gamePaused) {
			for (i=0; i<RESPAWN_COUNT; i++) {
				if (currentEnemyIndex < MAX_ENEMY_COUNT) {
					enemies[currentEnemyIndex] = new ZombieMan(
							0.9f*rand.nextFloat()*GAME_WIDTH, 
							(0.5f*rand.nextFloat())*GAME_HEIGHT, 
							(int) (NORMAL_ENTITY_WIDTH*ENEMY_ENTITY_GRAPHICS_MULTIPLIER), 
							(int) (NORMAL_ENTITY_WIDTH*ENEMY_ENTITY_GRAPHICS_MULTIPLIER),
							this.player
					);
					enemies[currentEnemyIndex++].loadBgData(bgManager.getCurrBg().getBgData());
				} else {
					System.out.print("Too many enemies: Stopped enemy respawn until some enemies are removed. ");
					break;
				}
			}
			System.out.println("There are now " + currentEnemyIndex + " enemies.");
		}
		
	}

	public void update() {
		if(!playing.gamePaused) {
			switch(GameState.state){
			case MENU:
				menu.update();
				break;
			case PLAYING:
				playing.update();
				for (i=0; i<currentEnemyIndex; i++) {
					enemies[i].update();
				}
				break;
			default:
				break;
			}
		}
		
		// player.update();
		// bgManager.update();
		
	}
	
	public void render(Graphics g) {
		switch(GameState.state){
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);
			for (i=0; i<currentEnemyIndex; i++) {
			enemies[i].render(g);
			}
			break;
		default:
			break;
		}
		// bgManager.draw(g);
		// player.render(g);
		

	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0/FPS_SET;
		double timePerUpdate = 1000000000.0/UPS_SET;
		
		long previousTime = System.nanoTime();
		long respawnCounter = previousTime;			// time counter for enemy respawn 
		long currentTimeInSeconds, currentTime;		// separate currentTime for performance improvement

		double deltaU = 0;
		double deltaF = 0;

		while(true) {
			
			currentTime = System.nanoTime();
			
			// computes time that passed ever since start
			currentTimeInSeconds = (currentTime-respawnCounter) / 1000000000;
			
			// if 3 seconds have passed already
			if (currentTimeInSeconds > RESPAWN_TIME) {
				System.out.println("3 seconds has passed.");
				generateEnemy();
				respawnCounter = System.nanoTime(); // reset respawn counter
			}

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if(deltaU >= 1) {
				update();
				deltaU--;
			}

			if(deltaF >= 1) {
				gamePanel.repaint();
				deltaF--;
			}
		}
	}

	public void windowFocusLost() {
		if(GameState.state == GameState.PLAYING){
			playing.getPlayer().resetDirectionBooleans();
		}
	}

	public Menu getMenu(){
		return menu;
	}

	public Playing getPlaying(){
		return playing;
	}

	public GamePanel getGamePanel(){
		return this.gamePanel;
	}
}