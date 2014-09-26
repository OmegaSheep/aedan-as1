package com.example.aedan_as1;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public enum SaveAndLoad {
	INSTANCE;
	  
    private static final String ToDoItemFile = "appfile.sav";
    private static final String ArchiveFile = "appfile2.sav";
    private static Gson gson = new Gson();
    private static Context megaContext;
    public void loadContext(Context ctx) {
    	megaContext = ctx;
    }
    public void saveItems() {
    	try {
    		FileUtils.write((new File(megaContext.getFilesDir(),ToDoItemFile)), gson.toJson(ListSharingClass.todoList));
    		FileUtils.write((new File(megaContext.getFilesDir(),ArchiveFile)), gson.toJson(ListSharingClass.archiveList));
			Log.i("Persistence", "Saved: All Items");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void loadList() {
    	
		try {
			//To-Do List
			String line = FileUtils.readFileToString(new File(megaContext.getFilesDir(),ToDoItemFile));
			Type collectionType = new TypeToken<Collection<ToDoItemObject>>() {}.getType();
			ListSharingClass.todoList = gson.fromJson(line, (java.lang.reflect.Type) collectionType);
			//Archive List
			String line1 = FileUtils.readFileToString(new File(megaContext.getFilesDir(),ArchiveFile));
			ListSharingClass.archiveList = gson.fromJson(line1, (java.lang.reflect.Type) collectionType);
		} catch (Exception e) {
			Log.i("To-Do List", "Error loading To-Do List");
			e.printStackTrace();
			ListSharingClass.archiveList = new ArrayList<ToDoItemObject>();
			ListSharingClass.todoList = new ArrayList<ToDoItemObject>();
		}
		
    }
	
}
