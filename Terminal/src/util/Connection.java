package util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;

public class Connection {
	
	static Socket socket;
	static ObjectInputStream in;
	static PrintWriter out;
	static Vector<Hashtable<String, Object>> devices;
	
	private static String IP;
	private static int port;
	
	@SuppressWarnings("unchecked")
	public static Vector<Hashtable<String, Object>>
			getDevicesInfo(String IP, int port) throws Exception {
		
		Connection.IP = IP;
		Connection.port = port;
		socket = new Socket(IP, port);
		out = new PrintWriter(socket.getOutputStream(), true);
		out.println("query");
		in = new ObjectInputStream(socket.getInputStream());
		devices = (Vector<Hashtable<String, Object>>) in.readObject();
		in.close();
		out.close();
		socket.close();
		return devices;
	}
	
	@SuppressWarnings("rawtypes")
	public static Dictionary invoke(Hashtable<String, Object> props) throws Exception {
		socket = new Socket(IP, port);
		out = new PrintWriter(socket.getOutputStream(), true);
		out.println("invoke");
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(props);
		oos.flush();
		in = new ObjectInputStream(socket.getInputStream());
		Dictionary dic = (Dictionary) in.readObject();
		out.close();
		oos.close();
		in.close();
		socket.close();
		return dic;
	}
	
}
