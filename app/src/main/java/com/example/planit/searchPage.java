package com.example.planit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class searchPage extends AppCompatActivity {
    private EditText taskName;
    private TextView IDTextView;
    private CheckBox updateCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        ActionBar AB = getSupportActionBar();
        AB.setTitle("My Planner");

        // changing the bar logo pictures
        AB.setDisplayShowHomeEnabled(true);
        AB.setDisplayUseLogoEnabled(true);
        AB.setLogo(R.drawable.round_menu_book_24);

        taskName = (EditText)findViewById(R.id.taskSearchEditText);
        IDTextView = (TextView) findViewById(R.id.isCompletedTextView);
        updateCheck = (CheckBox)findViewById(R.id.completeCheck);
        updateCheck.setEnabled(false);
    }



    // Creating the menu to be viewable
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // All images/logos are temporary right now :)
    // creating the menu to click through pages
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent myIntent;

        //adding the clickable menu by switch
        switch(id){
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

            //filter button changed to search button
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

    } // end of onOptionsSelectMenu

    // Finding the task
    public void findClick (View v) {
        String tempName = taskName.getText().toString();
        PlannerDBHandler handler = new PlannerDBHandler(this);

        // return the date when asked to find something
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd");
        Date myDate;
        String dueDate;
        Task task = handler.findTask(tempName);
        Boolean comp;
        Boolean test;



        if (task != null) {
            //Date testDate = new Date();
            myDate = task.getTaskDueDate();
            dueDate = formatter.format(myDate);
            comp = task.getTask_isCompleted();
            String returnBool;

            updateCheck.setEnabled(true);

            if(comp){
                updateCheck.setChecked(true);
            }

            if(updateCheck.isChecked()){
                handler.updateCompletion(tempName, true);
                comp = true;
            }

            //String todayStr = formatter.format(dueDate);

            if(comp == true){
                returnBool = "! This task is finished!";
            } else{
                returnBool = "! Not yet completed";
            }

            //Toast.makeText(this, "Due Date: " + myDate, Toast.LENGTH_LONG).show();
            IDTextView.setText("This is due " + dueDate + returnBool);


        } else{
            IDTextView.setText("Task not found");
            taskName.setText("");
        }
    }

    // deleting the task
    public void deleteClick (View v){

        String tempName = taskName.getText().toString();
        PlannerDBHandler handler = new PlannerDBHandler(this);

        boolean result = handler.deleteTask(tempName);

        // if deleted it does this
        if (result){
            taskName.setText("");
            IDTextView.setText( tempName + " was deleted!");
            //handler.dailyList.remove(taskName);
        } else {
            IDTextView.setText("No match found");
        }
    }


}