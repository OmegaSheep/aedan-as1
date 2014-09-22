package com.example.aedan_as1;

import java.util.ArrayList;

import android.app.Application;

public class ListSharingClass extends Application{
	public static ArrayList<ToDoItemObject> todoList = new ArrayList<ToDoItemObject>();
    public static ArrayList<ToDoItemObject> archiveList = new ArrayList<ToDoItemObject>();
}
