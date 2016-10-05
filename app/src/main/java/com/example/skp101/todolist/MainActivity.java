package com.example.skp101.todolist;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {

    private ArrayList<TaskObject> taskListArray = new ArrayList<TaskObject>();
    private TaskObject taskObject;
    private ListView listView;
    private CustomAdapter customAdapter;
    public EditText Item;
    public EditText Description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customAdapter = new CustomAdapter(this, taskListArray);
        listView = (ListView) findViewById(R.id.taskListView);
        listView.setAdapter(customAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                customAdapter.remove(customAdapter.getItem(i));
                saveList();
                Toast.makeText(getApplicationContext(), "Task Removed",Toast.LENGTH_LONG).show();
                return true;
            }
        });

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        loadList();
    }

    public void addButtonClick(View view) {
        Item = (EditText) findViewById(R.id.addTask);
        Description = (EditText) findViewById(R.id.addDescription);

        String toDoItem = (Item).getText().toString().trim();
        String toDoDescription = (Description).getText().toString().trim();

        if(toDoItem.length() == 0 || toDoDescription.length() == 0){
            Toast.makeText(getApplicationContext(), "Please enter a task AND a description",Toast.LENGTH_LONG).show();
            return;
        }

        TaskObject newTask = new TaskObject();
        newTask.setTitle(toDoItem);
        newTask.setDescription(toDoDescription);
        customAdapter.add(newTask);

        Item.setText("");
        Description.setText("");
        saveList();

        Toast.makeText(getApplicationContext(), "Task Added",Toast.LENGTH_LONG).show();

    }

    public void saveList(){

        try {
            PrintWriter pw = new PrintWriter(openFileOutput("ToDoList.txt", Context.MODE_PRIVATE));

            for (TaskObject task : taskListArray) {
                pw.println(task.getTitle() + " " + task.getDescription());
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
                String[] toDoArray = toDo.split("\\s+");
                TaskObject task = new TaskObject();
                task.setTitle(toDoArray[0]);
                task.setDescription(toDoArray[1]);
                if(toDo.length() > 0) {
                    customAdapter.add(task);
                }
            }

        }catch(Exception e){
            Log.i("ON LOAD", e.getMessage());
        }

    }
}