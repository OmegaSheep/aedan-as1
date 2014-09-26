package com.example.aedan_as1;

//import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	int todoDone = 0;
	int todoNotDone = 0;
	int todo_archive = 0;
	int todoDone_archive = 0;
	int todoNotDone_archive = 0;
	CustomListAdapter listAdapter;
	CustomListAdapter archiveAdapter;
	Button emailAll, archive, add, summary;
	EditText userinput;
	boolean archiveFlag = true;
	@Override
	protected void onResume() {
		super.onResume();
        SaveAndLoad.INSTANCE.loadContext(this);
        SaveAndLoad.INSTANCE.loadList();
        listAdapter.update();
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       
		
        setContentView(R.layout.activity_main);
        
        userinput = (EditText) findViewById(R.id.todoField); 
        
        add = (Button) findViewById(R.id.add_button);
        archive = (Button) findViewById(R.id.archive_button);
        emailAll = (Button) findViewById(R.id.all_button);
        summary = (Button) findViewById(R.id.summary_button);
        
        //Set up array adapter
        ListView todoListView = (ListView) findViewById(R.id.listView);
        listAdapter = new CustomListAdapter(this, ListSharingClass.todoList);//new CustomListAdapter(this, ListSharingClass.todoList);
        archiveAdapter = new CustomListAdapter(this, ListSharingClass.archiveList);
        todoListView.setAdapter(listAdapter); //We want to view the To-Do List, not the archive initially.
        listAdapter.notifyDataSetChanged();
        add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Add Note", Toast.LENGTH_SHORT).show();
				String inputString = userinput.getText().toString();
				ToDoItemObject newItem = new ToDoItemObject();
				newItem.todoText = inputString;
				newItem.isCompleted = false;
				newItem.currentDate = Calendar.getInstance();
				if (!inputString.equals("")) { //Make sure the text field is not empty before adding something.
					ListSharingClass.todoList.add(newItem);
					SaveAndLoad.INSTANCE.saveItems();
				}
				listAdapter.update();
				userinput.setText("");
			}
		});
        
        archive.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				ListView todoListView = (ListView) findViewById(R.id.listView);
				if (archiveFlag == true) {
					archive.setText("View To-Do");
					todoListView.setAdapter(archiveAdapter);
					archiveFlag = false;
				}
				else {
					archive.setText("View Archive");
					todoListView.setAdapter(listAdapter);
					archiveFlag = true;
				}
			}
        });
        emailAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String mega_array = "";
				
				//Adds all the todoList items to Mega Array
				for(int i=0; i< ListSharingClass.todoList.size(); i++){
					mega_array += "To-Do: " + ListSharingClass.todoList.get(i).todoText + "\n";
				}
				//Adds all the archiveList items to Mega Array
				for(int i=0; i< ListSharingClass.archiveList.size(); i++){
					mega_array += "Archived: " + ListSharingClass.archiveList.get(i).todoText + "\n";
				}
				Intent massEmailer = new Intent(android.content.Intent.ACTION_SEND);
         	    massEmailer.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"youremails@yourprovider.com"});		  
         	    massEmailer.putExtra(android.content.Intent.EXTRA_SUBJECT, "To-Do List Notification");
         	    massEmailer.putExtra(android.content.Intent.EXTRA_TEXT, mega_array);
         	    massEmailer.setType("message/rfc822");
         	    startActivity(android.content.Intent.createChooser(massEmailer, "Choose an Email client :"));
				
			}
        });
        summary.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent sumActivity = new Intent(v.getContext(), SummaryActivity.class);
				startActivity(sumActivity);
			}
        });
    }
  
}
