package com.example.skp101.todolist;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> taskListArray;
    private ArrayAdapter<String> taskListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskListArray = new ArrayList<String>();
        taskListAdapter = new ArrayAdapter<String>(this, R.layout.custom_layout, taskListArray);
        final ListView taskListView = (ListView) findViewById(R.id.taskListView);
        taskListView.setAdapter(taskListAdapter);


        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(taskListArray.size() > 0) {
                    taskListAdapter.remove(taskListView.getItemAtPosition(i).toString());
                    saveList();
                    Toast.makeText(getApplicationContext(), "Task Removed",Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        loadList();
    }

    public void addButtonClick(View view) {
        EditText Item = (EditText) findViewById(R.id.addTask);
        EditText Description = (EditText) findViewById(R.id.addDescription);

        String toDoItem = (Item).getText().toString().trim();
        String toDoDescription = (Description).getText().toString().trim();

        String task = toDoItem + "\n" + toDoDescription;


        if (toDoItem.isEmpty()) {
            return;
        }


        taskListAdapter.add(task);

        Item.setText("");
        Description.setText("");
        saveList();
        Toast.makeText(getApplicationContext(), "Task Added",Toast.LENGTH_LONG).show();

    }

    public void saveList(){

        try {
            PrintWriter pw = new PrintWriter(openFileOutput("ToDoList.txt", Context.MODE_PRIVATE));

            for (String toDo : taskListArray) {
                Log.i("SAVING LIST", toDo);
                pw.println(toDo);
            }

            pw.close();
        }catch(Exception e){
            Log.i("SAVING LIST",e.getMessage());
        }

    }

    public void loadList(){

        try{
            Log.i("ON LOAD","List was loaded");
            Scanner scanner = new Scanner(openFileInput("ToDoList.txt"));

            while(scanner.hasNextLine()){
                String toDo = scanner.nextLine();
                if(toDo.length() > 0) {
                    taskListAdapter.add(toDo);
                }
            }

        }catch(Exception e){
            Log.i("ON LOAD", e.getMessage());
        }

    }

}