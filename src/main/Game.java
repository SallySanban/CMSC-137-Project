package main;

import java.awt.Graphics;
import entities.Player;
import entities.Enemy;
import java.util.Random;


public class Game implements Runnable {

	static final long RESPAWN_TIME = 3;
	static final int RESPAWN_COUNT = 3;
	
	private GameWindow gameWindow;
	private Random rand = new Random();
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	private Player player;
	private Enemy[] enemies = new Enemy[100];
	private int currentEnemyIndex = 0;
	private int i;
	
	public Game() {
		initialize();
		gamePanel = new GamePanel(this);
		
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		
		startGameLoop();		
	}
	
	private void initialize() {
		player = new Player(200, 200);
		
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	private void generateEnemy() {
		for (i=0; i<RESPAWN_COUNT; i++) {
			enemies[currentEnemyIndex++] = new Enemy(rand.nextFloat()*GamePanel.WINDOW_WIDTH, rand.nextFloat()*GamePanel.WINDOW_HEIGHT);
		}
	}
	
	public void update() {
		player.update();
		for (i=0; i<currentEnemyIndex; i++) {
			enemies[i].update();
		}
	}
	
	public void render(Graphics g) {
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
		long respawnCounter = previousTime;					// time counter for enemy respawn 
		long currentTimeInSeconds, currentTime;	// separate currentTime for performance improvement
		
		double deltaU = 0;
		double deltaF = 0;
		
		while(true) {
			
			currentTime = System.nanoTime();
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