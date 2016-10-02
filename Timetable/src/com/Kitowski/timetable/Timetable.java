package com.Kitowski.timetable;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class Timetable extends Activity {
	private LinearLayout layout;
	private ProgressBar loadBar;
	
	private ArrayList<String> dates;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timetable);
		
		layout = (LinearLayout)findViewById(R.id.mainLayout);
		loadBar = new ProgressBar(this);
		layout.addView(loadBar);
		
		HttpReader http = (HttpReader) new HttpReader().execute("https://inf.ug.edu.pl/terminy-zjazdow-semestr-zimowy-201617", "table");
		
		
		try {
			dates = http.get();
			
			for(String str : dates) Log.i("TEST", str);
		}
		catch(Exception e) {
			
		}
	}
}
