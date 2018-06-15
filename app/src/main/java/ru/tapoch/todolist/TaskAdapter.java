package ru.tapoch.todolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Влад on 15.06.2018.
 */

public class TaskAdapter extends ArrayAdapter<Task>{

    private ArrayList<Task> objects;

    public TaskAdapter(Context context, int textViewResourceId, ArrayList<Task> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_row, null);
        }
        Task task = objects.get(position);
        if(task!=null){
            TextView taskName = v.findViewById(R.id.task_mame);
            TextView taskDesc = v.findViewById(R.id.task_desc);
            TextView taskCount = v.findViewById(R.id.task_count);

            taskName.setText(task.getName());
            taskDesc.setText(task.getDescription());
            taskCount.setText(String.valueOf(task.getCounts()));

        }

        return v;
    }
}
