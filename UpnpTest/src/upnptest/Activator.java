package upnptest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.upnp.UPnPAction;
import org.osgi.service.upnp.UPnPDevice;
import org.osgi.service.upnp.UPnPEventListener;
import org.osgi.service.upnp.UPnPService;

public class Activator implements BundleActivator, UPnPEventListener, ServiceListener {

	private static BundleContext context;
	private ServiceRegistration srr;
	private static String filter = "(|(" + UPnPDevice.UDN + "=*)(" 
            + UPnPService.ID + "=*))";
	public Vector devices = new Vector();
	
	private ServerSocket ss;
	private Socket socket;
	private BufferedReader in;
	ObjectOutputStream out;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		//update();
		ServiceReference[] dvs = bundleContext.getServiceReferences(
				UPnPDevice.class.getName(),
				"(ObjectClass=" + UPnPDevice.class.getName() + ")");
		
		if (dvs == null) {
			System.out.println("No UPnP device found");
		} else {
			for (int i = 0; i < dvs.length; i++) {
				Hashtable info = new Hashtable();
				String[] keys = dvs[i].getPropertyKeys();
				//System.out.println(Arrays.toString(keys));
				for (int j = 0; j < keys.length; j++) {
					info.put(keys[j], dvs[i].getProperty(keys[j]));
				}
				System.out.println("Device: " + dvs[i].getProperty(UPnPDevice.UDN));
				UPnPDevice dev = (UPnPDevice) context.getService(dvs[i]);
				UPnPService[] services = dev.getServices();
				Hashtable svs = new Hashtable(services.length);
				for (int j = 0; j < services.length; j++) {
					UPnPAction[] actions = services[j].getActions();
					ArrayList acns = new ArrayList(actions.length);
					acns.add(services[j].getType());
					for (int k = 0; k < actions.length; k++) {
						ArrayList parameters = new ArrayList(3);
						parameters.add(actions[k].getName());
						//parameters.add(actions[k].getReturnArgumentName());
						parameters.add(actions[k].getInputArgumentNames());
						parameters.add(actions[k].getOutputArgumentNames());
						acns.add(parameters);
					}
					svs.put(services[j].getId(), acns);
				}
				info.put("Device Services", svs);
				devices.add(info);
//				UPnPAction action = services[0].getActions()[0];
//				Hashtable prop = new Hashtable();
//				prop.put(action.getInputArgumentNames()[0], "test");
//				action.invoke(prop);
			}
		}	
		
		bundleContext.addServiceListener(this, 
				"(ObjectClass=" + UPnPDevice.class.getName() + ")");
		
		Hashtable tmp = new Hashtable();
		Filter fi = null;
		try {
			fi = bundleContext.createFilter(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tmp.put(UPNP_FILTER, fi);
		srr = bundleContext.registerService(
				UPnPEventListener.class.getName(), this, null);	
		
		ss = new ServerSocket(22222);
		thread.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		if (srr != null) {
			srr.unregister();
		}
		thread.interrupt();
		ss.close();
	}

	public void notifyUPnPEvent(String deviceId, String serviceId, Dictionary events) {
		Enumeration en = events.keys();
		System.out.println();
		System.out.println("Event!");
		System.out.println("UDN: " + deviceId);
		System.out.println("ServiceID: " + serviceId);
		
		while (en.hasMoreElements()) {
			String ssvName = (String) en.nextElement();
			Object value = events.get(ssvName);
			System.out.println("Variable: " + ssvName + " = " + value);
		}
	}

	public void serviceChanged(ServiceEvent event) {
		System.out.println(event);
		
		if (event.getType() == ServiceEvent.UNREGISTERING) {
			System.out.println(
					"UNREGISTERING: " + 
					event.getServiceReference().getProperty(UPnPDevice.UDN));
			try {
				update();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (event.getType() == ServiceEvent.REGISTERED) {
			System.out.println(
					"REGISTERED: " + 
					event.getServiceReference().getProperty(UPnPDevice.UDN));
			try {
				update();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (event.getType() == ServiceEvent.MODIFIED) {
			System.out.println(
					"MODIFIED: " + 
					event.getServiceReference().getProperty(UPnPDevice.UDN));
			try {
				update();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void update() throws Exception {
		ServiceReference[] dvs = context.getServiceReferences(
				UPnPDevice.class.getName(),
				"(ObjectClass=" + UPnPDevice.class.getName() + ")");
		devices.clear();
		
		if (dvs == null) {
			System.out.println("No UPnP device found");
		} else {
			for (int i = 0; i < dvs.length; i++) {
				Hashtable info = new Hashtable();
				String[] keys = dvs[i].getPropertyKeys();
				for (int j = 0; j < keys.length; j++) {
					info.put(keys[j], dvs[i].getProperty(keys[j]));
				}
				UPnPDevice dev = (UPnPDevice) context.getService(dvs[i]);
				UPnPService[] services = dev.getServices();
				Hashtable svs = new Hashtable(services.length);
				for (int j = 0; j < services.length; j++) {
					UPnPAction[] actions = services[j].getActions();
					ArrayList acns = new ArrayList(actions.length);
					for (int k = 0; k < actions.length; k++) {
						ArrayList parameters = new ArrayList(3);
						parameters.add(actions[k].getName());
						//parameters.add(actions[k].getReturnArgumentName());
						parameters.add(actions[k].getInputArgumentNames());
						parameters.add(actions[k].getOutputArgumentNames());
						acns.add(parameters);
					}
					svs.put(services[j].getId(), acns);
				}
				info.put("Device Services", svs);
				devices.add(info);
			}
		}	
	}
	
	Thread thread = new Thread() {
		
		public void run() {
			try {
				while (true) {
					socket = ss.accept();
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String s = in.readLine();
					if (s != null) {
						if (s.equals("query")) {
							out = new ObjectOutputStream(socket.getOutputStream());
							if (devices == null) {
								System.out.println("devices == null");
							} else {
								out.writeObject(devices);
								out.flush();
								out.close();
							}
						} else if (s.equals("invoke")) {
							System.out.println("[Server]: invoke");
							ObjectInputStream ois = 
									new ObjectInputStream(socket.getInputStream());
							System.out.println("[Server]: start reading");
							Hashtable props = (Hashtable) ois.readObject();
							System.out.println("[Server]: read complete");
							out = new ObjectOutputStream(socket.getOutputStream());
							out.writeObject(invoke(props));
							out.flush();
							System.out.println("[Server]: write complete");
							out.close();
							ois.close();
						}
					}
					in.close();
					socket.close();
				}
			} catch(SocketException e) {
				e.printStackTrace();
				this.interrupt();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	};
	
	private Dictionary invoke(Hashtable props) throws Exception {
		Dictionary dic = null;
		String device_id = (String) props.get("DEVICE_ID");
		String service_id = (String) props.get("SERVICE_ID");
		String action_name = (String) props.get("ACTION_NAME");
		if (device_id == null || service_id == null || action_name == null) {
			return null;
		}
		Hashtable inputs = (Hashtable) props.get("ARGUMENTS");
		
		ServiceReference[] dvs = context.getServiceReferences(
				UPnPDevice.class.getName(),
				"(ObjectClass=" + UPnPDevice.class.getName() + ")");
		if (dvs != null) {
			for (int i = 0; i < dvs.length; i++) {
				if (dvs[i].getProperty(UPnPDevice.UDN).toString().equals(device_id)) {
					UPnPDevice dev = (UPnPDevice) context.getService(dvs[i]);
					UPnPService service = dev.getService(service_id);
					UPnPAction action = service.getAction(action_name);
					dic = action.invoke(inputs);
				}
			}
		}
		return dic;
	}

}
