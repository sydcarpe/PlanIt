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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements TaskRecyclerViewAdapter.ItemClickListener{
    private static final String TAG = "MainActivity";
    // intent flags
    private static final int flag = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
    private static final boolean USE_FLAG = true;
    public ArrayList<Task> dailyList;
    public String[] taskNameList;
    public TextView dateTextView;

    private static final int request_code_checked = 1;
    private static final String KEY_MAINDATA = "maindata";
    private static final String KEY_CHECKBOX = "checkbox";
    private static final String KEY_RETURNDATA = "returnData";

    private static final String KEY_RETURNCHECK = "returnCheckResponse";


    // have to add images a certain way
    // set densitys too!
    //link to do it
    //https://developer.android.com/studio/write/resource-manager
    // recycler view
    TaskRecyclerViewAdapter myAdapter;
    CheckBox checkBoxes;

    private CheckBox taskBox;
    // creating adapter and layoutview
    public ArrayList<Task> myList;

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
        AB.setLogo(R.drawable.round_menu_book_24);

        checkBoxes = (CheckBox)findViewById(R.id.taskCheckBox);
        dateTextView = (TextView)findViewById(R.id.dateTextView);

        //setting the dateTextView to be the current date
        //changing the format to just display the current date
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        Date today = new Date();
        String todayStr = formatter.format(today);
        dateTextView.setText(todayStr);


        PlannerDBHandler handler = new PlannerDBHandler(this);

        // grabbing the arraylist from the DB

        //ArrayList<String> myList = handler.currentDayTasks();



        myList = handler.getTaskNames();
        //stringTaskNames =  new ArrayList<>();
        String [] tmpList = new String[myList.size()];

        // getting only the task names from the list and adding them to a String[] for the recycler
        for(int i = 0; i < myList.size(); i++){
            Task curTask = myList.get(i);
            String tempName = curTask.getTaskName();
            tmpList[i] = tempName;
        }


        //recycler View
        RecyclerView rView = (RecyclerView) findViewById(R.id.recyclerView);
        rView.setLayoutManager(new LinearLayoutManager(this));
        //making recycler view show the data
        myAdapter = new TaskRecyclerViewAdapter(tmpList, this);
        myAdapter.setTasks(myList);
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

                //checking if checkboxes are clicked
                //Boolean isChecked = checkBoxes.isChecked();
                //myIntent.putExtra(KEY_CHECKBOX, isChecked);
                startActivity(myIntent);
                return true;
            //add button
            case R.id.actionAdd:
                myIntent = new Intent(this, addPage.class);

                //checkbox clicked
                //Boolean isCheckedAdd = checkBoxes.isChecked();
                //myIntent.putExtra(KEY_CHECKBOX, isCheckedAdd);
                startActivity(myIntent);
                return true;

            //filter button changed to search button
            case R.id.actionFilter:
                myIntent = new Intent (this, searchPage.class);

                //check boc Clicked
                //Boolean isCheckedFilter = checkBoxes.isChecked();
                //myIntent.putExtra(KEY_CHECKBOX, isCheckedFilter);
                startActivity(myIntent);
                return true;

            //past planner button
            case R.id.actionPastPlan:
                myIntent = new Intent(this, pastPlanners.class);

                //checkbox clicked
                //Boolean isCheckedPast = checkBoxes.isChecked();
                //myIntent.putExtra(KEY_CHECKBOX, isCheckedPast);

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
        Intent myIntent = new Intent();

        Boolean isChecked = checkBoxes.isChecked();
        myIntent.putExtra(KEY_CHECKBOX, isChecked);
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


    // checkboxes


}// end of main activity