package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
	private int numPlayers;
	private int maxPlayers;
	private ServerSocket ss;
	private Socket player1Socket; //to store socket for player 1
	private Socket player2Socket; //to store socket for player 2
	private ReadFromClient p1ReadRunnable;
	private ReadFromClient p2ReadRunnable;
	private WriteToClient p1WriteRunnable;
	private WriteToClient p2WriteRunnable;
	private float p1x, p1y, p2x, p2y;
	private int p1power, p2power, p1state, p2state;




	//constructor for game server
	public GameServer(){
		numPlayers = 0;
		maxPlayers = 2;
		p1x = 200;
		p2x = 300;
		p1y = 150;
		p2y = 150;

		try{
			ss = new ServerSocket(45371);
			System.out.println("Successfully created server");
		}catch(IOException ex){
			System.out.println("IOException from server constructor");
		}
	}

	//function to accept connections in the socket
	public void acceptConnections(){
		try{
			System.out.println("Waiting for connections");
			//will accept connections until maxplayers is reached
			while(numPlayers < maxPlayers){
				Socket s = ss.accept();
				DataInputStream in = new DataInputStream(s.getInputStream());
				DataOutputStream out = new DataOutputStream(s.getOutputStream());

				numPlayers++;
				out.writeInt(numPlayers);
				System.out.println(numPlayers + " has connected");
				ReadFromClient rfc = new ReadFromClient(numPlayers, in);
				WriteToClient wtc = new WriteToClient(numPlayers, out);

				//to properly assign the fields to player 1 or player 2
				if(numPlayers == 1){
					player1Socket = s;
					p1ReadRunnable = rfc;
					p1WriteRunnable = wtc;
				}else{
					player2Socket = s;
					p2ReadRunnable = rfc;
					p2WriteRunnable = wtc;
					//will send start message once player2 has connected
					p1WriteRunnable.sendStartMessage();
					p2WriteRunnable.sendStartMessage();
					Thread readThread1 = new Thread(p1ReadRunnable);
					Thread readThread2 = new Thread(p2ReadRunnable);
					readThread1.start();
					readThread2.start();
					Thread writeThread1 = new Thread(p1WriteRunnable);
					Thread writeThread2 = new Thread(p2WriteRunnable);
					writeThread1.start();
					writeThread2.start();
				}
			}
			System.out.println("No longer accepting connections");
		}catch(IOException ex){
			System.out.println("IOException in game server constructor");
		}
	}

	//reading information from clients
	private class ReadFromClient implements Runnable{
		private int playerID;
		private DataInputStream dataIn;

		public ReadFromClient(int pid, DataInputStream in){
			playerID = pid;
			dataIn = in;
			System.out.println("Read from client " + playerID + " runnable created");

		}

		//since this is a runnable
		public void run(){
			try{
				while(true){
					//ifplayerID == 2 , assign the values to be read to p1x and p2x
					if(playerID == 1){
						p1x = dataIn.readFloat();
						p1y = dataIn.readFloat();
						p1power = dataIn.readInt();
						p1state = dataIn.readInt();
//						System.out.println("Player 1 x : " + p1power);
//						System.out.println("Player 1 y : " + p1y);
					}else{
						p2x = dataIn.readFloat();
						p2y = dataIn.readFloat();
						p2power = dataIn.readInt();
						p2state = dataIn.readInt();
//						System.out.println("Player 2 x : " + p2power);
//						System.out.println("Player 2 y : " + p2y);
					}
					//close the socket if one of them already won
					if(p1state == 1 || p2state == 2){
						ss.close();
					}
				}
			}catch(IOException ex){
				System.out.println("IOException from read from client run");
			}
		}
	}

	//writing information from clients
	private class WriteToClient implements Runnable{
		private int playerID;
		private DataOutputStream dataOut;

		public WriteToClient(int pid, DataOutputStream out){
			playerID = pid;
			dataOut = out;
			System.out.println("Write to  client " + playerID + " runnable created");

		}

		//since this is a runnable
		public void run(){
			try{
				while(true){
					//if player 1, send the coordinates of player 2
					//else, send coordinates of player 1
					if(playerID == 1){
						dataOut.writeFloat(p2x);
						dataOut.writeFloat(p2y);
						dataOut.writeInt(p2power);
						dataOut.writeInt(p2state);
						dataOut.flush();
					}else{
						dataOut.writeFloat(p1x);
						dataOut.writeFloat(p1y);
						dataOut.writeInt(p1power);
						dataOut.writeInt(p1state);
						dataOut.flush();
					}
					try{
						Thread.sleep(25);
					}catch(InterruptedException ex){
						System.out.println("InterruptedException from write to client run");
					}
				}
			}catch(IOException ex){
				System.out.println("IOException in write to client run");
			}
		}

		public void sendStartMessage(){
			try{
				dataOut.writeUTF("Players are ready");
			}catch(IOException ex){
				System.out.println("IOException from sending start message");
			}
		}
	}

	public static void main(String[] args) {

		GameServer gs = new GameServer();
		gs.acceptConnections();
	}
}
