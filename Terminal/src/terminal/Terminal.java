package terminal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Vector;

import server.Server;

public class Terminal {
	
	Socket socket;
	ObjectInputStream in;
	PrintWriter out;
	Vector<Hashtable<String, Object>> devices;
	
	public Terminal(String IP, int port) throws Exception {
		socket = new Socket(IP, port);
		out = new PrintWriter(socket.getOutputStream(), true);
		out.println("query");
		System.out.println("wrote");
		in = new ObjectInputStream(socket.getInputStream());
		System.out.println("[Client]: connected");
		devices = (Vector<Hashtable<String, Object>>) in.readObject();
		System.out.println("read");
		in.close();
		socket.close();
		for (Hashtable<String, Object> h : devices) {
			System.out.println(h.keySet());
		}
	}
	
	public void printDevices() {
		if (devices.isEmpty()) {
			return;
		}
	}
	
	public static void main(String[] args) throws Exception {
//		Thread thread = new Thread() {
//			public void run() {
//				try {
//					Server server = new Server();
//					server.waitClient();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		};
//		thread.start();

		Terminal terminal = new Terminal("192.168.0.219", 22222);
		terminal.printDevices();
	}

}
