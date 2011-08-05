package com.jewelz.devicemanager;

import java.util.ArrayList;
import java.util.Hashtable;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

public class DeviceInfoActivity extends PreferenceActivity {

	private Hashtable<String, Object> DeviceInfo;
	static Hashtable<String, ArrayList<Object>> Services;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.deviceinfo);
		DeviceInfo = DevicesActivity.getDeviceInfo();
		addDeviceInfo();
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
	private void addDeviceInfo() {		
		for (String key : DeviceInfo.keySet()) {
			if (key.equals("Device Services")) {
				Services = (Hashtable<String, ArrayList<Object>>)
						DeviceInfo.get("Device Services");
				continue;
			}
			
			Preference item = new Preference(this);
			item.setTitle(key);
			item.setSummary(DeviceInfo.get(key).toString());
			getPreferenceScreen().addPreference(item);
		}
	}
	
}
