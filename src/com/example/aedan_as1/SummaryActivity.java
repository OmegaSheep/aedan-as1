package com.example.aedan_as1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SummaryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary_activity);
		
		//The 5 values represent the 5 fields that must be displayed in the summary page.
		
		TextView sum1 = (TextView) findViewById(R.id.sum_val1); //Todo Checked
		TextView sum2 = (TextView) findViewById(R.id.sum_val2);	//Todo Unchecked
		TextView sum3 = (TextView) findViewById(R.id.sum_val3);	//Total Archived
		TextView sum4 = (TextView) findViewById(R.id.sum_val4);	//Archived Checked
		TextView sum5 = (TextView) findViewById(R.id.sum_val5);	//Archived Unchecked
		
		
		sum1.append(String.valueOf(ListSharingClass.calculateChecked(ListSharingClass.todoList)));
		sum4.append(String.valueOf(ListSharingClass.calculateChecked(ListSharingClass.archiveList)));
		sum2.append(String.valueOf(ListSharingClass.calculateUnchecked(ListSharingClass.todoList)));
		sum5.append(String.valueOf(ListSharingClass.calculateUnchecked(ListSharingClass.archiveList)));
		sum3.append(String.valueOf(ListSharingClass.calculateTotal(ListSharingClass.archiveList)));
		
	}
}
