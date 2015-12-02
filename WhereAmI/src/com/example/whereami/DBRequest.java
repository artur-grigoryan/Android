package com.example.whereami;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DBRequest extends AsyncTask<Object,Void,ResultSet>{
	
	private static String EC2MachineInstance = "ec2-107-20-8-84.compute-1.amazonaws.com";
	private Context context;
	private Activity activity;
	ProgressDialog progress;
	
	public DBRequest(Context context, Activity activity) {
	    this.context = context;
	    this.activity = activity;
	  }
	
	@Override
	protected void onPreExecute() {
		progress = new ProgressDialog(this.context);
		progress.setMessage("Loading...");
	}
		
	@Override
	protected ResultSet doInBackground(Object... params) {
					
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return null;
		}
		System.out.println("PostgreSQL JDBC Driver Registered!");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://" + EC2MachineInstance + ":5432/ridesharing", "ubuntu",
					"PGSQLmishka13");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		try {
			//prepare statement out of the request
			PreparedStatement pstmt = connection.prepareStatement((String)params[0]);
			// check if you request or upload data
			if ((Boolean)params[1] == false){
				
				pstmt.setTimestamp(1, (Timestamp)params[2]);
				pstmt.setString(2, (String)params[3]);
				pstmt.setString(3, (String)params[4]);
				pstmt.setDouble(4, (Double)params[5]);
				pstmt.setDouble(5, (Double)params[6]);
				pstmt.setDouble(6, (Double)params[7]);
				pstmt.setDouble(7, (Double)params[8]);
				pstmt.setTimestamp(8, (Timestamp)params[9]);
				pstmt.executeUpdate();
				connection.close();
				return null;
			
			} else {
				
				ResultSet schedule = pstmt.executeQuery();
				connection.close();
				return schedule;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(ResultSet set) {
        super.onPostExecute(set);
        
        if (set != null){
        	
        	ArrayList<String> tripsList = new ArrayList<String>();
        	ListView lv = (ListView)this.activity.findViewById(R.id.trips_list);
        	
        	try{
        		while(set.next()){
        			tripsList.add("Id: " + "From: " + set.getString("depaddress") + "\nTo: " + set.getString("destaddress") + "\nDeparture Time: "
        					+ set.getString("deptimepref") + "\nArrival Time: " + set.getString("arrivaltimepref"));
        		}
        	} catch (SQLException e){
        		e.printStackTrace();
        	}	
        
        	final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.context,R.layout.lists_text_view,tripsList);
      	   	lv.setAdapter(adapter);
        	
        }		
		
        progress.dismiss();
        
   }
}