package com.jewelz.devicemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ServicesActivity extends ListActivity {

	private static ArrayList<ArrayList<Object>> Services;
	private static int service_id = -1;
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		service_id = position;
		Intent intent = new Intent(ServicesActivity.this, ActionsActivity.class);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Services = new ArrayList<ArrayList<Object>>();
		SimpleAdapter adapter = new SimpleAdapter(
				this,
				getData(),
				R.layout.devices,
				new String[]{"name"},
				new int[]{R.id.device_name});
		setListAdapter(adapter);
	}
	
	public static ArrayList<Object> getActions() {
		if (service_id != -1) {
			return Services.get(service_id);
		}
		return null;
	}
	
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();		
		Map<String, Object> map;
		
		for (String key : DeviceInfoActivity.Services.keySet()) {
			map = new HashMap<String, Object>();
			ArrayList<Object> service = DeviceInfoActivity.Services.get(key);
			map.put("name", key);
			list.add(map);
			
			ServicesActivity.Services.add(service);
		}

		service_id = -1;
        return list;
	}
}
