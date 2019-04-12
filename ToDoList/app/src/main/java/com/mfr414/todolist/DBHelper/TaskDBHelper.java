package com.mfr414.todolist.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mfr414.todolist.Model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDBHelper extends SQLiteOpenHelper {

    //init static variable for DB_NAME AND DB_VER
    public static final String DB_NAME = "TodoDB";
    private static final int DB_VER = 1;


    public TaskDBHelper(Context context) {
        super(context, DB_NAME,null,DB_VER);
    }

    //Creating Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Task.CREATE_TABLE);
    }

    //Upgrading Database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop older Table
        db.execSQL("DROP TABLE IF EXISTS " + Task.TABLE_NAME);
        //create table again
        onCreate(db);
    }

    //Inserting Task to Database
    public long insertTask(String inserttask) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically.
        // no need to add them
        values.put(Task.COLUMN_TASK, inserttask);

        // insert row
        long id = db.insert(Task.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    //getting task by id from database
    public Task getTask(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Task.TABLE_NAME,
                new String[]{Task.COLUMN_ID, Task.COLUMN_TASK},
                Task.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Task task = new Task(
                cursor.getInt(cursor.getColumnIndex(Task.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Task.COLUMN_TASK)));

        // close the db connection
        cursor.close();

        return task;
    }

    //Get All Task from database
    public List<Task> getAllTask() {
        List<Task> allTask = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Task.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task itemtask = new Task();
                itemtask.setId(cursor.getInt(cursor.getColumnIndex(Task.COLUMN_ID)));
                itemtask.setTask(cursor.getString(cursor.getColumnIndex(Task.COLUMN_TASK)));
                allTask.add(itemtask);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return allTask;
    }

    //Delete task from database
    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Task.TABLE_NAME, Task.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
