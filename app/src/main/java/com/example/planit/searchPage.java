package com.example.planit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;

public class searchPage extends AppCompatActivity {
    private EditText taskName;
    private TextView IDTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        taskName = (EditText)findViewById(R.id.taskSearchEditText);
        IDTextView = (TextView) findViewById(R.id.isCompletedTextView);
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


        Task task = handler.findTask(tempName);

        if (task != null) {

            IDTextView.setText(String.valueOf(task.getID()));

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
        } else {
            IDTextView.setText("No match found");
        }
    }


}