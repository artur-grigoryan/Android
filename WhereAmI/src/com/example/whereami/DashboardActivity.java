
package com.example.whereami;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends Activity {
	//UserFunctions userFunctions;
	Button getMessages;
	Button sentMessages;
	Button logout;
	TextView greeting;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       	  
        
        getMessages = (Button) findViewById(R.id.getMessages);
    	sentMessages = (Button) findViewById(R.id.getSent);
    	greeting = (TextView) findViewById(R.id.welcome);
    	logout = (Button) findViewById(R.id.logout);
    	
    	getMessages.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String user = "sola";
				final UserFunctions userFunction = new UserFunctions();
				
				Condition cond = userFunction.getMessages(user);
				final Messages[] messages = cond.getCondition();
				if(messages.length==0){
					Toast toast = Toast.makeText(getApplicationContext(), "No unread messages", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 50, 50);
					toast.show();
				}
				else{
					Intent getMessages = new Intent(getApplicationContext(), GetMessages.class);
    				startActivity(getMessages);
    				//finish();
				}
				
			}
    	});
    	
    	sentMessages.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String user = "ibrahim";
				final UserFunctions userFunction = new UserFunctions();
				
				Condition cond = userFunction.getSentMessages(user);
				final Messages[] messages = cond.getCondition();
				if(messages.length==0){
					Toast toast = Toast.makeText(getApplicationContext(), "No sent messages", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 50, 50);
					toast.show();
				}
				else{
					Intent getSentMessages = new Intent(getApplicationContext(), GetSentMessages.class);
    				startActivity(getSentMessages);
    				//finish();
				}
				
			}
    	});
    
    
    	logout.setOnClickListener(new View.OnClickListener() {
		
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
				SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
				Editor editor = pref.edit();
				editor.clear();
				editor.commit();
				Intent login = new Intent(getApplicationContext(), Login.class);
	        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	startActivity(login);
	        	// Closing dashboard screen
	        	finish();
			}
		});
 
    }
  
        

    
    public void onAttachedToWindow(){
    	
    	SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        if(pref.getBoolean("is_logged_in", false)){
        	String username = pref.getString("username", null);
        	String password = pref.getString("password", null);
        	UserFunctions userFunction = new UserFunctions();
        		
			JSONObject json = userFunction.loginUser(username, password);
			
			setContentView(R.layout.dashboard);
        	
        	greeting.setText("Welcome "+ username);
        	
        	
        	
		
        }
        
        else{
        	// user is not logged in show login screen
        	Intent login = new Intent(getApplicationContext(), Login.class);
        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(login);
        	// Closing dashboard screen
        	finish();
        }
    }
}