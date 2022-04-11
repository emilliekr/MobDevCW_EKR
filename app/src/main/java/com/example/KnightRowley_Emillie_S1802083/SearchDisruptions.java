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
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.KnightRowley_Emillie_S1802083.viewmodels.SearchDisruptionsViewModel;

public class SearchDisruptions extends AppCompatActivity {
    private SearchDisruptionsViewModel mSearchDisruptionsViewModel;
    private ExpandableListView expandableListView;

    private EditText textInput;
    private Button filterByTextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_disruptions);
        //datePicker = (DatePicker) findViewById(R.id.datePicker);
        filterByTextButton = (Button) findViewById(R.id.filterByTextButton);
        textInput = (EditText) findViewById(R.id.textInput);
        expandableListView = findViewById(R.id.elvMobiles);
        Context activityContext = this;

        mSearchDisruptionsViewModel = ViewModelProviders.of(this).get(SearchDisruptionsViewModel.class);

        // Get date when button pressed
        filterByTextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // TODO --> Move Logic to ViewModel
                String filterText = textInput.getText().toString();
                ExpandableListAdapter elvAdapter = mSearchDisruptionsViewModel.getELVAdapter(activityContext, filterText);

                expandableListView.setAdapter(elvAdapter);

                // Set Expand/ Collapse behaviour.
                expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    int lastExpandedPosition = -1;
                    @Override
                    public void onGroupExpand(int i) {
                        // If another header is selected, collapse the one presently open.
                        lastExpandedPosition =  mSearchDisruptionsViewModel.collapseLastGroup(expandableListView,lastExpandedPosition, i);
                    }
                });



            }
        });





    }

}