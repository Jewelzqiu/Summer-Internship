package com.jewelz.devicemanager.device;

import java.util.ArrayList;
import java.util.Vector;

public class Service {

	String device_id;
	String service_id;
	String service_type;
	Vector<Action> Actions = new Vector<Action>();
	
	@SuppressWarnings("unchecked")
	public Service(String device_id, String service_id, ArrayList<Object> actions) {
		this.device_id = device_id;
		this.service_id = service_id;
		this.service_type = actions.get(0).toString();
		for (int i = 1; i < actions.size(); i++) {
			ArrayList<Object> action_info = (ArrayList<Object>) actions.get(i);
			Actions.add(new Action(device_id, service_id, action_info.get(0).toString(),
					(String[]) action_info.get(1), (String[]) action_info.get(2)));
		}
	}
	
	public String getID() {
		return service_id;
	}
	
	public String getType() {
		return service_type;
	}
	
	public Vector<Action> getActions() {
		return Actions;
	}
	
}
