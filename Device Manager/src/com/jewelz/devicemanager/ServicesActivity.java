package com.jewelz.devicemanager;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class ServicesActivity extends PreferenceActivity {

	private static ArrayList<ArrayList<Object>> Services;
	private static int service_id = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.services);
		Services = new ArrayList<ArrayList<Object>>();
		addServices();
	}
	
	public static ArrayList<Object> getActions() {
		if (service_id != -1) {
			return Services.get(service_id);
		}
		return null;
	}
	
	private void addServices() {
		if (DeviceInfoActivity.Services == null) {
			return;
		}
		
		for (String key : DeviceInfoActivity.Services.keySet()) {
			ArrayList<Object> service = DeviceInfoActivity.Services.get(key);
			Preference item = new Preference(this);
			item.setTitle(key);
			item.setKey(ServicesActivity.Services.size() + "");
			item.setOnPreferenceClickListener(new OnServiceClickListener());
			getPreferenceScreen().addPreference(item);
			
			ServicesActivity.Services.add(service);
		}
		service_id = -1;
	}
	
	private class OnServiceClickListener implements OnPreferenceClickListener {

		@Override
		public boolean onPreferenceClick(Preference preference) {
			service_id = new Integer(preference.getKey());
			Intent intent = new Intent(ServicesActivity.this, ActionsActivity.class);
			startActivity(intent);
			return false;
		}
		
	}
}
