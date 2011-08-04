package com.jewelz.devicemanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class MainActivity 
		extends PreferenceActivity 
		implements OnSharedPreferenceChangeListener {

	public static String IP;
	public static int port;
	
	private EditTextPreference IPText;
	private EditTextPreference PortText;
	private Preference start;
	
	@SuppressWarnings("null")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
		IPText = (EditTextPreference) findPreference("IPText");
		PortText = (EditTextPreference) findPreference("PortText");
		IP = IPText.getText();
		String Port = PortText.getText();
		IPText.setSummary(IP);
		if (Port == null || Port.equals("")) {
			port = 0;
		} else {
			port = new Integer(Port);
		}
		PortText.setSummary(Port);
		
		start = findPreference("StartPreference");
		start.setOnPreferenceClickListener(new startListener());
		
		getPreferenceManager().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);	
		
	}

	@Override
	protected void onDestroy() {
		getPreferenceManager().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
		super.onDestroy();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals("IPText")) {
			IP = IPText.getText();
			IPText.setSummary(IPText.getText());
		}
		if (key.equals("PortText")) {
			String Port = PortText.getText();
			if (Port.equals("")) {
				port = 0;
			} else {
				port = new Integer(Port);
			}
			PortText.setSummary(Port);
		}
	}
	
	private class startListener implements OnPreferenceClickListener {
		@Override
		public boolean onPreferenceClick(Preference preference) {
			if (IP.equals("") || port == 0) {
				new AlertDialog.Builder(MainActivity.this)
				.setTitle("Warning")
				.setMessage("Please set the valid IP address and the port number first.")
				.setPositiveButton("OK", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// close
					}
					
				})
				.show();
				return false;
			}
			
			ConnectivityManager cm = (ConnectivityManager) 
					getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netinfo = cm.getActiveNetworkInfo();
			if (!netinfo.isConnected()) {
				new AlertDialog.Builder(MainActivity.this)
				.setTitle("Warning")
				.setMessage("Please check the network connection state.")
				.setPositiveButton("OK", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// close
					}
					
				})
				.show();
				return false;
			}
			
			Intent intent = new Intent(MainActivity.this, DevicesActivity.class);
			startActivity(intent);
			return false;
		}
		
	}

}
