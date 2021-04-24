package com.example.planit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class pastPlanners extends AppCompatActivity {

    // add tasks to a array and then add them drop down in the menu
    // look up how to do that because idk what a spinner is/ how to use it
    // helpful link 
    // https://www.tutorialspoint.com/how-can-i-add-items-to-a-spinner-in-android




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_planners);

        PlannerDBHandler handler = new PlannerDBHandler(this);

        //creating spinner/dropdown menu
        Spinner incompleteSpinner = (Spinner)findViewById(R.id.incompleteSpinner);
        Spinner completedSpinner = (Spinner)findViewById(R.id.completeSpinner);


        //test drop down menu arrayList
        // maybe making 2 drop downs, completed and incompleted
        //ArrayList<String> incompletedList = handler.incompletedTasks();
        //ArrayList<String> completedList = handler.completedTasks();
        ArrayList<String> tmpList = handler.getTaskNames();


        // crashes app -_-
        /*
        for (int i = taskArrayList.taskList.size(); i < 0; i++){
            String curTaskName = myTasks.get(i).getTaskName();
            if (curTaskName != null){
                testList.add(curTaskName);
            }
        }
        */

        // creating and adding to the spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, tmpList);
        ArrayAdapter<String> arrayAdapterCompleted = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tmpList);

        //setting the drop down menu to be the items in the list
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incompleteSpinner.setAdapter(arrayAdapter);

        // for completed list
        arrayAdapterCompleted.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        completedSpinner.setAdapter(arrayAdapterCompleted);
    }

    // Creating the menu on the past planners page to be viewable
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
                //Toast.makeText(this, "Filter", Toast.LENGTH_SHORT).show();
                myIntent = new Intent(this, searchPage.class);
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
    }



}