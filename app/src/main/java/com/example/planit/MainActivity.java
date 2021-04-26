package com.example.planit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements TaskRecyclerViewAdapter.ItemClickListener{
    private static final String TAG = "MainActivity";
    // intent flags
    private static final int flag = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
    private static final boolean USE_FLAG = true;
    public ArrayList<Task> dailyList;
    public String[] taskNameList;


    // have to add images a certain way
    // set densitys too!
    //link to do it
    //https://developer.android.com/studio/write/resource-manager

    /*
    Updated design doc: 10 pts :)
    Comments: 10 pts :)
    Still working on spinner: 15 pts :)
    Polishing things (layout, …): 15 pts :)
    SMS reminders: 25 pts :)
    Add send sms summary when creating an item :)
    Figuring out how to set alarm with OS to wake up and send SMS
    Add more data for demo: 25 pts :)
     */


    // recycler view
    TaskRecyclerViewAdapter myAdapter;
    CheckBox checkBoxes;

    private CheckBox taskBox;
    // creating adapter and layoutview
    public ArrayList<String> myList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //changing the top bar name
        ActionBar AB = getSupportActionBar();
        AB.setTitle("My Planner");

        // changing the bar logo pictures
        AB.setDisplayShowHomeEnabled(true);
        AB.setDisplayUseLogoEnabled(true);
        AB.setLogo(R.mipmap.ic_logo_round);

        checkBoxes = (CheckBox)findViewById(R.id.taskCheckBox);


        PlannerDBHandler handler = new PlannerDBHandler(this);

        // grabbing the arraylist from the DB

        //ArrayList<String> myList = handler.currentDayTasks();


        myList = handler.getTaskNames();
        // moving the array list to a String[] list so it is easier to view the tasks in recy view
        // this works
        String [] tmpList = new String[myList.size()];
        for (int i = 0; i < myList.size(); i++){
            tmpList[i] = myList.get(i);
        }

        //recycler View
        RecyclerView rView = (RecyclerView) findViewById(R.id.recyclerView);
        rView.setLayoutManager(new LinearLayoutManager(this));
        //making recycler view show the data
        myAdapter = new TaskRecyclerViewAdapter(tmpList);
        myAdapter.setClickListener(this);
        rView.setAdapter(myAdapter);




    } // end of onCreate


    // Creating the menu to be viewable
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    } // end of onCreateOptionsMenu
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

    public void onItemClick(View view, int position){
        Toast.makeText(this, "You clicked " + myAdapter.getItem(position), Toast.LENGTH_LONG).show();
    }

    //if checkbox is clicked
    public void checkBoxClick(View v){
        if(checkBoxes.isChecked()){
            //get the task and make the boolean = true for completed
        }
        // else it stays unclicked
    }


    //intent flag
    public void onAddtoMain(View v){
        //creating the intent
        Intent myIntent = new Intent(this, MainActivity.class);
        if(USE_FLAG){
            myIntent.addFlags(flag);
        }

        startActivity(myIntent);
    }



}// end of main activity