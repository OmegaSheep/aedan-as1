package com.example.aedan_as1;

import java.util.Calendar;

public class ToDoItemObject {
	String todoText;
	boolean isCompleted = false; //Default is false so that items added are always unchecked.
	boolean isArchived = false;
	boolean isSelected = false;
	Calendar currentDate; //Probably not necessary, but might be a cool feature.
	
}
