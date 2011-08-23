package upnpdevicetest;

import org.osgi.service.upnp.UPnPStateVariable;

public class StateVarImpl implements UPnPStateVariable {
	
	String name;
	Class javaDataType;
	String UPnPDataType;
	Object defaultValue;
	boolean canSendEvents;
	
	public StateVarImpl(String name, Class javaDataType, String UPnPDataType,
			Object defaultValue, boolean canSendEvents) {
		this.name = name;
		this.javaDataType = javaDataType;
		this.UPnPDataType = UPnPDataType;
		this.defaultValue = defaultValue;
		this.canSendEvents = canSendEvents;
	}

	public String getName() {
		return name;
	}

	public Class getJavaDataType() {
		return javaDataType;
	}

	public String getUPnPDataType() {
		return UPnPDataType;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public String[] getAllowedValues() {
		return null;
	}

	public Number getMinimum() {
		return null;
	}

	public Number getMaximum() {
		return null;
	}

	public Number getStep() {
		return null;
	}

	public boolean sendsEvents() {
		return canSendEvents;
	}

}
