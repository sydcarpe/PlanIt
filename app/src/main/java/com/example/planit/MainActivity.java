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

    // rubric for P4
    //Try/catch: 20 pts :)
    //Design doc: 10 pts :)
    //Update the Date data in the DB: 20 pts :)
    //Spinner: 10 pts
    //Display tasks on the home page: 15 pts
    //Display tasks on past screen: 15 pts
    //Intent Flags: 10 pts

    // recycler view
    TaskRecyclerViewAdapter myAdapter;

    private CheckBox taskBox;
    // creating adapter and layoutview



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //changing the top bar name
        ActionBar AB = getSupportActionBar();
        AB.setTitle("Planner");

        // changing the bar logo picture
        AB.setDisplayShowHomeEnabled(true);
        AB.setDisplayUseLogoEnabled(true);
        AB.setLogo(R.mipmap.ic_logo_round);
        dailyList = new ArrayList<>();


        // finding
        //taskBox = (CheckBox)findViewById(R.id.taskCheckBox);
        PlannerDBHandler handler = new PlannerDBHandler(this);
        // creating local var
        //ArrayList<Task> arrayList = handler.dailyList;
       // String[] taskNames = new String[arrayList.size()];

        //test
        String[] myList = new String[3];
        myList[0] = "assignment";
        myList[1] = "chore";
        myList[2] = "errand";



        DisplayTasks();
        //recycler View stuff
        RecyclerView rView = (RecyclerView) findViewById(R.id.recyclerView);
        rView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new TaskRecyclerViewAdapter(myList);
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


    // need to create a way to display all tasks on home page that is current date
    public void DisplayTasks(){
        PlannerDBHandler handler = new PlannerDBHandler(this);


        if (dailyList.isEmpty()){
            //do nothing?
        } else{
            // add tasks to home page first
            taskNameList = new String[dailyList.size()];
            for (int i = 0; i<dailyList.size(); i++){
                taskNameList[i] = dailyList.get(i).getTaskName();
            }
        }
    }


    public void onItemClick(View view, int position){
        Toast.makeText(this, "You clicked " + myAdapter.getItem(position), Toast.LENGTH_LONG).show();
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