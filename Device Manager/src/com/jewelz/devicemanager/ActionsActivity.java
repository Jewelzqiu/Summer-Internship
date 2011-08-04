package com.jewelz.devicemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ActionsActivity extends ListActivity {
	
	private static ArrayList<Object> Actions;
	private static int action_id = -1;
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		action_id = position;
		Intent intent = new Intent(ActionsActivity.this, ActionInfoActivity.class);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Actions = ServicesActivity.getActions();
		SimpleAdapter adapter = new SimpleAdapter(
				this,
				getData(),
				R.layout.devices,
				new String[]{"name"},
				new int[]{R.id.device_name});
		setListAdapter(adapter);
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getData() {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();		
		Map<String, Object> map;
		
		for (Object o : Actions) {
			map = new HashMap<String, Object>();
			ArrayList<Object> action = (ArrayList<Object>) o;
			map.put("name", action.get(0));
			list.add(map);
		}
		
		action_id = -1;
        return list;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Object> getAction() {
		if (action_id != -1) {
			return (ArrayList<Object>) Actions.get(action_id);
		}
		return null;
	}

}
