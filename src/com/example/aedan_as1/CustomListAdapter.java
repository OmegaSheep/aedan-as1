package com.example.aedan_as1;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CustomListAdapter extends ArrayAdapter<ToDoItemObject> implements Runnable {
		private LayoutInflater inflater;
		private Context context;
		public CustomListAdapter(Context context, ArrayList<ToDoItemObject> list) {
			// TODO Auto-generated constructor stub
		super(context, R.layout.list_view, list); //
		this.context = context; //Prevents self-assignment and makes context essentially accessible to 
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Service required for Inflation
		}
		Handler handle = new Handler(Looper.getMainLooper());
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
			};

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				//produce crazy ass menus
				String someDelete = "Delete Selected";
				String someArchive;
				String someEmail = "E-mail Selected";
				String someTitle = "What do you wish to do?";
				if (!newItemObject.isArchived) {
					someArchive = "Archive Selected";
				}
				else {
					someArchive = "Un-Archive Selected";
				}
				
				AlertDialog.Builder longClickDialog = new AlertDialog.Builder(context, 2); //The "2" constant, changes the color scheme to dark.
				longClickDialog.setTitle(someTitle).setPositiveButton(someDelete, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   remove(newItemObject);
	                   }
	               }).setNegativeButton(someArchive, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User wants to archive item
	                	   boolean x = newItemObject.isCompleted;
	                	   if (newItemObject.isArchived) {
	                		   newItemObject.isArchived = false;
	                		   ListSharingClass.archiveList.remove(newItemObject);
	                		   ListSharingClass.todoList.add(newItemObject);

	                	   }			
	                	   else {
	                		   newItemObject.isArchived = true;
	                		   ListSharingClass.todoList.remove(newItemObject);
	                		   ListSharingClass.archiveList.add(newItemObject);
	                		   
	                	   }
	                	   
	                	   handle.post(CustomListAdapter.this);
	                	   newItemObject.isCompleted = x;
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
				
				AlertDialog dialog = longClickDialog.create();
				dialog.show();
				return true;
				// TODO Auto-generated method stub
			}
			
		}
		@Override
		public void run() {
			this.notifyDataSetChanged();
			// TODO Auto-generated method stub
			
		}
}
