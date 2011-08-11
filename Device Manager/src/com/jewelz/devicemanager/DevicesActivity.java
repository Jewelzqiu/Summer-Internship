package com.jewelz.devicemanager;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import com.jewelz.devicemanager.device.Device;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

public class DevicesActivity extends PreferenceActivity {
	
	private Socket socket;
	private ObjectInputStream in;
	private PrintWriter out;
	private static ArrayList<Device> Devices;
	private static int device_id = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.details);
		
		addDevices();
	}
	
	private void addDevices() {
		Vector<Hashtable<String, Object>> devices = null;
		try {
			devices = getDevicesInfo();
			Devices = new ArrayList<Device>();
			
			if (devices != null) {
				for (Hashtable<String, Object> device : devices) {
					Preference deviceitem = new Preference(this);
					deviceitem.setTitle((CharSequence) device.get("UPnP.device.modelName"));
					deviceitem.setKey(Devices.size() + "");
					deviceitem.setOnPreferenceClickListener(new OnDeviceClickListener());
					getPreferenceScreen().addPreference(deviceitem);
					Devices.add(new Device(device));
				}
			}	
			device_id = -1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private Vector<Hashtable<String, Object>> getDevicesInfo() throws Exception {
		socket = new Socket(MainActivity.IP, MainActivity.port);
		out = new PrintWriter(socket.getOutputStream(), true);
		out.println("query");
		in = new ObjectInputStream(socket.getInputStream());
		Vector<Hashtable<String, Object>> devices =
				(Vector<Hashtable<String, Object>>) in.readObject();
		in.close();
		socket.close();
		return devices;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "Refresh");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == 0) {
			this.getPreferenceScreen().removeAll();
			addDevices();
		}
		return true;
	}
	
	public static Device getDevice() {
		if (device_id != -1) {
			return Devices.get(device_id);
		}
		return null;
	}
	
	private class OnDeviceClickListener implements OnPreferenceClickListener {

		@Override
		public boolean onPreferenceClick(Preference preference) {
			device_id = new Integer(preference.getKey());
			Intent intent = new Intent(DevicesActivity.this, DeviceInfoActivity.class);
			startActivity(intent);
			return false;
		}
		
	}
	
}
