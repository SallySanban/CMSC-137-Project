package chats;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket serverSocket;
	private Thread chatServer;
	
	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public void startServer() throws IOException {
		new Thread(new Runnable() {
            public void run() {
	            try {
	    			while(!serverSocket.isClosed()) {
	    				Socket socket = serverSocket.accept();
	    				System.out.println("A new client has connected!");
	    				ClientHandler clientHandler = new ClientHandler(socket);
	    				
	    				Thread thread = new Thread(clientHandler);
	    				thread.start();
	    			}
	    		} catch(IOException e) {
	    			e.printStackTrace();
	    		}}}).start();
	}
	
	public void closeServerSocket() {
		try {
			if(serverSocket != null) {
				serverSocket.close();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void mainServer() throws IOException {
		ServerSocket serverSocket = new ServerSocket(12312);
		Server server = new Server(serverSocket);
		server.startServer();
	}
}
