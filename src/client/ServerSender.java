package client;

import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ServerSender {

	public static void sendCharToServer(char string, OutputStream outputStream) {
		if (outputStream != null) {
			(new PrintWriter(outputStream)).println(string);
		}
	}

}