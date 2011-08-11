package com.jewelz.devicemanager;

import java.util.Vector;

import com.jewelz.devicemanager.device.Action;
import com.jewelz.devicemanager.device.Service;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class ServicesActivity extends PreferenceActivity {

	private static int service_id = -1;
	private boolean FromMain;
	private static Vector<Service> Services;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FromMain = this.getIntent().getBooleanExtra("FROM_MAIN", true);
		addPreferencesFromResource(R.xml.details);
		addServices();
	}
	
	public static Vector<Action> getActions() {
		if (service_id != -1) {
			return Services.get(service_id).getActions();
		}
		return null;
	}
	
	private void addServices() {
		if (FromMain) {
			
			addAllServices();
			
		} else {
			
			if (DeviceInfoActivity.Services == null) {
				return;
			}
			Services = DeviceInfoActivity.Services;
			for (int i = 0; i < Services.size(); i++) {
				Service service = Services.get(i);
				Preference item = new Preference(this);
				item.setTitle(service.getID());
				item.setKey(Integer.toString(i));
				item.setOnPreferenceClickListener(new OnServiceClickListener());
				getPreferenceScreen().addPreference(item);
			}
		}	
		
		service_id = -1;
	}
	
	private void addAllServices() {
		Services = ServiceTypesActivity.getService();
		if (Services == null) {
			return;
		}
		
		for (int i = 0; i < Services.size(); i++) {
			Preference item = new Preference(this);
			item.setTitle(Services.get(i).getID());
			item.setKey(Integer.toString(i));
			item.setOnPreferenceClickListener(new OnServiceClickListener());
			getPreferenceScreen().addPreference(item);
		}
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
