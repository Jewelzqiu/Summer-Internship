package com.jewelz.devicemanager;

import java.util.Hashtable;
import java.util.Vector;

import com.jewelz.devicemanager.device.Device;
import com.jewelz.devicemanager.device.Service;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

public class DeviceInfoActivity extends PreferenceActivity {

	private Device DeviceInfo;
	static Vector<Service> Services;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.details);
		DeviceInfo = DevicesActivity.getDevice();
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
			intent.putExtra("FROM_MAIN", false);
			startActivity(intent);
		}
		return true;
	}
	
	private void addDeviceInfo() {
		Services = DeviceInfo.getServices();
		Hashtable<String, Object> info = DeviceInfo.getDeviceInfo();
		for (String key : info.keySet()) {
			Preference item = new Preference(this);
			item.setTitle(key);
			item.setSummary(info.get(key).toString());
			getPreferenceScreen().addPreference(item);
		}
	}
	
}
