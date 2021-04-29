package com.example.planit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    //creating a new arrayList to be accessed by all
    public ArrayList<String> myTasks = new ArrayList<>();


    public PlannerDBHandler (Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }




    // creating the table
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TASK_TABLE = "CREATE TABLE " +
                TABLE_TASKS + "(" +
                COLUMN_TASKID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASKNAME + " TASK, " +
                COLUMN_TASKTYPE + " STRING, "+
                COLUMN_TASKDATE + " DATE, " +
                COLUMN_TASKCOMPLETED + " BOOLEAN)";
        db.execSQL(CREATE_TASK_TABLE);


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
        // saving the date as a string
        Date tempDate =task.getTaskDueDate();
        String dateStr = tempDate.toString();

        myValues.put(COLUMN_TASKNAME, task.getTaskName());
        myValues.put(COLUMN_TASKTYPE, task.getTaskType());
        myValues.put(COLUMN_TASKDATE, dateStr);
        myValues.put(COLUMN_TASKCOMPLETED, task.getTask_isCompleted());


        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TASKS, null, myValues);
        db.close();
    }



    // getting task names with SQL

    public ArrayList<Task> getTaskNames(){
        // selecting from task table SQL
        String sqlQuery = "SELECT * FROM "  + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor myCursor = db.rawQuery(sqlQuery, null);
        ArrayList<Task> taskNameArray = new ArrayList<>();
        myCursor.moveToPosition(0);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        // if the cursor is empty, it will not crash the app because it won't continuously search for something
        if(!myCursor.moveToFirst()){
            myCursor.close();
            // just returns the empty array
            return taskNameArray;
        }

        // while cursor isn't closed get name and save it to a list
        // use moveToNext()
        // if its the last one then close it and close the while loop
        while(!myCursor.isClosed()){
            //getting the current name and adding to list
            String tmpName = myCursor.getString(1);
            String tmpType = myCursor.getString(2);
            // String to Date here
            String tmpDate = myCursor.getString(3);
            Date myDate = new Date();
            try {
                myDate = formatter.parse(tmpDate);
            } catch (Exception e){
                e.printStackTrace();
            }
            //String to boolean is completed here
            String tmpCompleted = myCursor.getString(4);
            Boolean isCompleted;
            // changing tmpCompleted to boolean
            if (tmpCompleted == "0"){
                isCompleted = false;
            } else {
                isCompleted = true;
            }

            // adding task to Array
            Task curTask = new Task(tmpName, tmpType, myDate, isCompleted);
            taskNameArray.add(curTask);

            // moving on to the next and repeating loop
            myCursor.moveToNext();
            // if cursor is after last, end
            if (myCursor.isAfterLast()){
                myCursor.close();
            }
        }
        return taskNameArray;
    }

    // organize the tasks by current date here
    public ArrayList<String> currentDayTasks(){
        //sql is "SELECT * FROM TABLE_TASKS WHERE CURDATE() >= task_date
        // want to get past things that aren't completed to show up
        String sqlQuery = "SELECT * FROM "  + TABLE_TASKS +
                " WHERE CURDATE() >= " + COLUMN_TASKDATE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor myCursor = db.rawQuery(sqlQuery, null);
        ArrayList<String> currentDay = new ArrayList<>();

        // while cursor isn't closed get name and save it to a list
        // use moveToNext()
        // if its the last one then close it and close the while loop

        myCursor.moveToPosition(0);
        while(!myCursor.isClosed()){
            //getting the current name and adding to list
            String tmpName = myCursor.getString(1);
            currentDay.add(tmpName);

            // moving on to the next and repeating loop
            myCursor.moveToNext();
            // if cursor is after last, end
            if (myCursor.isAfterLast()){
                myCursor.close();
            }
        }
        return currentDay;
    }

    // getting the tasks that are completed
    public ArrayList<String> completedTasks(){
        //want to get where task_completed is 1
        String sqlQuery = "SELECT * FROM "  + TABLE_TASKS +
                " WHERE " + COLUMN_TASKCOMPLETED + "=\"" + 1 + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor myCursor = db.rawQuery(sqlQuery, null);
        ArrayList<String> completedTaskArray = new ArrayList<>();

        // while cursor isn't closed get name and save it to a list
        // use moveToNext()
        // if its the last one then close it and close the while loop

        myCursor.moveToPosition(0);
        while(!myCursor.isClosed()){
            //getting the current name and adding to list
            String tmpName = myCursor.getString(1);
            completedTaskArray.add(tmpName);

            // moving on to the next and repeating loop
            myCursor.moveToNext();
            // if cursor is after last, end
            if (myCursor.isAfterLast()){
                myCursor.close();
            }
        }
        return completedTaskArray;
    }


    //getting the incomplete lists of tasks to show
    public ArrayList<String> incompletedTasks(){
        //want to get where task_completed is 0 (false)
        String sqlQuery = "SELECT * FROM "  + TABLE_TASKS +
                " WHERE " + COLUMN_TASKCOMPLETED + "=\"" + 0 + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor myCursor = db.rawQuery(sqlQuery, null);
        ArrayList<String> incompletedTaskArray = new ArrayList<>();

        // while cursor isn't closed get name and save it to a list
        // use moveToNext()
        // if its the last one then close it and close the while loop

        myCursor.moveToPosition(0);
        while(!myCursor.isClosed()){
            //getting the current name and adding to list
            String tmpName = myCursor.getString(1);
            incompletedTaskArray.add(tmpName);

            // moving on to the next and repeating loop
            myCursor.moveToNext();
            // if cursor is after last, end
            if (myCursor.isAfterLast()){
                myCursor.close();
            }
        }
        return incompletedTaskArray;
    }

    // updating the Boolean to be 1 and not 0
    public void updateCompletion(String name){

        String sqlQuery = "UPDATE " + TABLE_TASKS +
                " SET " + COLUMN_TASKCOMPLETED + " = '1' " +
                " WHERE " + COLUMN_TASKNAME +"=\"" + name + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor myCursor = db.rawQuery(sqlQuery, null);
        ContentValues cv = new ContentValues();

        // getting the boolean and changing the database
        if (myCursor.moveToFirst()){
            String tempID = myCursor.getString(0);
           // db.update(TABLE_TASKS, cv, null, new String[]{"1"});

        }
    }




    // finding the task
    public Task findTask(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_TASKS +
                " WHERE " + COLUMN_TASKNAME + "=\"" +
                name + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor myCursor = db.rawQuery(sqlQuery, null);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");

        Task myTask = null;

        if(myCursor.moveToFirst()){
            /*
            Date myDate = new Date();
            String testDate = "13/02/2019";
            Date date = null;
            String testIdk = formatter.format(myDate);
             */
            int tempID = myCursor.getInt(0);
            String tempName = myCursor.getString(1); // getting the task name
            String tempType = myCursor.getString(2); // getting the type of task it is
            String tempDate = myCursor.getString(3); // getting the date
            String tempBool = myCursor.getString(4); // getting the boolean
            Boolean completed;
            // get boolean isCompleted? Maybe?
            Date date = new Date();

            if(tempBool == "0"){
                completed = false;
            } else {
                completed = true;
            }

            // try and catch to change the date from string to date
            try {
                //myDate = formatter.parse(testDate);
                date = formatter.parse(tempDate);

            } catch (ParseException e){
                e.printStackTrace();
            }

            myCursor.close();
            myTask = new Task(tempID, tempName, tempType, date, completed);
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
