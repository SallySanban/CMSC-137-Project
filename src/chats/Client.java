package chats;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;
import java.awt.Color;

public class Client extends JFrame implements ActionListener {	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String username;
	private JTextField text;
	private JPanel backgroundPanel;
	static Box vertical = Box.createVerticalBox();
	public List<String> texts = new ArrayList<>();
	private boolean sent = false;
	
	public Client(Socket socket, String username) {
		setLayout(null);
		setSize(450,700);
		setVisible(true);
		getContentPane().setBackground(Color.WHITE);
		setLocation(200, 50);
		
		backgroundPanel = new JPanel();
		backgroundPanel.setBackground(Color.WHITE);
		backgroundPanel.setBounds(5, 75, 450, 500);
		add(backgroundPanel);
		
		text = new JTextField();
		text.setBounds(10, 600, 280, 40);
		add(text);
		
		JButton send = new JButton("Send");
		send.setBounds(300, 600, 123, 40);
		add(send);
		send.addActionListener(this);
		
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.username = username;
		} catch(IOException e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
	}
	
	public void sendMessage() {
		try {
			bufferedWriter.write(username);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			
			Scanner scanner = new Scanner(System.in);
			String messageToSend = "";
			
			while(socket.isConnected()) {
				if(!texts.contains(text.getText()) && sent == true) {
					messageToSend = text.getText();
					texts.add(messageToSend);
					
					bufferedWriter.write(username + ": " + messageToSend);
					bufferedWriter.newLine();
					bufferedWriter.flush();
					
					sent = false;
					text.setText("");
				}
			}
		} catch(IOException e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
	}
	
	public void listenForMessage() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				String msgFromGroupChat;
				
				while(socket.isConnected()) {
					try {
						msgFromGroupChat = bufferedReader.readLine();
						
						//System.out.println(msgFromGroupChat);
						showMessage(msgFromGroupChat);
						
						
					} catch(IOException e) {
						closeEverything(socket, bufferedReader, bufferedWriter);
					}
				}
				
			}
			
		}).start();
	}
	
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		try {
			if(bufferedReader != null) {
				bufferedReader.close();
			}
			
			if(bufferedWriter != null) {
				bufferedWriter.close();
			}
			
			if(socket != null) {
				socket.close();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void mainClient() throws IOException {		
		JFrame frame = new JFrame();
		
		String username = JOptionPane.showInputDialog(frame,"Enter your username"); 
		
		new Thread(new Runnable() {
            public void run() {
            	try {
					Socket socket = new Socket("192.168.2.110", 1002);
					Client client = new Client(socket, username);
					client.listenForMessage();
					client.sendMessage();
            		}
	    		catch(IOException e) {
	    			e.printStackTrace();
            }}}).start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		sent = true;
		
		String out = text.getText();
		
		JLabel output = new JLabel(out);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.add(output);
		
		backgroundPanel.setLayout(new BorderLayout());
		
		JPanel right = new JPanel(new BorderLayout());
		right.setBackground(Color.WHITE);
		right.add(panel, BorderLayout.LINE_START);
		vertical.add(right);
		vertical.add(Box.createVerticalStrut(15));
		
		backgroundPanel.add(vertical, BorderLayout.PAGE_START);
		
		repaint();
		invalidate();
		validate();
		
	}
	
	public void showMessage(String message) {
		String out = message;
		
		JLabel output = new JLabel(out);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.add(output);
		
		backgroundPanel.setLayout(new BorderLayout());
		
		JPanel right = new JPanel(new BorderLayout());
		right.setBackground(Color.WHITE);
		right.add(panel, BorderLayout.LINE_START);
		vertical.add(right);
		vertical.add(Box.createVerticalStrut(15));
		
		backgroundPanel.add(vertical, BorderLayout.PAGE_START);
		
		repaint();
		invalidate();
		validate();
	}
}
