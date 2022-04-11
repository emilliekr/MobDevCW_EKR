/**
 * @author Knight-Rowley_Emillie_S1802083
 */
package com.example.KnightRowley_Emillie_S1802083;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.example.KnightRowley_Emillie_S1802083.models.Disruption;
import com.example.KnightRowley_Emillie_S1802083.viewmodels.MainActivityViewModel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private Handler progressHandler;
    private ProgressBar progressBar;

    private Button getDataButton;
    private Button gotoGetAllDisruptions;
    private Button gotoFilterByDate;
    private Button gotoSearchDisruptions;
    private Button gotoJourneyPlanner;


    private String urlSource="";
    public static  ArrayList<Disruption> data_disruptions;

    // New MVVM Stuff
    private MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create our View Model Object
        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        // Press to Get Data
        getDataButton = (Button)findViewById(R.id.getDataButton);
        getDataButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.e("data_load","Initialising Data Load.");
                mMainActivityViewModel.setDisruptions(urlSource);
            }
        });

        // View All Disruptions
        gotoGetAllDisruptions = (Button)findViewById(R.id.gotoGetAllDisruptions);
        gotoGetAllDisruptions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("nav","Navigating to 'Get All Disruptions'.");
                Intent allDisruptionsIntent = new Intent(MainActivity.this, GetAllDisruptions.class);
                startActivity(allDisruptionsIntent);

            }
        });

        // View Filter by Date
        gotoFilterByDate = (Button)findViewById(R.id.gotoFilterByDate);
        gotoFilterByDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("nav","Navigating to 'Search Disruptions by Date'.");
                Intent disruptionsByDate = new Intent(MainActivity.this, FilterByDate.class);
                startActivity(disruptionsByDate);

            }
        });

        // View Search Disruptions
        gotoSearchDisruptions = (Button) findViewById(R.id.gotoSearchDisruptions);
        gotoSearchDisruptions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("nav","Navigating to 'Search Disruptions by ...Road?'.");
                Intent disruptionsByDate = new Intent(MainActivity.this, SearchDisruptions.class);
                startActivity(disruptionsByDate);

            }
        });

        // View Journey Planner
        gotoJourneyPlanner = (Button) findViewById(R.id.gotoJourneyPlanner);
        gotoJourneyPlanner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("nav","Navigating to 'Search Disruptions by ...Road?'.");
                Intent journeyPlanner = new Intent(MainActivity.this, JourneyPlanner.class);
                startActivity(journeyPlanner);

            }
        });

    }



}




 /*
            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");

                }
            });

  */


