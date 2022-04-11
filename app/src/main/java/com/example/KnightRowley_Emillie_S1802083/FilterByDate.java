/**
 * @author Knight-Rowley_Emillie_S1802083
 */
package com.example.KnightRowley_Emillie_S1802083;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.KnightRowley_Emillie_S1802083.viewmodels.FilterByDateViewModel;

import java.util.Calendar;
import java.util.Date;

public class FilterByDate extends AppCompatActivity {
    private FilterByDateViewModel mFilterByDateViewModel;
    private ExpandableListView expandableListView;

    private DatePicker datePicker;
    private Button selectDateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO --> Move Data load to onclick for calender button
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by_date);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        selectDateButton = (Button) findViewById(R.id.selectDateButton);
        expandableListView = findViewById(R.id.elvMobiles);
        Context activityContext = this;

        mFilterByDateViewModel = ViewModelProviders.of(this).get(FilterByDateViewModel.class);

        // Get date when button pressed
        selectDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // TODO --> Move Logic to ViewModel
                // Date(y, m, d) is depreciated now
                Calendar myCal = Calendar.getInstance();
                myCal.set(Calendar.YEAR, datePicker.getYear());
                myCal.set(Calendar.MONTH, datePicker.getMonth());
                myCal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

                Date filterDate = myCal.getTime();
                ExpandableListAdapter elvAdapter = mFilterByDateViewModel.getELVAdapter(activityContext, filterDate);


                expandableListView.setAdapter(elvAdapter);

                // Set Expand/ Collapse behaviour.
                expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    int lastExpandedPosition = -1;
                    @Override
                    public void onGroupExpand(int i) {
                        // If another header is selected, collapse the one presently open.
                        lastExpandedPosition =  mFilterByDateViewModel.collapseLastGroup(expandableListView,lastExpandedPosition, i);
                    }
                });



            }
        });





    }

}