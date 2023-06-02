package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;

import chats.Client;
import chats.Server;
import main.Game;

public class Paused extends State implements Statemethods {
	private boolean serverPressed = false;

	public Paused(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.drawString("THIS GAME IS PAUSED!", (Game.GAME_WIDTH/2)-70, Game.GAME_HEIGHT/2);
//		g.drawString("Press S to start server", (Game.GAME_WIDTH/2)-65, (Game.GAME_HEIGHT/2)+20);
		g.drawString("Entering chat mode...", (Game.GAME_WIDTH/2)-65, (Game.GAME_HEIGHT/2)+40);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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
	
	public void enterChatMode() {
		try {
			Client.mainClient();
		} catch(IOException e1) {
			e1.printStackTrace();				
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			game.getPlaying().gamePaused = false;
			GameState.state = GameState.PLAYING;
//		} else if(e.getKeyCode() == KeyEvent.VK_S) {
//			serverPressed = true;
//			try {
//				Server.mainServer();
//			} catch(IOException e1) {
//				e1.printStackTrace();
//			}
//		} else if(e.getKeyCode() == KeyEvent.VK_C) {
//			try {
//				Client.mainClient();
//			} catch(IOException e1) {
//				e1.printStackTrace();				
//			}
		} 

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
