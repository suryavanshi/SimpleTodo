package com.manu.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TodoTask> items;
    TodoAdapter todoAdapter;
    private ListView lvItems;
    private final int EDIT_REQUEST_CODE = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ADD HERE
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = (ArrayList<TodoTask>)TodoTask.listAll(TodoTask.class);
        //items = new ArrayList<String>();
        //readItems();
        //itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        todoAdapter = new TodoAdapter(this,items);
        lvItems.setAdapter(todoAdapter);
        //items.add("First Item");
        //items.add("Second Item");
        setupListViewListener();
        setupListEditListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        TodoTask deletedtask = items.remove(pos);
                        deletedtask.delete();
                        // Refresh the adapter
                        todoAdapter.notifyDataSetChanged();
                        // Return true consumes the long click event (marks it handled)
                        //writeItems();
                        return true;
                    }

                });
    }

    private void setupListEditListener() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(parent.getContext(), EditItemActivity.class);
                i.putExtra("task", items.get(position).taskName);
                i.putExtra("pos", position);
                startActivityForResult(i, EDIT_REQUEST_CODE);
            }
        });
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        TodoTask newTask = new TodoTask(itemText, DateFormat.getDateInstance().format(new Date()));
        todoAdapter.add(newTask);
        newTask.save();
        etNewItem.setText("");
        //writeItems();
    }

    /*private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }*/

    /*private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            // Extract name value from result extras
            String tname = data.getExtras().getString("taskSav");
            int position = data.getIntExtra("posSav", 200);
            //TodoTask newTask = new TodoTask(tname, DateFormat.getDateInstance().format(new Date()));
            TodoTask editedTask = items.remove(position);
            editedTask.taskName = tname;
            editedTask.save();
            items.add(position, editedTask);
            todoAdapter.notifyDataSetChanged();
            //writeItems();

        }
    }

}
