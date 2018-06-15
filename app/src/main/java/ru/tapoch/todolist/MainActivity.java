package ru.tapoch.todolist;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DbController dbController;
    TaskAdapter adapter;
    ListView taskListView;
    ArrayList<Task> tasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbController = new DbController(this);

        taskListView = findViewById(R.id.lvTasks);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = adapter.getItem(position);
                task.addCount();
                dbController.updateTask(task);
                loadTaskList();
            }
        });

        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                tasks.remove(position);
                Task task = adapter.getItem(position);
                dbController.deleteTask(task.getName());
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Удалено", Toast.LENGTH_LONG).show();
                loadTaskList();
                return false;
            }
        });
        loadTaskList();



    }

    private void loadTaskList() {
        tasks = dbController.getTaskList();
        if(adapter==null){
            adapter = new TaskAdapter(this, R.layout.list_row, tasks);
            taskListView.setAdapter(adapter);
        }
        else{
            adapter.clear();
            adapter.addAll(tasks);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_task:
                final EditText editTextTaskName = new EditText(this);
                final EditText editTextTaskDesc = new EditText(this);
                editTextTaskDesc.setLines(3);

                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);

                editTextTaskName.setHint("Название");
                editTextTaskDesc.setHint("Описание");

                layout.addView(editTextTaskName);
                layout.addView(editTextTaskDesc);

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Создание задачи")
                        .setView(layout)
                        .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String taskName = String.valueOf(editTextTaskName.getText());
                                String taskDesc = String.valueOf(editTextTaskDesc.getText());
                                dbController.insertNewTask(taskName, taskDesc);
                                loadTaskList();
                            }
                        })
                        .setNegativeButton("Отмена",null)
                        .create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
