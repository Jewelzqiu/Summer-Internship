package testclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Vector;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import testservice.HelloService;
import ch.ethz.iks.r_osgi.RemoteOSGiService;
import ch.ethz.iks.r_osgi.RemoteServiceReference;
import ch.ethz.iks.r_osgi.URI;
import ch.ethz.iks.slp.Locator;
import ch.ethz.iks.slp.ServiceLocationEnumeration;
import ch.ethz.iks.slp.ServiceType;
import ch.ethz.iks.slp.ServiceURL;

public class Activator implements BundleActivator {

	Vector ServiceLocation = new Vector();
	RemoteOSGiService remote;
	RemoteServiceReference[] refs;
	ServiceReference sref;
	HelloService helloservice;
	ServiceReference locRef;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		
		locRef = context.getServiceReference(Locator.class.getName());
		
		if (locRef == null) {
			System.out.println("locRef is null!");
		} else {
            System.out.println("Got reference for Locator");
            Locator locator = (Locator) context.getService(locRef);

            ServiceLocationEnumeration slenum = locator.findServices(
            		new ServiceType("service:osgi"), null, null);
            System.out.println("RESULT:");
            while (slenum.hasMoreElements()) {
            	ServiceURL service = (ServiceURL) slenum.nextElement();
            	String url = service.toString();
            	String address = url.substring(13);
                System.out.println("address: " + address);
                ServiceLocation.add(address);
            }
        }
		
		sref = context.getServiceReference(RemoteOSGiService.class.getName());
		
		if (sref == null) {
			throw new BundleException("OSGi remote service not found");
		}
		
		remote = (RemoteOSGiService) context.getService(sref);
		
		for (int i = 0; i < ServiceLocation.size(); i++) {
			refs = remote.getRemoteServiceReferences(
					new URI(ServiceLocation.elementAt(i).toString()),
					HelloService.class.getName(), null);
			
			if (refs == null) {
				System.out.println("Service not found at " 
						+ ServiceLocation.elementAt(i).toString());
				continue;
			}
			
			helloservice = (HelloService) remote.getRemoteService(refs[0]);
			System.out.println(helloservice.hello());
		}
		
		receiveFile("newFile.jpg");
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Client exit");
	}

	public void receiveFile(String filename) throws IOException {
		Socket socket = new Socket("192.168.0.219", 9527);
		InputStream is = new DataInputStream(
				new BufferedInputStream(socket.getInputStream()));
		DataOutputStream dos = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream(filename)));
		
		byte[] buffer = new byte[1024];
		int len = is.read(buffer);
		while (len != -1) {
			dos.write(buffer, 0, len);
			len = is.read(buffer);
		}
		System.out.println("File received");
		is.close();
		dos.close();
		socket.close();
	}
}
