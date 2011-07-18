package testserver;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import testservice.HelloService;
import testserviceimpl.HelloServiceImpl;
import ch.ethz.iks.r_osgi.RemoteOSGiService;
import ch.ethz.iks.slp.Advertiser;
import ch.ethz.iks.slp.ServiceURL;

public class Activator implements BundleActivator {

	ServiceRegistration registration;
	Advertiser advertiser;
	ServiceReference advRef;
	Hashtable properties = new Hashtable();
	String IPAddr = new String();
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		
		advRef = context.getServiceReference(Advertiser.class.getName());
				
		properties.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
		
		registration = context.registerService(HelloService.class.getName(),
				new HelloServiceImpl(), properties);
		
		getIPAddresses();
		if (IPAddr.equals("")) {
			System.out.println("Invalid IP address");
			return;
		}
		
		if (advRef == null) {
			System.out.println("advRef is null!");
		} else  {
			System.out.println("Got reference for Advertiser");
            advertiser = (Advertiser) context.getService(advRef);
            advertiser.register(
            		new ServiceURL("service:osgi:r-osgi://" + IPAddr + ":9278", 1000),
            		null);
            System.out.println("registered: " + "service:osgi:r-osgi://" + IPAddr + ":9278");
		}
		
		sendFile("/home/ftp/images/20110713161011.jpg");
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
		advertiser.deregister(new ServiceURL("service:osgi:r-osgi://" + IPAddr + ":9278", 1000));
		System.out.println("unregistered");
	}

	public void getIPAddresses() throws SocketException {
		Enumeration e = NetworkInterface.getNetworkInterfaces();
		while (e.hasMoreElements()) {
			NetworkInterface ni = (NetworkInterface) e.nextElement();
			Enumeration ia = ni.getInetAddresses();
			while (ia.hasMoreElements()) {
				InetAddress addr = (InetAddress) ia.nextElement();
				if (addr.isLoopbackAddress()) {
					continue;
				}
				if (addr instanceof Inet4Address) {
					IPAddr = addr.getHostAddress();
					//System.out.println("ipv4: " + IPAddr);
				}
				if (addr instanceof Inet6Address) {
					//System.out.println("ipv6: " + addr.getHostAddress());
				}
			}
		}
	}
	
	public void sendFile(String filename) {
		File file = new File(filename);
		int len = (int) file.length();
		try {
			FileInputStream fis = new FileInputStream(file);
			
			ServerSocket ss = new ServerSocket(9527);
			Socket client = ss.accept();
			
			OutputStream netout = client.getOutputStream();
			OutputStream doc = new DataOutputStream(new BufferedOutputStream(netout));
			System.out.println("Start sending a file!");
			byte[] buffer = new byte[8192];
			int num = fis.read(buffer);
			while (num != -1) {
				System.out.println("sent " + num + " bytes...");
				doc.write(buffer, 0, num);
				doc.flush();
				//len -= num;
				num = fis.read(buffer);
			}
			System.out.println("File sent");
			fis.close();
			doc.close();
			client.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
