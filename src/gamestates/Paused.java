package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import chats.Client;
import chats.Server;
import main.Game;

public class Paused extends State implements Statemethods {

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

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			game.getPlaying().gamePaused = false;
			GameState.state = GameState.PLAYING;
		} else if(e.getKeyCode() == KeyEvent.VK_S) {
			try {
				Server.mainServer();
			} catch(IOException e1) {
				e1.printStackTrace();
			}
		} else if(e.getKeyCode() == KeyEvent.VK_C) {
			try {
				Client.mainClient();
			} catch(IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
