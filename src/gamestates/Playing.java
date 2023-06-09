package gamestates;

import java.awt.Graphics;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

import background.BackgroundManager;
import entities.Enemy;
import entities.Player;
import main.Game;
import main.GamePanel;

import utils.LoadSave;

public class Playing extends State implements Statemethods{
	protected Player player;
	protected Enemy opponent;
	private BackgroundManager bgManager;
	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int lvlTilesWide = LoadSave.GetBgData()[0].length;
	private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxlvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
	private BufferedImage backgroundImg;
	public boolean gamePaused = false;
	public int state = 0;
	public int enemystate = 0;

	private Socket socket;
	private ReadFromServer rfsRunnable;
	private WriteToServer wtsRunnable;

	private int playerID;

	public Playing(Game game) {
		super(game);
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.GAME_BG_IMAGE);


	}
	private void initialize(float playerx, float playery, float enemyx, float enemyy) {
		bgManager = new BackgroundManager(game);
		opponent = new Enemy(enemyx, enemyy, 65, 70);
		player = new Player(playerx, playery, 65, 70);
		player.loadBgData(bgManager.getCurrBg().getBgData());

	}


	@Override
	public void update() {
		if(!gamePaused) {
			bgManager.update();
			player.update();
//			checkCloseToBorder();
		}


	}

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitbox().x;
		int diff = playerX - xLvlOffset;

		if(diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if(diff< leftBorder)
			xLvlOffset += diff - leftBorder;

		if(xLvlOffset > maxlvlOffsetX)
			xLvlOffset = maxlvlOffsetX;
		else if(xLvlOffset < 0)
			xLvlOffset = 0;
	}

	@Override
	public void draw(Graphics g) {
		for(int i = 0; i<3; i++){
			g.drawImage(backgroundImg, (i*Game.GAME_WIDTH)-xLvlOffset, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		}

		//bgManager.draw(g, xLvlOffset);
		bgManager.draw(g);
		//player.render(g, xLvlOffset);
		player.render(g);
		opponent.render(g);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			player.setAttack(true, game.getGamePanel(), opponent);
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		// updated code by Yves: Make arrow keys also functional (omit switch case usage)
		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			player.setJump(true);
		} else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
			player.setLeft(true);
		} else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			player.setDown(true);
		} else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
			player.setRight(true);


		// Yves also added space for easier debugging attack functionality
		} else if (keyCode == KeyEvent.VK_SPACE) {
			player.setAttack(true, game.getGamePanel(), opponent);
		}

		else if(keyCode == KeyEvent.VK_ESCAPE) {
			gamePaused = true;
			GameState.state = GameState.PAUSED;
			game.getPaused().enterChatMode();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int keyCode = e.getKeyCode();

		// updated code by Yves: Make arrow keys also functional (omit switch case usage)
		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			player.setJump(false);
		} else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
			player.setLeft(false);
		} else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			player.setDown(false);
		} else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
			player.setRight(false);


		// Yves also added space for easier debugging attack functionality
		} else if (keyCode == KeyEvent.VK_SPACE) {
			player.setAttack(false, game.getGamePanel(), opponent);
		}
	}

	public void windowFocusLost() {
		player.resetDirectionBooleans();
	}

	public Player getPlayer() {
		return player;
	}

	public boolean getPaused() {
		return gamePaused;
	}

	//runnable class to read from server
	private class ReadFromServer implements Runnable{
		private DataInputStream dataIn;

		public ReadFromServer(DataInputStream in){
			dataIn = in;
			System.out.println("RFS Runnable Created");
		}
		public void run(){
			try{
				while(true){
					if(opponent != null){
						opponent.setEnemyX(dataIn.readFloat());
						opponent.setEnemyY(dataIn.readFloat());
						opponent.setPower(dataIn.readInt());
						enemystate = dataIn.readInt();
						if(enemystate == 1){
							GameState.state = GameState.LOSE;
						}else if(enemystate == 2) {
							GameState.state = GameState.WIN;
						}

					}
				}
			}catch(IOException ex){
				System.out.println("IOException from read from server run");
			}
		}

		//wait for start message function -- wait message from server
		public void waitForStartMessage(){
			try{
				String startMsg = dataIn.readUTF();
				System.out.println("Message: " + startMsg);
				Thread readThread = new Thread(rfsRunnable);
				Thread writeThread = new Thread(wtsRunnable);
				readThread.start();
				writeThread.start();
			}catch(IOException ex){
				System.out.println("IOException from wait for start message");

			}
		}
	}

	public void updatePower(int power){
		player.powerValue = power;
	}
	
	public void decreaseHealth() {
		player.HPvalue --;
	}
	
	public int getPlayerHealth() {
		return player.getHealth();
	}

	public int getPlayerPower(){
		return player.powerValue;
	}

	public int getOpponentPower(){
		return opponent.powerValue;
	}

	//runnable class to write to server
	private class WriteToServer implements Runnable{
		private DataOutputStream dataOut;

		public WriteToServer(DataOutputStream out){
			dataOut = out;
			System.out.println("WTS Runnable Created");
		}
		public void run(){
			try{
				while(true){
					if(player != null){
						dataOut.writeFloat(player.getPlayerX());
						dataOut.writeFloat(player.getPlayerY());
						dataOut.writeInt(player.powerValue);
						dataOut.writeInt(state);
						dataOut.flush();
					}
					try{
						Thread.sleep(25);
					}catch(InterruptedException ex){
						System.out.println("InterruptedException from write to server run");
					}
				}
			}catch(IOException ex){
				System.out.println("IOException from write to server");
			}

		}
	}

	//function to connect to the server
		public void connectToServer(){
			try{
				socket = new Socket("192.168.2.110", 1001);
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				playerID = in.readInt();
				System.out.println("Player Id: " + playerID);
				if(playerID == 1){
					System.out.println("Waiting for other player");
					initialize(200, 150, 500, 150);
				}else{
					initialize(500, 150, 200, 150);
				}
				rfsRunnable = new ReadFromServer(in);
				wtsRunnable = new WriteToServer(out);

				//wait for start message for both players' GUI to start
				rfsRunnable.waitForStartMessage();

			}catch(IOException ex){
				System.out.println("IOException from connect to server");
			}
		}


}
