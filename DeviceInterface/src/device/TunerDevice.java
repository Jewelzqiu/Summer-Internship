package device;

import org.osgi.service.device.Device;

public interface TunerDevice extends Device {
	
	public void setState(int state);
	
	public int getState();

}
