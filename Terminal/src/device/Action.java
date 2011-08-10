package device;

import java.util.Hashtable;

public class Action {

	Hashtable<String, Object> props;
	String device_id;
	String service_id;
	String name;
	String[] input;
	String[] output;
	
	public Action(String device_id, String service_id, String name,
			String[] input, String[] output) {
		this.device_id = device_id;
		this.service_id = service_id;
		this.name = name;
		this.input = input;
		this.output = output;
	}
	
	@SuppressWarnings("rawtypes")
	public Hashtable<String, Object> getProps(Hashtable arguments) {
		props = new Hashtable<String, Object>();
		props.put("DEVICE_ID", device_id);
		props.put("SERVICE_ID", service_id);
		props.put("ACTION_NAME", name);
//		@SuppressWarnings("rawtypes")
//		Hashtable arguments = new Hashtable();
//		for (int i = 0; i < input.length; i++) {
//			arguments.put(input[i], values[i]);
//		}
		props.put("ARGUMENTS", arguments);
		return props;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getInputs() {
		return input;
	}
	
	public String[] getOutputs() {
		return output;
	}
	
}
