package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity {
	
	Button viewTripsBtn, createTripBtn;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main_menu);
		
		viewTripsBtn = (Button)this.findViewById(R.id.view_trip);
		  viewTripsBtn.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
		      
		    	Intent i = new Intent(MainMenu.this, ScheduledTrips.class);
				startActivity(i);
				
		    }
		  });
		  
		  createTripBtn = (Button)this.findViewById(R.id.create_trip);
		  createTripBtn.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
		      
		    	Intent i = new Intent(MainMenu.this, WhereAmI.class);
				startActivity(i);
				
		    }
		  });
		
	}
	
	
	
}
