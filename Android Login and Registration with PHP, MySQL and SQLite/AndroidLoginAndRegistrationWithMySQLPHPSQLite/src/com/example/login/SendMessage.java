package com.example.login;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidhive.R;
import com.example.login.library.DatabaseHandler;
import com.example.login.library.UserFunctions;



public class SendMessage extends Activity {
	
	Button send;
	EditText messageBody;
	EditText messageSubject;
	TextView errorMessage;

	// JSON Response node names
	
	//@SuppressWarnings("deprecation")
	SharedPreferences pref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendmessage);

		// Importing all assets like buttons, text fields
		messageBody = (EditText) findViewById(R.id.messageBody);
		messageSubject = (EditText) findViewById(R.id.messageSubject);
		send = (Button) findViewById(R.id.sendButton);
		errorMessage = (TextView) findViewById(R.id.errorMessage);
		
		
		

		// Login button Click Event
		send.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				//String subject = messageSubject.getText().toString();
				
				String body = messageBody.getText().toString();
				UserFunctions userFunction = new UserFunctions();
				
				SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
				//String sender = pref.getString("username", null); // load user data. since we log in using email, we need to get username first
				String sender = "ibrahim";
				String receiver = "sola";
				//String receiver = getIntent().getExtras().getString("Receiver"); // need to get the owner of root
				String subject = getIntent().getExtras().getString("Subject"); // the route_id
				messageSubject.setText(subject);
					
					
				JSONObject json = userFunction.sendMessage(sender, receiver, body, subject);
				try {
					if (json.getString("condition").equals("0")) {
						errorMessage.setText("Error while sending message, try it again");
					}
					else {
						Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
						
						// Close all views before launching Dashboard
					dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(dashboard);
					finish();
					}
				} catch(JSONException e){
					e.printStackTrace();
				}
			}
					
				
		});
	}
}
