package upnpdevicetest;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.upnp.UPnPAction;
import org.osgi.service.upnp.UPnPDevice;
import org.osgi.service.upnp.UPnPService;
import org.osgi.service.upnp.UPnPStateVariable;

public class PrinterService implements UPnPService {
	
	UPnPPrinterDevice dev;
	Hashtable listenerProps;
	
	public PrinterService(UPnPPrinterDevice dev) {
	    this.dev = dev;
	    listenerProps = new Hashtable(6);
	    listenerProps.put(UPnPDevice.TYPE, dev.props.get(UPnPDevice.TYPE));
	    listenerProps.put(UPnPDevice.UDN, dev.props.get(UPnPDevice.UDN));
	    listenerProps.put(UPnPService.TYPE, getType());
	    listenerProps.put(UPnPService.ID, getId());
	}
	
	public void generateEvent(Dictionary events) throws Exception { 
		dev.generateEvent(listenerProps, events);
	}

	public String getId() {
		return "urn:prosyst-com:serviceId:PrinterDevice"; 
	}

	public String getType() {
	    return "urn:schemas-prosyst-com:service:Printer";
	}

	public String getVersion() {
		return "1";
	}

	public UPnPAction getAction(String name) {
		if (dev.upnpActions[0].getName().equals(name)) {
			return dev.upnpActions[0];
		} else {
			return null;
		}
	}

	public UPnPAction[] getActions() {
		return dev.upnpActions;
	}

	public UPnPStateVariable[] getStateVariables() {
		return dev.upnpStateVars;
	}

	public UPnPStateVariable getStateVariable(String name) {
		for (int i = 0; i < dev.upnpStateVars.length; i++) {
			if (dev.upnpStateVars[i].getName().equals(name)) {
				return dev.upnpStateVars[i];
			}
		}
		return null;
	}

}
