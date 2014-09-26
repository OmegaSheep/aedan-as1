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

public enum SaveAndLoad { //enum enforces "singletons" in Java which allows this same instance to be accessed anywhere instantiated.
	INSTANCE;
	  
    private static final String ToDoItemFile = "appfile.sav"; //We save our two lists to separate files to avoid any confusion on load.
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
		} catch (Exception e) {
			Log.i("notes", "Error saving To-Do List");
			e.printStackTrace();
		}
    }
    
    @SuppressWarnings("unchecked") //Eclipse suggested a weird type-cast which is causing warnings, but seems to work for my scenario.
	public void loadList() {
    	
		try {
			//To-Do List
			String listContent = FileUtils.readFileToString(new File(megaContext.getFilesDir(),ToDoItemFile));
			Type collectionType = new TypeToken<Collection<ToDoItemObject>>() {}.getType(); //API to get a generic type for feeding to GSON
			ListSharingClass.todoList.clear();
			//This '?' cast was suggested as a default by Eclipse after ruthlessly 
			//trying to debug and figure out what type to give it.
			ListSharingClass.todoList.addAll((Collection<? extends ToDoItemObject>) gson.fromJson(listContent, (java.lang.reflect.Type) collectionType));
			
			//Archive List
			String archiveContent = FileUtils.readFileToString(new File(megaContext.getFilesDir(),ArchiveFile));
			
			ListSharingClass.archiveList.clear();
			//This '?' cast was suggested as a default by Eclipse after ruthlessly 
			//trying to debug and figure out what type to give it.
			ListSharingClass.archiveList.addAll((Collection<? extends ToDoItemObject>) gson.fromJson(archiveContent, (java.lang.reflect.Type) collectionType));
			
		} catch (Exception e) {
			Log.i("notes", "Error loading To-Do List");
			e.printStackTrace();
			ListSharingClass.archiveList = new ArrayList<ToDoItemObject>(); //Initialize lists just in case to prevent a memory leak. Shouldn't ever happen really.
			ListSharingClass.todoList = new ArrayList<ToDoItemObject>(); //Initialize lists just in case to prevent a memory leak. Shouldn't ever happen really.
		}
		
    }
	
}
