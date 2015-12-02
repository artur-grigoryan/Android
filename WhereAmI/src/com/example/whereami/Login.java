package com.example.whereami;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Login extends Activity {
	Button btnLogin;
	Button btnLinkToRegister;
	EditText inputUsername;
	EditText inputPassword;
	TextView loginErrorMsg;
	Button fbLogin;

	// JSON Response node names
	private static String KEY_CONDITION = "condition";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_NAME = "name";
	private static String KEY_USERNAME = "username";
	private static String KEY_EMAIL = "email";
	//private static String KEY_CREATED_AT = "created_at";
	
	//private static String APP_ID = "1409497022667839";
	//private Facebook facebook = new Facebook(APP_ID);;
	//private AsyncFacebookRunner mAsyncRunner;
	
	//@SuppressWarnings("deprecation")
	SharedPreferences pref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		// Importing all assets like buttons, text fields
		inputUsername = (EditText) findViewById(R.id.loginEmail);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);
		//fbLogin = (Button) findViewById(R.id.authButton);
		//mAsyncRunner = new AsyncFacebookRunner(facebook);
		
	

		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String username = inputUsername.getText().toString();
				String password = inputPassword.getText().toString();
				UserFunctions userFunction = new UserFunctions();
				Log.d("Button", "Login");
				JSONObject json = userFunction.loginUser(username, password);

				// check for login response
				try {
					if (!json.getString(KEY_CONDITION).equals("0")) {
						loginErrorMsg.setText("");
						String res = json.getString(KEY_CONDITION);
						pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
						Editor editor = pref.edit();
						
						editor.putString("username", username);
						
						editor.putString("password", password);
						editor.putBoolean("is_logged_in", true);
						editor.commit();
							// user successfully logged in
							// Store user details in SQLite Database
							/*DatabaseHandler db = new DatabaseHandler(getApplicationContext());
							JSONObject json_user = json.getJSONObject("username");
							
							// Clear all previous data in database
							userFunction.logoutUser(getApplicationContext());
							db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_USERNAME), json_user.getString(KEY_EMAIL));*/						
							
							// Launch Dashboard Screen
							Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
							
							// Close all views before launching Dashboard
							dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(dashboard);
							
							// Close Login Screen
							finish();
						}else{
							// Error in login
							loginErrorMsg.setText("Incorrect username/password");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		// Link to Register Screen
		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						Register.class);
				startActivity(i);
				finish();
			}
		});
		
//		fbLogin.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Log.d("Image Button", "button Clicked");
//				loginToFacebook();
//			}
//
//			private void loginToFacebook() {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
	}
	
	private class AsyncLogin extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
