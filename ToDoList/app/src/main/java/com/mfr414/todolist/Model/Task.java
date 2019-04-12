package com.mfr414.todolist.Model;

public class Task {
    public static final String TABLE_NAME = "task";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TASK = "task";

    private int id;
    private String task;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TASK + " TEXT"
                    + ")";

    public Task() {
    }

    public Task(int id, String task) {
        this.id = id;
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}