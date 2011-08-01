package drivertest;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.device.Constants;
import org.osgi.service.device.Device;
import org.osgi.service.device.Driver;

import device.TunerDevice;

public class TunerDriver implements BundleActivator, Driver {

	private static BundleContext context;
	static final String TUNER_DEVICE_CATEGORY = "tuner";
	static final String TUNER_DRIVER_ID = "my.driver.tuner";
	private ServiceRegistration sReg = null;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		TunerDriver.context = bundleContext;
		Hashtable props = new Hashtable();
	    props.put(Constants.DRIVER_ID, TUNER_DRIVER_ID);
	    sReg = context.registerService(Driver.class.getName(), this, props);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		TunerDriver.context = null;
		if (sReg != null) {
			sReg.unregister();
		}
	}

	public int match(ServiceReference reference) throws Exception {
		if (reference != null) {
			String deviceCategory = (String) reference.getProperty(
					Constants.DEVICE_CATEGORY);
		    if (deviceCategory.equals(TUNER_DEVICE_CATEGORY)) {
		        return 1;
		    }
		}
		return Device.MATCH_NONE;
	}

	public String attach(ServiceReference reference) throws Exception {
		if (reference != null) {
			TunerDevice device = (TunerDevice) context.getService(reference);
		    dump("Initial State = " + device.getState());
		    device.setState(5);
		}
		return null;
	}
	
	private void dump(String msg) {
	    System.out.println("[MY TUNER DRIVER] " + msg);
	}

}
