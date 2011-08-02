package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Vector;

public class Server {
	
	private ServerSocket ss;
	private Socket s;
	private ObjectOutputStream out;
	private BufferedReader in;

	Vector<Hashtable<String, Integer>> v = new Vector<Hashtable<String, Integer>>();
	
	public Server() throws IOException {
		Hashtable<String, Integer> h1 = new Hashtable<String, Integer>();
		h1.put("test", 1);
		h1.put("sb", 2);
		v.add(h1);
		Hashtable<String, Integer> h2 = new Hashtable<String, Integer>();
		h2.put("nima", 3);
		h2.put("sss", 0);
		v.add(h2);
		ss = new ServerSocket(12345);
	}
	
	public void waitClient() {
		try {
			while (true) {
				s = ss.accept();
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				System.out.println("[Server]: Connected");
				String line = in.readLine();
				if (line != null && line.equals("query")) {			
					out = new ObjectOutputStream(s.getOutputStream());
					out.writeObject(v);
					out.flush();
				}
				Thread.sleep(5000);
				in.close();
				if (out != null) {
					out.close();
				}
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
