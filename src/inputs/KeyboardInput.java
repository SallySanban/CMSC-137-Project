package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import gamestates.GameState;
import main.GamePanel;

public class KeyboardInput implements KeyListener{
	private GamePanel gamePanel;

	// variables
	String serverAddress;
	int port = 8080;
	Socket socket;
	DataOutputStream outputStream;

	// remove the unnecessarily long "System.out.println()" just to print lol
	private static void print(String string) {
	    System.out.println(string);
	}
	
	public KeyboardInput(GamePanel gamePanel) {
		this.gamePanel = gamePanel;

		// try connecting to server now
		try {
			serverAddress = InetAddress.getLocalHost().getHostAddress();
			socket = new Socket(serverAddress, port);
			outputStream = new DataOutputStream(socket.getOutputStream());
			System.out.println("Connected to the server.");
			socket.close();
			print("Connection closed.");
		} catch (IOException e) {
			print("No server yet!!\n");        	
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(GameState.state){
		case MENU:
			gamePanel.getGame().getMenu().setServerOutputStream(outputStream);
			gamePanel.getGame().getMenu().keyPressed(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().setServerOutputStream(outputStream);
			gamePanel.getGame().getPlaying().keyPressed(e);
			break;
		case PAUSED:
			gamePanel.getGame().setOutputStream(outputStream);
			gamePanel.getGame().getPaused().setServerOutputStream(outputStream);
			gamePanel.getGame().getPaused().keyPressed(e);
			break;
		default:
			break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(GameState.state){
		case MENU:
			gamePanel.getGame().getMenu().keyReleased(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyReleased(e);
			break;
		case PAUSED:
			gamePanel.getGame().getPaused().keyReleased(e);
			break;
		default:
			break;

		}
	}
	
}