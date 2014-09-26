package com.example.aedan_as1;

import java.util.ArrayList;
import android.app.Application;
public class ListSharingClass extends Application{
	public static ArrayList<ToDoItemObject> todoList = new ArrayList<ToDoItemObject>();
    public static ArrayList<ToDoItemObject> archiveList = new ArrayList<ToDoItemObject>();
    
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
