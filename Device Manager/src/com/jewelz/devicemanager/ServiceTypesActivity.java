package com.jewelz.devicemanager;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Vector;

import com.jewelz.devicemanager.device.Device;
import com.jewelz.devicemanager.device.Service;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class ServiceTypesActivity extends PreferenceActivity {
	
	private static Hashtable<String, Vector<Service>> ServiceTypes;
	private static String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.details);
		try {
			addServiceTypes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void addServiceTypes() throws Exception {
		Socket socket = new Socket(MainActivity.IP, MainActivity.port);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println("query");
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		Vector<Hashtable<String, Object>> devices =
				(Vector<Hashtable<String, Object>>) in.readObject();
		in.close();
		socket.close();
		
		ServiceTypes = new Hashtable<String, Vector<Service>>();
		for (Hashtable<String, Object> o : devices) {
			Device device = new Device(o);
			Vector<Service> services = device.getServices();
			for (Service service : services) {
				String service_type = service.getType();
				if (ServiceTypes.contains(service_type)) {
					Vector<Service> Services = ServiceTypes.get(service_type);
					Services.add(service);
					ServiceTypes.put(service_type, Services);
				} else {
					Vector<Service> Services = new Vector<Service>();
					Services.add(service);
					ServiceTypes.put(service_type, Services);
					Preference p = new Preference(this);
					p.setTitle(service_type);
					p.setOnPreferenceClickListener(new OnPreferenceClickListener() {

						@Override
						public boolean onPreferenceClick(Preference preference) {
							type = preference.getTitle().toString();
							Intent intent = new Intent(ServiceTypesActivity.this, 
									ServicesActivity.class);
							intent.putExtra("FROM_MAIN", true);
							startActivity(intent);
							return false;
						}
						
					});
					getPreferenceScreen().addPreference(p);
				}
			}
			type = new String();
		}		
		
	}
	
	public static Vector<Service> getService() {
		return ServiceTypes.get(type);
	}

}
