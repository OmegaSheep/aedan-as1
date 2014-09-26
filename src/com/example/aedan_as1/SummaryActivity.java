package com.example.aedan_as1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SummaryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary_activity);
		TextView sum1 = (TextView) findViewById(R.id.sum_val1);
		TextView sum2 = (TextView) findViewById(R.id.sum_val2);
		TextView sum3 = (TextView) findViewById(R.id.sum_val3);
		TextView sum4 = (TextView) findViewById(R.id.sum_val4);
		TextView sum5 = (TextView) findViewById(R.id.sum_val5);
		sum1.append(String.valueOf(ListSharingClass.calculateChecked(ListSharingClass.todoList)));
		sum4.append(String.valueOf(ListSharingClass.calculateChecked(ListSharingClass.archiveList)));
		sum2.append(String.valueOf(ListSharingClass.calculateUnchecked(ListSharingClass.todoList)));
		sum5.append(String.valueOf(ListSharingClass.calculateUnchecked(ListSharingClass.archiveList)));
		sum3.append(String.valueOf(ListSharingClass.calculateTotal(ListSharingClass.archiveList)));
		
	}
}
