package com.example.planit;

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

//will show the added list after it is done

public class AfterAddedPage extends AppCompatActivity {
    private TextView justAddedTextView;
    private TextView taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_added_page);

        justAddedTextView = (TextView)findViewById(R.id.justAddedTaskTextView);
        taskList = (TextView)findViewById(R.id.taskList);
    }

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

    } // end of onOptionsSelectMenu

    // maybe adding the find and delete things here - or edit

    public void findClick(View v){
       String taskName = justAddedTextView.getText().toString();
       PlannerDBHandler handler = new PlannerDBHandler(this);

       //Task task = handler.findTask(taskName);
    }



}