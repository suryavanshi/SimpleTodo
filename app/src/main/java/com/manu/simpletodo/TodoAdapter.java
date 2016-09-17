package com.manu.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Manu on 9/16/2016.
 */
public class TodoAdapter extends ArrayAdapter<TodoTask> {

    public TodoAdapter(Context context, ArrayList<TodoTask> todoTasks) {
        super(context, 0, todoTasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TodoTask todoTask = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_task, parent, false);
        }
        // Lookup view for data population
        TextView taskName = (TextView) convertView.findViewById(R.id.taskName);
        TextView taskDate = (TextView) convertView.findViewById(R.id.taskDate);
        // Populate the data into the template view using the data object
        taskName.setText(todoTask.taskName);
        taskDate.setText(todoTask.endDate);
        // Return the completed view to render on screen
        return convertView;
    }
}
