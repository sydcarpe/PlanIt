package com.example.planit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
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

        //creating spinner/dropdown menu
        Spinner mySpinner = (Spinner)findViewById(R.id.dateDropDown);


        //test drop down menu arrayList
        ArrayList<String> testList = new ArrayList<String>(5);

        testList.add("test1");
        testList.add("test2");
        testList.add("test3");
        testList.add("test4");
        testList.add("test5");

        // creating and adding to the spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, testList);

        //setting the drop down menu to be the items in the list
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(arrayAdapter);
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
                Toast.makeText(this, "Filter", Toast.LENGTH_SHORT).show();
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