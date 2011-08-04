package com.jewelz.devicemanager;

import java.util.ArrayList;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;

public class ActionInfoActivity extends PreferenceActivity {

	ArrayList<Object> ActionInfo;
	PreferenceCategory Inputs;
	PreferenceCategory Outputs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionInfo = ActionsActivity.getAction();
		
		addPreferencesFromResource(R.xml.action);
		Inputs = (PreferenceCategory) findPreference("input");
		Outputs = (PreferenceCategory) findPreference("output");
		
		String[] inputs = (String[]) ActionInfo.get(1);
		if (inputs != null) {
			for (String input : inputs) {
				Preference p = new Preference(this);
				p.setTitle(input);
				Inputs.addPreference(p);
			}
		}
		
		String[] outputs = (String[]) ActionInfo.get(2);
		if (outputs != null) {
			for (String output : outputs) {
				Preference p = new Preference(this);
				p.setTitle(output);
				Outputs.addPreference(p);
			}
		}
	}	
	
}
