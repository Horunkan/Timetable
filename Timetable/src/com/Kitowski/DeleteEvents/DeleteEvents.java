package com.Kitowski.DeleteEvents;

import com.Kitowski.timetable.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class DeleteEvents extends Activity {
	private LinearLayout layout;
	private CheckBox selectAllCheckbox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_events);
		layout = (LinearLayout)findViewById(R.id.deleteLayout);
		selectAllCheckbox = (CheckBox)findViewById(R.id.checkBox_selectAll);
	}
}
