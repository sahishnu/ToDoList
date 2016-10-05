package com.example.skp101.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.skp101.todolist.R;
import com.example.skp101.todolist.TaskObject;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<TaskObject> {

    public Context context;
    public ArrayList<TaskObject> taskObjectArrayList;

    public CustomAdapter(Context context, ArrayList<TaskObject> taskObjectArrayList) {
        super(context,0,taskObjectArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TaskObject task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout,parent,false);
        }

        TextView list_entry_task = (TextView)convertView.findViewById(R.id.list_entry_task);
        TextView list_entry_description =(TextView)convertView.findViewById(R.id.list_entry_description);

        list_entry_task.setText(task.getTitle());
        list_entry_description.setText(task.getDescription());

        return convertView;
    }
}