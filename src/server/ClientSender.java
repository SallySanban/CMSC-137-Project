package server;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ClientSender {
	
	public static void sendCharToClient(char ch, OutputStream outputStream) {
		if (outputStream != null) {
			PrintWriter pw = new PrintWriter(outputStream);
			pw.println(ch);
			pw.flush();
		}
	}

	public static void sendStringToClient(String string, OutputStream outputStream) {
		if (outputStream != null) {
			PrintWriter pw = new PrintWriter(outputStream);
			pw.println(string);
			pw.flush();
		}
	}	
}