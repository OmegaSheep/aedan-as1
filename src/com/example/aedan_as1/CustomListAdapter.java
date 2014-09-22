package com.example.aedan_as1;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CustomListAdapter extends ArrayAdapter<ToDoItemObject>{
		private LayoutInflater inflater;
		private Context context;
		public CustomListAdapter(Context context, ArrayList<ToDoItemObject> list) {
			// TODO Auto-generated constructor stub
		super(context, R.layout.list_view, list); //
		this.context = context; //Prevents self-assignment and makes context essentially accessible to 
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Service required for Inflation
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				view = inflater.inflate(R.layout.list_view, parent, false); //False = magic
				//parent: just passing the root to the inflater
			}
			// Put code to show to-do object here.
			CheckBox todoCheckBox = (CheckBox) view.findViewById(R.id.todoBox);
			todoCheckBox.setText(getItem(position).todoText); //getItem(position) grabs the item, and we set its parameter "todoText" to what we want the item to be.
			todoCheckBox.setChecked(getItem(position).isCompleted);
			UpdateItem listener = new UpdateItem(getItem(position));
			todoCheckBox.setOnCheckedChangeListener(listener); //Grabs the item itself rather than it's boolean.
			todoCheckBox.setOnLongClickListener(listener);
			return view;
		}
			
		private class UpdateItem implements OnCheckedChangeListener, OnLongClickListener {
			
			ToDoItemObject newItemObject;
			public UpdateItem(ToDoItemObject object) {
				newItemObject = object;
			}
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				newItemObject.isCompleted = isChecked;
			}

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				//produce crazy ass menus
				String someDelete = "Delete Selected";
				String someArchive;
				String someEmail = "E-mail Selected";
				String someTitle = "What do you wish to do?";
				if (!newItemObject.isArchived) {
					someArchive = "ArchiveAny Selected";
				}
				else {
					someArchive = "Un-Archive Selected";
				}
				
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle(someTitle).setPositiveButton(someDelete, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                     remove(newItemObject);
	                   }
	               }).setNegativeButton(someArchive, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User wants to archive item
	                	   //add(newItemObject);
	                	   if (newItemObject.isArchived) {
	                		   newItemObject.isArchived = false;
	                	   }
	                	   else {
	                		   newItemObject.isArchived = true;
	                	   }
	                   }
	               }).setNeutralButton(someEmail, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User wants to email item
	                	   Intent emailer = new Intent(Intent.ACTION_SEND);
	                	   emailer.putExtra(Intent.EXTRA_EMAIL, new String[]{"youremails@yourprovider.com"});		  
	                	   emailer.putExtra(Intent.EXTRA_SUBJECT, "To-Do List Notification");
	                	   emailer.putExtra(Intent.EXTRA_TEXT, newItemObject.todoText);
	                	   emailer.setType("message/rfc822");
	                	   context.startActivity(Intent.createChooser(emailer, "Choose an Email client :"));
	                   }
	               });
				
				AlertDialog dialog = builder.create();
				dialog.show();
				return true;
				// TODO Auto-generated method stub
			}
		}
		
}
