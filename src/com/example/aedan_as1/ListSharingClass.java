package com.example.aedan_as1;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

import android.app.Application;
import android.content.Context;
import android.renderscript.Type;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
public class ListSharingClass extends Application{
	public static ArrayList<ToDoItemObject> todoList = new ArrayList<ToDoItemObject>();
    public static ArrayList<ToDoItemObject> archiveList = new ArrayList<ToDoItemObject>();
    
    private static final String FILENAME = "appfile.sav";
    private static Gson gson = new Gson();
    public static void saveItems(ArrayList<ToDoItemObject> saveList, Context ctx) {
    	try {
			FileOutputStream fos = new FileOutputStream(new File(ctx.getFilesDir(),FILENAME));
			
			String jsonTweetList = gson.toJson(saveList);
			fos.write(jsonTweetList.getBytes());
			fos.close();
		
			Log.i("Persistence", "Saved: All Items");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static ArrayList<ToDoItemObject> loadList(ArrayList<ToDoItemObject> loadList, Context ctx) {
    	ArrayList<ToDoItemObject> lts = new ArrayList<ToDoItemObject>();

		try {
			
			String line = FileUtils.readFileToString(new File(ctx.getFilesDir(),FILENAME));

			Type collectionType = (Type) new TypeToken<Collection<ToDoItemObject>>() {}.getType();
			lts = gson.fromJson(line, (java.lang.reflect.Type) collectionType);
			Log.i("To-Do List", lts.toString());

		} catch (Exception e) {
			Log.i("To-Do List", "Error loading To-Do List");
			e.printStackTrace();
		}

		return lts;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static int calculateChecked(ArrayList<ToDoItemObject> lst) {
    	int totalChecked = 0;
    	for(int i=0; i< lst.size(); i++){
    		if (lst.get(i).isCompleted == true) {
    			totalChecked++;
    		}
		}
		return totalChecked;
    }
    public static int calculateUnchecked(ArrayList<ToDoItemObject> lst) {
    	int totalUnchecked = 0;
    	for(int i=0; i< lst.size(); i++){
    		if (lst.get(i).isCompleted == false) {
    			totalUnchecked++;
    		}
		}
		return totalUnchecked;
    }
    public static int calculateTotal(ArrayList<ToDoItemObject> lst) {
    	return lst.size();
    }
 
}
