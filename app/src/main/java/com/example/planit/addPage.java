package com.example.planit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class addPage extends AppCompatActivity {
    //add button adds to the daily planner
    // cancel button goes to home page again
    // *** means to-do
    // I should use Array list bc i want to be able to use dups.

    // creating the views/edits/checkboxes
    private EditText taskEdit;
    private EditText dueDate;
    private CheckBox errandBox;
    private CheckBox homeworkBox;
    private CheckBox choreBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);


        taskEdit = (EditText)findViewById(R.id.taskEditText);
        dueDate = (EditText)findViewById(R.id.dueDateEditTextDate);
        errandBox = (CheckBox)findViewById(R.id.errandCheckBox);
        homeworkBox = (CheckBox)findViewById(R.id.homeworkCheckBox);
        choreBox = (CheckBox)findViewById(R.id.choreCheckBox);


    }


    // Creating the menu on the add page
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    // creating the menu to click through pages
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent myIntent;

        //adding the clickable menu
        switch (id) {
            //home button
            case R.id.actionHome:
                myIntent = new Intent(this, MainActivity.class);
                startActivity(myIntent);
                return true;
            //add button
            case R.id.actionAdd:
                myIntent = new Intent(this, addPage.class);
                startActivity(myIntent);
                return true;

            //filter button
            case R.id.actionFilter:
                myIntent = new Intent (this, searchPage.class);
                //Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                startActivity(myIntent);
                return true;

            //past planner button
            case R.id.actionPastPlan:

                myIntent = new Intent(this, pastPlanners.class);
                startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    } // end of on OptionsItemSelected


    // next 3 blocks are disabling the other checkboxes so you can only have one checked
    //if Errand button is clicked
    public void onErrandClick(View v){
        if (errandBox.isChecked()){
            homeworkBox.setEnabled(false);
            choreBox.setEnabled(false);
        }
        if (!errandBox.isChecked()){
            homeworkBox.setEnabled(true);
            choreBox.setEnabled(true);
        }
    }

    //if homework is clicked
    public void onHomeworkClick(View v){
        if (homeworkBox.isChecked()){
            errandBox.setEnabled(false);
            choreBox.setEnabled(false);
        }
        if (!homeworkBox.isChecked()){
            errandBox.setEnabled(true);
            choreBox.setEnabled(true);
        }
    }

    //if choreis clicked
    public void onChoreClick(View v){
        if (choreBox.isChecked()){
            homeworkBox.setEnabled(false);
            errandBox.setEnabled(false);
        }
        if (!choreBox.isChecked()){
            homeworkBox.setEnabled(true);
            errandBox.setEnabled(true);
        }
    }



    // making the add button move to add page
    // just removed parse exception
    public void addButtonOnClick(View v) {
        //adding the new tasks to the queue
        String taskName = taskEdit.getText().toString();
        String taskTypeStr;
        String taskDueDate = dueDate.getText().toString();

        // will ALWAYS be false while first added - not completed yet!
        Boolean isCompleted = false;


        // Creating the date format

        //Date date = new SimpleDateFormat("dd/MM/yyyy").parse(userDate);

        // check which box is checked
        if (errandBox.isChecked()){
            taskTypeStr = "Errand";
            //Toast.makeText(this, " Errand Box was Checked", Toast.LENGTH_SHORT).show();
        } else if (homeworkBox.isChecked()){
            taskTypeStr = "Homework";
            //Toast.makeText(this, "Homework Box was checked", Toast.LENGTH_SHORT).show();
        } else if (choreBox.isChecked()){
            taskTypeStr = "Chore";
            //Toast.makeText(this, "Chore Box was checked", Toast.LENGTH_SHORT).show();
        } else {
            taskTypeStr = "";
            //Toast.makeText(this, "No boxes were checked", Toast.LENGTH_SHORT).show();
        }

        // Saving the task

        // *** Add the date and stuff ***
        //Task task = new Task (taskName, taskTypeStr, date, isCompleted);
        Task task = new Task(taskName, taskTypeStr, taskDueDate, false);

        // creating the planner
        PlannerDBHandler handler = new PlannerDBHandler(this);
        handler.addTask(task);
        //handler.addToTaskList(task);


        Toast.makeText(this, taskName + " was added!" , Toast.LENGTH_SHORT).show();

        //creating the intent
        // I changed it so it will take back to main menu
        Intent myIntent = new Intent(this, MainActivity.class);
        //creating the new activity start up page AFTER everything else has already been done
        startActivity(myIntent);
    }

    // making the cancel button return to the main page
    public void cancelButtononClick(View v){
        Intent myIntent = new Intent (this, MainActivity.class);
        startActivity(myIntent);
    }


} // end of add page