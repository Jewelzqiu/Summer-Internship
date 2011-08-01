package device.impl;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.device.Device;

import device.TunerDevice;

public class TunerDeviceImpl implements TunerDevice, BundleActivator {
	
	ServiceRegistration sReg = null;
	private int state = -1;
	
	public TunerDeviceImpl() {
		state = 0;
	}

	public void noDriverFound() {
		state = -1;
	}

	public void start(BundleContext context) throws Exception {
		Hashtable deviceProps = new Hashtable();
	    deviceProps.put(org.osgi.service.device.Constants.DEVICE_CATEGORY, "tuner");
	    deviceProps.put(org.osgi.service.device.Constants.DEVICE_SERIAL, "AB12");
	    deviceProps.put(org.osgi.framework.Constants.SERVICE_PID, "my.device.tuner");
	    sReg = context.registerService(
	    		new String[] {
	    				Device.class.getName(), 
	                    TunerDevice.class.getName()
	            }, 
	            this, 
	            deviceProps);
	}

	public void stop(BundleContext context) throws Exception {
		if (sReg != null) {
			sReg.unregister();
		}
	}

	public void setState(int state) {
		dump("State is set to " + state);
	    this.state = state;
	}

	public int getState() {
		dump("State = " + state);
	    return state;
	}
	
	private void dump(String msg) {
	    System.out.println("[TUNER DEVICE] " + msg);
	}

}
