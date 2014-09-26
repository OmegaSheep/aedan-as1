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
		super(context, R.layout.list_view, list); //
		this.context = context; //Prevents self-assignment and makes context accessible
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Service required for Inflation
		}
		
		Handler handle = new Handler(Looper.getMainLooper()); //This helps the GUI stay updated.
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				view = inflater.inflate(R.layout.list_view, parent, false); //False, ensures it is the root of the inflated XML that gets returned.
			}
			
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
				//Check or uncheck items.
				newItemObject.isCompleted = isChecked;
				SaveAndLoad.INSTANCE.saveItems();
			};

			@Override
			public boolean onLongClick(View v) {

				String someDelete = "Delete Selected";
				String someArchive; //Defined in if-else statement below.
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
					//Positive button is for deleting items.
	                   public void onClick(DialogInterface dialog, int id) {
	                	   remove(newItemObject);
	                	   SaveAndLoad.INSTANCE.saveItems();
	                   }
	                //Negative button is for archiving items.
	               }).setNegativeButton(someArchive, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   boolean x = newItemObject.isCompleted;
	                	   if (newItemObject.isArchived) {
	                		   newItemObject.isArchived = false;
	                		   ListSharingClass.archiveList.remove(newItemObject);
	                		   ListSharingClass.todoList.add(newItemObject);
	                		   ListSharingClass.todoList.get(ListSharingClass.todoList.size() - 1).isCompleted = x; //Grabs what is certain to be the item you just passed and updates it's check value.
	                	   }			
	                	   else {
	                		   newItemObject.isArchived = true;
	                		   ListSharingClass.todoList.remove(newItemObject);
	                		   ListSharingClass.archiveList.add(newItemObject);
	                		   ListSharingClass.archiveList.get(ListSharingClass.archiveList.size() - 1).isCompleted = x; //Grabs what is certain to be the item you just passed and updates it's check value.
	                	   }
	                	   SaveAndLoad.INSTANCE.saveItems();
	                	   handle.post(CustomListAdapter.this); //Ensures GUI is updated.
	                   }
	                 //Neutral button is for E-Mailing specific items.
	               }).setNeutralButton(someEmail, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                   	   //Code Snippet referenced from Stackoverflow in README. Posted by fiXedd.
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
			}
			
		}
		
		@Override
		//Didn't end up using this much. update() seems to work better.
		public void run() {
			this.notifyDataSetChanged();
		}
		
		public void update() {
			handle.post(this);
		}
		
}
