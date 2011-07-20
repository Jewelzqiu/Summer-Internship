package upnpdevicetest;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.service.upnp.UPnPAction;
import org.osgi.service.upnp.UPnPDevice;
import org.osgi.service.upnp.UPnPEventListener;
import org.osgi.service.upnp.UPnPIcon;
import org.osgi.service.upnp.UPnPService;
import org.osgi.service.upnp.UPnPStateVariable;


public class UPnPPrinterDevice implements UPnPDevice {
	
	Hashtable props;
	  
	UPnPStateVariable sv_Printing;
	UPnPStateVariable sv_PrintMessage;
	PrintAction ac_Print;
	PrinterService srv_Printer;

	UPnPIcon[] icons;
	UPnPStateVariable upnpStateVars[];
	UPnPAction upnpActions[];
	UPnPService upnpServices[];
	UPnPEventListener listeners[];
	
	public UPnPPrinterDevice() {
		// init properties
		props = new Hashtable(14);
		props.put(UPnPDevice.DEVICE_CATEGORY, 
				new String[]{UPnPDevice.DEVICE_CATEGORY});
		props.put(UPnPDevice.UDN, "uuid:PrinterType-" + getLocalHostname());    
		props.put(UPnPDevice.TYPE, "urn:schemas-prosyst-com:device:UPnPPrinterType:1");
		props.put(UPnPDevice.FRIENDLY_NAME, "UPnPPrinter");
		props.put(UPnPDevice.MANUFACTURER, "ProSyst Software AG");
		props.put(UPnPDevice.MANUFACTURER_URL, "http://www.prosyst.com");
		props.put(UPnPDevice.MODEL_DESCRIPTION, "UPnPPrinter device");
		props.put(UPnPDevice.MODEL_NAME, "upnpprinter");
		props.put(UPnPDevice.MODEL_NUMBER, "1");
		props.put(UPnPDevice.MODEL_URL, "http://www.upnpprinter.com/model.html");
		props.put(UPnPDevice.UPC, "12345");
		props.put(UPnPDevice.SERIAL_NUMBER, "123");
		props.put(UPnPDevice.UPNP_EXPORT, "Yes");
		props.put(UPnPDevice.PRESENTATION_URL, "");

		// init state var Printing  
		sv_Printing = 
				new StateVarImpl("Printing", Boolean.class, 
						UPnPStateVariable.TYPE_BOOLEAN, Boolean.FALSE, true);
		      
		// init state var PrintMessage
		sv_PrintMessage =
				new StateVarImpl("PrintMessage", String.class, 
						UPnPStateVariable.TYPE_STRING, "", false);
		      
		upnpStateVars = new UPnPStateVariable[] {sv_Printing, sv_PrintMessage};
		    
		// init action Print
		Hashtable nameVar = new Hashtable(2);
		nameVar.put(sv_PrintMessage.getName(), sv_PrintMessage);

		ac_Print = new PrintAction(new String[]{sv_PrintMessage.getName()},
				nameVar, this);
		upnpActions = new UPnPAction[] {ac_Print};

		// init Printer service
		srv_Printer = new PrinterService(this);
		upnpServices = new UPnPService[] {srv_Printer};
				    
		// init icons
		icons = new IconImpl[]{new IconImpl(32, 32, 256, 143,
				"image/gif", "osgiupnpprinter.gif")};		    
	}

	public UPnPService getService(String serviceId) {
		for (int i = 0; i < upnpServices.length; i++) {
			if (upnpServices[i].getId().equals(serviceId)) {
				return upnpServices[i];
			}
		}
		return null;
	}

	public UPnPService[] getServices() {
		return upnpServices;
	}

	public UPnPIcon[] getIcons(String locale) {
		return icons;
	}

	public Dictionary getDescriptions(String locale) {
		return props;
	}
	
	public void generateEvent(Dictionary matchDictionary, Dictionary events)
            throws Exception {
		// get all UPnPEventListener listeners from the registry of the OSGi framework. 
		ServiceReference listeners[] = Activator.context.getServiceReferences(
				UPnPEventListener.class.getName(), null);
		for (int i=0; i>listeners.length; i++) {
			// get listener filter         
			Filter filter = (Filter) listeners[i].getProperty(UPnPEventListener.UPNP_FILTER); 
			// evaluate filter
			if (filter.match(matchDictionary)) {
				((UPnPEventListener) listeners[i]).notifyUPnPEvent(
						(String) matchDictionary.get(UPnPDevice.UDN),
						(String) matchDictionary.get(UPnPService.ID), 
						events);
			}
		}
	}
	
	public static String getLocalHostname() {
		try {
			return java.net.InetAddress.getLocalHost().getHostName();
		} catch (Exception uhe) {
			return "unknown IP";
		}
	}

}
