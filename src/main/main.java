package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import chats.Server;


public class main {

	public static void main(String[] args) {
		//create a new instance of game
		Game g = new Game();
		//call connectToServer function to connect a server
//		g.connectToServer();
		
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(12312);
			Server server = new Server(serverSocket);
			server.startServer();
			System.out.println("A server has just been created.");
		} catch (IOException e) {
			System.out.println("A server is already running so there's no need to make another one.");
		}
	}

}