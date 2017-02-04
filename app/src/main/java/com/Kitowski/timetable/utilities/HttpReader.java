package com.Kitowski.timetable.utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;

public class HttpReader extends AsyncTask<String, Void, ArrayList<String>> {
	private final static String logcatTAG = "HTTP reader";
	private ArrayList<String> content;
	
	@Override
	protected ArrayList<String> doInBackground(String... params) {
		content = new ArrayList<String>();
		read(params[0], params[1]);
		content = removeTabs();
		content = removeHTML();
		
		return content;
	}
	
	private void read(String website, String htmlSplitter) {
		URL url;
		InputStream input;
		BufferedReader reader;
		boolean contentBegin = false;
		
		try {
			url = new URL(website);
			input = url.openStream();
			reader = new BufferedReader(new InputStreamReader(input));
			
			for(String line = ""; line != null; line = reader.readLine()) {
				if(line.matches("(?i).*<" + htmlSplitter + ".*")) {
					contentBegin = true;
					continue;
				}
				else if(line.matches("(?i).*</" + htmlSplitter + ">.*")) break;
				
				if(contentBegin) content.add(line);
			}
		}
		catch(Exception e) {
			Log.e(logcatTAG, "Failed to load website");
		}
	}
	
	private ArrayList<String> removeTabs() {
		ArrayList<String> buffer = new ArrayList<String>();
		
		for(String str : content) buffer.add(str.replaceAll("\\t", ""));
		return buffer;
	}
	
	private ArrayList<String> removeHTML() {
		ArrayList<String> buffer = new ArrayList<String>();
		
		for(String str : content) {
			String s = str.replaceAll("<[^>]*>", "");
			if(!s.contentEquals("")) buffer.add(s);
		}
		return buffer;
	}
}
