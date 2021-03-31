package com.example.planit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlannerDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // creating table and columns
    private static final String DATABASE_NAME = "taskDB.db";
    private static final String TABLE_TASKS = "Tasks";
    private static final String COLUMN_TASKID = "task_id"; // task id, primary key
    private static final String COLUMN_TASKNAME = "task_name"; // user inputed name of task
    private static final String COLUMN_TASKTYPE = "task_type"; // checked box input
    private static final String COLUMN_TASKDATE = "task_date"; // date that the task must be completed
    private static final String COLUMN_TASKCOMPLETED = "task_isCompleted"; // boolean if task is completed or not


    public PlannerDBHandler (Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public ArrayList<String> taskNameList;


    // creating the table
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TASK_TABLE = "CREATE TABLE " +
                TABLE_TASKS + "(" +
                COLUMN_TASKID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASKNAME + " TASK, " +
                COLUMN_TASKTYPE + " STRING, "+
                COLUMN_TASKDATE + " STRING, " +
                COLUMN_TASKCOMPLETED + " BOOLEAN)";
        db.execSQL(CREATE_TASK_TABLE);

        // creating the arraylist
        taskNameList = new ArrayList<>();
    }


    // checking if table exists
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    //adding tasks
    public void addTask(Task task){
        ContentValues myValues = new ContentValues();
        //Date tempDate =task.getTaskDueDate();
       // String dateStr = tempDate.toString();

        myValues.put(COLUMN_TASKNAME, task.getTaskName());
        myValues.put(COLUMN_TASKTYPE, task.getTaskType());
        myValues.put(COLUMN_TASKDATE, task.getTaskDueDate());
       myValues.put(COLUMN_TASKCOMPLETED, task.getTask_isCompleted());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TASKS, null, myValues);
        db.close();
    }

    // add task to array list to have all the names in the array
    /*
    public void addToTaskList(Task task){
        ContentValues myValues = new ContentValues();
        myValues.get(task.getTaskName());

        taskNameList.add(task.getTaskName());
    }
    */


    // finding the task
    public Task findTask(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_TASKS +
                " WHERE " + COLUMN_TASKNAME + "=\"" +
                name + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor myCursor = db.rawQuery(sqlQuery, null);

        Task myTask = null;

        if(myCursor.moveToFirst()){
            int tempID = myCursor.getInt(0);
            String tempName = myCursor.getString(1); // getting the task name
            String tempType = myCursor.getString(2); // getting the type of task it is
            // get boolean isCompleted? Maybe?

            myCursor.close();
            myTask = new Task(tempID, tempName, tempType);
        }

        db.close();
        return myTask;
    }



    // deleting the task
    public boolean deleteTask (String name){
        boolean result = false;

        String sqlQuery = "SELECT * FROM " + TABLE_TASKS +
                " WHERE " + COLUMN_TASKNAME + " =\"" +
                name + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor myCursor = db.rawQuery(sqlQuery, null);

        if (myCursor.moveToFirst()){
            int tempID = myCursor.getInt(0);

            String where = COLUMN_TASKID + "=?";
            String[] whereArgs = {String.valueOf(tempID)};
            db.delete(TABLE_TASKS, where, whereArgs);

            myCursor.close();
            result = true;
        }

        db.close();
        return result;
    }



} // end of PlannerDBHandler
