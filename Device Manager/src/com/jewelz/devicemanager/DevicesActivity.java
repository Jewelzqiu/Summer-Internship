package com.jewelz.devicemanager;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class DevicesActivity extends ListActivity {
	
	private Socket socket;
	private ObjectInputStream in;
	private PrintWriter out;
	private static ArrayList<Hashtable<String, Object>> Devices;
	private static int device_id = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SimpleAdapter adapter = new SimpleAdapter(
				this,
				getData(),
				R.layout.devices,
				new String[]{"name"},
				new int[]{R.id.device_name});
		setListAdapter(adapter);		
	}
	
	private List<Map<String, Object>> getData() {
		Vector<Hashtable<String, Object>> devices = null;
		try {
			 devices = getDevicesInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Devices = new ArrayList<Hashtable<String, Object>>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();		
		Map<String, Object> map = new HashMap<String, Object>();
		
		for (Hashtable<String, Object> device : devices) {
			map.put("name", device.get("UPnP.device.modelName"));
			list.add(map);
			Devices.add(device);
		}
		
//		map = new HashMap<String, Object>();
//		map.put("name", "device1");
//        list.add(map);
//        
//        map = new HashMap<String, Object>();
//		map.put("name", "device2");
//        list.add(map);

		device_id = -1;
        return list;
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
	protected void onListItemClick(ListView l, View v, int position, long id) {
		device_id = position;
		Intent intent = new Intent(DevicesActivity.this, DeviceInfoActivity.class);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
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
			SimpleAdapter adapter = new SimpleAdapter(
					this,
					getData(),
					R.layout.devices,
					new String[]{"name"},
					new int[]{R.id.device_name});
			setListAdapter(adapter);
		}
		return true;
	}
	
	public static Hashtable<String, Object> getDeviceInfo() {
		if (device_id != -1) {
			return Devices.get(device_id);
		}
		return null;
	}
	
}
