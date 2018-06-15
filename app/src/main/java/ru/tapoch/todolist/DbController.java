package ru.tapoch.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Влад on 15.06.2018.
 */

public class DbController extends SQLiteOpenHelper {

    private static final String DB_NAME = "ToDoList";
    public static final String DB_TABLE = "Task";
    public static final int DB_VER = 1;
    public static final String DB_COLUMN_NAME = "TaskName";
    public static final String  DB_COLUMN_DESC = "TaskDescription";
    public static final String DB_COLUMN_COUNT = "TaskCounts";

    public DbController(Context context){
        super(context, DB_NAME, null, DB_VER);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (" +
                "Id integer primary key autoincrement, " +
                "%s text not null, " +
                "%s text, " +
                "%s int default 0);",
                DB_TABLE, DB_COLUMN_NAME, DB_COLUMN_DESC, DB_COLUMN_COUNT);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s",DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }
    public void insertNewTask(String taskName, String taskDesc){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN_NAME,taskName);
        if(!taskDesc.isEmpty())
            values.put(DB_COLUMN_DESC, taskDesc);
        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }
    public void deleteTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN_NAME + " = ?",new String[]{task});
        db.close();
    }

    public ArrayList<Task> getTaskList(){
        ArrayList<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, null,null,null,null,null,null);
        while(cursor.moveToNext()){
            String taskName = cursor.getString(cursor.getColumnIndex(DB_COLUMN_NAME));
            String taskDesc = cursor.getString(cursor.getColumnIndex(DB_COLUMN_DESC));
            int taskCount = cursor.getInt(cursor.getColumnIndex(DB_COLUMN_COUNT));
            taskList.add(new Task(taskName, taskDesc, taskCount));
        }
        cursor.close();
        db.close();
        return taskList;
    }
    public void updateTask(Task task){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DB_COLUMN_COUNT, task.getCounts());
        db.update(DB_TABLE, cv, "TaskName = ?", new String[]{task.getName()});
        db.close();
    }
}
