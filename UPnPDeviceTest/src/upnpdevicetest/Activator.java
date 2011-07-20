package upnpdevicetest;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.upnp.UPnPDevice;

public class Activator implements BundleActivator {

	static BundleContext context;
	private ServiceRegistration sr;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		Hashtable properties = new Hashtable();
		properties.put(UPnPDevice.UPNP_EXPORT, Boolean.TRUE);
		sr = context.registerService(
				UPnPDevice.class.getName(),
				new UPnPPrinterDevice(),
				properties);
		System.out.println("UPnP PrintDevice registered");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		sr.unregister();
	}

}
