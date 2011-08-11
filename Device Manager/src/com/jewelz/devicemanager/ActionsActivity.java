package com.jewelz.devicemanager;

import java.util.Vector;

import com.jewelz.devicemanager.device.Action;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class ActionsActivity extends PreferenceActivity {
	
	private static Vector<Action> Actions;
	private static int action_id = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.details);
		Actions = ServicesActivity.getActions();
		addActions();
	}
	
	private void addActions() {
		if (Actions == null) {
			return;
		}
		
		for (int i = 0; i < Actions.size(); i++) {
			Preference item = new Preference(this);
			Action action = Actions.get(i);
			item.setTitle(action.getName());
			item.setKey(Integer.toString(i));
			item.setOnPreferenceClickListener(new OnActionClickListener());
			getPreferenceScreen().addPreference(item);
		}
			
		action_id = -1;
	}
	
	public static Action getAction() {
		if (action_id != -1) {
			return Actions.get(action_id);
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
