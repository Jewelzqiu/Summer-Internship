package com.jewelz.devicemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

public class DeviceInfoActivity extends ListActivity {

	private Hashtable<String, Object> DeviceInfo;
	static Hashtable<String, ArrayList<Object>> Services;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DeviceInfo = DevicesActivity.getDeviceInfo();
		SimpleAdapter adapter = new SimpleAdapter(
				this,
				getData(),
				R.layout.item,
				new String[]{"title", "summary"},
				new int[]{R.id.title, R.id.summary});
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "Services");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == 0) {
			Intent intent = new Intent(DeviceInfoActivity.this, ServicesActivity.class);
			startActivity(intent);
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();		
		Map<String, Object> map;
		
		for (String key : DeviceInfo.keySet()) {
			if (key.equals("Device Services")) {
				Services = (Hashtable<String, ArrayList<Object>>)
						DeviceInfo.get("Device Services");
				continue;
			}
			
			map = new HashMap<String, Object>();
			map.put("title", key);
			map.put("summary", DeviceInfo.get(key));
			list.add(map);
		}

        return list;
	}
	
}
