package main;

import java.awt.Graphics;
import java.util.Random;
import background.BackgroundManager;
import entities.Player;
import entities.Enemy;

public class Game implements Runnable {
	
	// variables for game scene
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	private Player player;
	
	// static declarations 
	private final static long RESPAWN_TIME = 3;
	private final int RESPAWN_COUNT = 3;		
	private final static int TILE_DEFAULT_SIZE = 32;
	public final static int NORMAL_ENTITY_WIDTH = 65;
	public final static int NORMAL_ENTITY_HEIGHT = 70;
	public final static float SCALE = 1.3f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 20;
	public final static int TILES_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE *TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	// variables for enemies
	private Enemy[] enemies = new Enemy[100];
	private Random rand = new Random();
	private int i, currentEnemyIndex = 0;

	// variables for background manager
	private BackgroundManager bgManager;
	
	public Game() {
		initialize();
		gamePanel = new GamePanel(this);

		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();

		startGameLoop();
	}

	private void initialize() {
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
		for (i=0; i<RESPAWN_COUNT; i++) {
			enemies[currentEnemyIndex] = new Enemy(rand.nextFloat()*GAME_WIDTH, rand.nextFloat()*GAME_HEIGHT, NORMAL_ENTITY_WIDTH, NORMAL_ENTITY_HEIGHT);
			enemies[currentEnemyIndex++].loadBgData(bgManager.getCurrBg().getBgData());
		}
	}

	public void update() {
		player.update();
		bgManager.update();
		for (i=0; i<currentEnemyIndex; i++) {
			enemies[i].update();
		}
	}

	public void render(Graphics g) {
		bgManager.draw(g);
		player.render(g);
		for (i=0; i<currentEnemyIndex; i++) {
			enemies[i].render(g);
		}

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
		player.resetDirectionBooleans();
	}

	public Player getPlayer() {
		return player;
	}
}