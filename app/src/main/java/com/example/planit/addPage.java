package com.example.planit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class addPage extends AppCompatActivity {

    //add button adds to the daily planner
    // cancel button goes to home page again

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);
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