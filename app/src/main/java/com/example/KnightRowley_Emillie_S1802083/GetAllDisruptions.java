/**
 * @author Knight-Rowley_Emillie_S1802083
 */
package com.example.KnightRowley_Emillie_S1802083;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.KnightRowley_Emillie_S1802083.viewmodels.GetAllDisruptionsViewModel;

public class GetAllDisruptions extends AppCompatActivity {

    private GetAllDisruptionsViewModel mGetAllDisruptionsViewModel;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_disruptions);
        mGetAllDisruptionsViewModel = ViewModelProviders.of(this).get(GetAllDisruptionsViewModel.class);

        expandableListView = findViewById(R.id.elvMobiles);
        expandableListView.setAdapter(mGetAllDisruptionsViewModel.getELVAdapter(this));

        // Set Expand/ Collapse behaviour.
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                // If another header is selected, collapse the one presently open.
                lastExpandedPosition =  mGetAllDisruptionsViewModel.collapseLastGroup(expandableListView,lastExpandedPosition, i);
            }
        });
    }

}
