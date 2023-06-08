package main;

import java.io.IOException;
import java.net.ServerSocket;
import chats.Server;

public class main {

	
	public static void main(String[] args) {
		
		int port = 8080;
		ServerSocket serverSocket;
		
		// connect the server for chat
		try {
			serverSocket = new ServerSocket(port);
			Server server = new Server(serverSocket);
			server.startServer();
			System.out.println("A server for chat on port " + port + " has just been created.");
		} catch (IOException e) {
			System.out.println("A server is already running so there's no need to make another one.");
		}
		
		new Game();

	}
	

}