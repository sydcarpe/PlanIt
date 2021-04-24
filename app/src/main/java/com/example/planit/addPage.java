package com.example.planit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    private EditText phoneNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);


        taskEdit = (EditText)findViewById(R.id.taskEditText);
        dueDate = (EditText)findViewById(R.id.dueDateEditTextDate);
        errandBox = (CheckBox)findViewById(R.id.errandCheckBox);
        homeworkBox = (CheckBox)findViewById(R.id.homeworkCheckBox);
        choreBox = (CheckBox)findViewById(R.id.choreCheckBox);
        phoneNumber = (EditText)findViewById(R.id.editTextPhone);

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
        //user inputed date
        String taskDueDate = dueDate.getText().toString();
        //creating the tempdate to put in the database, sets it equal to current date,
        // unless state otherwise
        // creating date format as well
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date tempDate = new Date();


        // check which box is checked
        // sets the type = to Errand, homework, or chore
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

        //try and catch for parse exception for the date
        try {
            tempDate = formatter.parse(taskDueDate);

        } catch (ParseException e){
            e.printStackTrace();
        }

        // Saving the task
        Task task = new Task(taskName, taskTypeStr, tempDate, false);



        // creating the planner
        PlannerDBHandler handler = new PlannerDBHandler(this);
        handler.addTask(task);
        //handler.addTaskToArray(task);
        handler.myTasks.add(task.getTaskName());


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


    // creating SMS button on click
    // will send the user a text of the task and due date
    public void onSendButton(View v){
        //getting date and task name to send to user
        String date = dueDate.getText().toString();
        String taskName = taskEdit.getText().toString();
        String phoneNum = phoneNumber.getText().toString();

        // creating automated message to send the date and name together at once
        String message = "New Task '" + taskName+ "' has been added! Due date is " + date ;

        //getting manager
        SmsManager smsManager = SmsManager.getDefault();

        // cant send if no phone number, so
        if (phoneNum.isEmpty()){
            Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_LONG).show();
        }

        //sending text
        smsManager.sendTextMessage(phoneNum, null, message, null, null);

        // oreo and above code
        int SMS_PERMISSION_REQ_CODE_SUBMIT =101;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(addPage.this, new String[]{Manifest.permission.RECEIVE_SMS},
                    SMS_PERMISSION_REQ_CODE_SUBMIT);
            ActivityCompat.requestPermissions(addPage.this, new String[] {Manifest.permission.SEND_SMS},
                    SMS_PERMISSION_REQ_CODE_SUBMIT);
        }
    }





} // end of add page