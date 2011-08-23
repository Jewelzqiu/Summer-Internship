package upnpdevicetest;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.upnp.UPnPAction;
import org.osgi.service.upnp.UPnPStateVariable;


public class PrintAction implements UPnPAction {
	
	UPnPPrinterDevice dev;
	String name = "print";
	String[] iaNames;
	Hashtable argSSV;
	         
	public PrintAction(String[] iaNames, Hashtable argSSV, UPnPPrinterDevice dev) {
		this.iaNames = iaNames;
		this.argSSV = argSSV;
		this.dev = dev;
	}

	public String getName() {
		return name;
	}

	public String getReturnArgumentName() {
		return null;
	}

	public String[] getInputArgumentNames() {
		return iaNames;
	}

	public String[] getOutputArgumentNames() {
		return null;
	}

	public UPnPStateVariable getStateVariable(String argumentName) {
		return (UPnPStateVariable) argSSV.get(argumentName);
	}

	public Dictionary invoke(Dictionary d) throws Exception {
		String msg = (String) d.get(iaNames[0]);
	    if (msg != null) {
	    	// send printing=true event
	    	Hashtable events = new Hashtable(2);
	    	events.put(dev.sv_Printing.getName(), Boolean.TRUE);
	    	dev.srv_Printer.generateEvent(events);
	    	// printing
	    	System.out.println("Printing: \n" + msg);
	    	try {
	    		Thread.currentThread();
				Thread.sleep(5000);
	    	} catch (Exception exc) {}
	         
	    	// send printing=false event
	    	events.put(dev.sv_Printing.getName(), Boolean.FALSE);
	    	dev.srv_Printer.generateEvent(events);
	    }
		return null;
	}

}