package inputs;

import java.awt.event.KeyEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.KeyListener;
import gamestates.GameState;
import main.GamePanel;
import java.net.*;

public class CharacterListener implements KeyListener {
	
	// variables
	private GamePanel gamePanel;
	String serverAddress;
	int port = 8080;
	

	// remove the unnecessarily long "System.out.println()" just to print lol
	private static void print(String string) {
	    System.out.println(string);
	}
	
	// constructor
	public CharacterListener(GamePanel gamePanel) {
		this.gamePanel = gamePanel;	
		
		// try connecting now
		try {
			serverAddress = InetAddress.getLocalHost().getHostAddress();
			Socket socket = new Socket(serverAddress, port);
			DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
			System.out.println("Connected to the server.");
			socket.close();
			print("Connection closed.");
		} catch (IOException e) {
			print("No server yet!!\n");        	
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {}
	
	@Override
	public void keyPressed(KeyEvent e) {
		print("I got this value on keyPressed: " + e.getKeyChar());
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		print("I got this value on keyReleased: " + e.getKeyChar());
	}	
}