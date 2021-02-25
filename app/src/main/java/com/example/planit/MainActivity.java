package com.example.planit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // have to add images a certain way
    // set densitys too!
    //link to do it
    //https://developer.android.com/studio/write/resource-manager

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

}// end of main activity