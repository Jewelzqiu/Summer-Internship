package upnptest;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.upnp.UPnPDevice;
import org.osgi.service.upnp.UPnPEventListener;
import org.osgi.service.upnp.UPnPService;

public class Activator implements BundleActivator, UPnPEventListener, ServiceListener {

	private static BundleContext context;
	private ServiceRegistration srr;
	private static String filter = "(|(" + UPnPDevice.UDN + "=*)(" 
            + UPnPService.ID + "=*))";

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		ServiceReference[] dvs = bundleContext.getServiceReferences(
				UPnPDevice.class.getName(),
				"(ObjectClass = " + UPnPDevice.class.getName() + ")");
		
		if (dvs == null) {
			System.out.println("No UPnP device found");
		}
		
		if (dvs != null) {
			for (int i = 0; i < dvs.length; i++) {
				System.out.println("Device: " + dvs[i].getProperty(UPnPDevice.UDN));
			}
		}
		
		bundleContext.addServiceListener(this, 
				"(ObjectClass = " + UPnPDevice.class.getName() + ")");
		
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
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		srr.unregister();
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
		}
		else if (event.getType() == ServiceEvent.REGISTERED) {
			System.out.println(
					"REGISTERED: " + 
					event.getServiceReference().getProperty(UPnPDevice.UDN));
		}
		else if (event.getType() == ServiceEvent.MODIFIED) {
			System.out.println(
					"MODIFIED: " + 
					event.getServiceReference().getProperty(UPnPDevice.UDN));
		}
		
	}

}
