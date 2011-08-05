package com.jewelz.devicemanager;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class ActionsActivity extends PreferenceActivity {
	
	private static ArrayList<Object> Actions;
	private static int action_id = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.actions);
		Actions = ServicesActivity.getActions();
		addActions();
	}
	
	@SuppressWarnings("unchecked")
	private void addActions() {
		if (Actions == null) {
			return;
		}
		
		int flag = 0;		
		for (Object o : Actions) {
			ArrayList<Object> action = (ArrayList<Object>) o;
			Preference item = new Preference(this);
			item.setTitle(action.get(0).toString());
			item.setKey(flag++ + "");
			item.setOnPreferenceClickListener(new OnActionClickListener());
			getPreferenceScreen().addPreference(item);
		}		
		action_id = -1;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Object> getAction() {
		if (action_id != -1) {
			return (ArrayList<Object>) Actions.get(action_id);
		}
		return null;
	}
	
	private class OnActionClickListener implements OnPreferenceClickListener {

		@Override
		public boolean onPreferenceClick(Preference preference) {
			action_id = new Integer(preference.getKey());
			Intent intent = new Intent(ActionsActivity.this, ActionInfoActivity.class);
			startActivity(intent);
			return false;
		}
		
	}

}
