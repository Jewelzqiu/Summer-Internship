package com.jewelz.devicemanager;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Hashtable;

import com.jewelz.devicemanager.device.Action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.widget.Toast;

public class ActionInfoActivity extends PreferenceActivity {

	Action ActionInfo;
	PreferenceCategory Inputs;
	PreferenceCategory Outputs;
	Preference InvokePreference;
	Hashtable<String, String> InputArguments = new Hashtable<String, String>();
	@SuppressWarnings("rawtypes")
	Dictionary results;
	
	Handler myHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionInfo = ActionsActivity.getAction();
		
		addPreferencesFromResource(R.xml.action);
		Inputs = (PreferenceCategory) findPreference("input");
		Outputs = (PreferenceCategory) findPreference("output");
		
		String[] inputs = ActionInfo.getInputs();
		if (inputs != null) {
			for (String input : inputs) {
				EditTextPreference p = new EditTextPreference(this);
				p.setOnPreferenceChangeListener(new OnInputChangeListener());
				p.setTitle(input);
				p.setKey(input);
				Inputs.addPreference(p);
			}
		} else {
			Preference p = new Preference(this);
			p.setTitle("No input arguments");
			p.setEnabled(false);
			Inputs.addPreference(p);
		}
		
		String[] outputs = ActionInfo.getOutputs();
		if (outputs != null) {
			for (String output : outputs) {
				EditTextPreference p = new EditTextPreference(this);
				p.setTitle(output);
				p.setKey(output);
				Outputs.addPreference(p);
			}
		} else {
			Preference p = new Preference(this);
			p.setTitle("No output arguments");
			p.setEnabled(false);
			Outputs.addPreference(p);
		}
		
		InvokePreference = findPreference("invoke");
		InvokePreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				InputArguments = new Hashtable<String, String>();
				if (ActionInfo.getInputs() == null) {
					return true;
				}
				
				for (String key : ActionInfo.getInputs()) {
					EditTextPreference p = (EditTextPreference) findPreference(key);
					if (p.getSummary() == null) {
						new AlertDialog.Builder(ActionInfoActivity.this)
						.setTitle("Warning")
						.setMessage("Please fill the input arguments")
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
						})
						.show();
						return true;
					}
					InputArguments.put(key, p.getSummary().toString());
				}
				
				InvokePreference.setEnabled(false);
				InvokePreference.setTitle("Invoking...");
				
				myHandler.post(Invoke);
				return true;
			}
			
		});
	}
	
	private class OnInputChangeListener implements OnPreferenceChangeListener {

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			preference.setSummary(newValue.toString());
			return false;
		}
		
	}
	
	Runnable Invoke = new Runnable() {

		@SuppressWarnings("rawtypes")
		@Override
		public void run() {
			Socket socket;
			try {
				socket = new Socket(MainActivity.IP, MainActivity.port);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println("invoke");
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(ActionInfo.getProps(InputArguments));
				oos.flush();
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				results = (Dictionary) in.readObject();
				out.close();
				oos.close();
				in.close();
				socket.close();
				if (ActionInfo.getOutputs() != null) {
					for (String key : ActionInfo.getOutputs()) {
						EditTextPreference p = (EditTextPreference) findPreference(key);
						String value = results.get(key).toString();
						if (value != null) {
							p.setText(value);
							p.setSummary(value);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			InvokePreference.setTitle("Invoke the action");
			InvokePreference.setEnabled(true);
			Toast.makeText(ActionInfoActivity.this,
					"Invoke complete", Toast.LENGTH_SHORT).show();
			myHandler.removeCallbacks(Invoke);
		}
		
	};
	
}
