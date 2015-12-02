package com.example.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

public class ProximityIntentReceiver extends BroadcastReceiver{
	
	@Override
	public void onReceive(Context context, Intent intent){
		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		
		Boolean entering = intent.getBooleanExtra(key, false);
		
		// perform proximity alert actions
	}
}