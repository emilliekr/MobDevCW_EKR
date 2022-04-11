/**
 * @author Knight-Rowley_Emillie_S1802083
 */
package com.example.KnightRowley_Emillie_S1802083.viewmodels;

import android.content.Context;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import androidx.lifecycle.ViewModel;

import com.example.KnightRowley_Emillie_S1802083.adapters.FilterByDateAdapter;
import com.example.KnightRowley_Emillie_S1802083.models.Disruption;
import com.example.KnightRowley_Emillie_S1802083.repositories.DisruptionRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterByDateViewModel  extends ViewModel {

    // Get Disruption Data
    private ArrayList<Disruption> disruptions;
    private DisruptionRepository repo;

    Map<String, List<String>> DisruptionCollection = new HashMap<>();
    List<String> groupList = new ArrayList<String>();
    List<String> childList = new ArrayList<String>();

    public ExpandableListAdapter getELVAdapter(Context sourceContext) {
        elvPrepareData();
        FilterByDateAdapter expandableListAdapter = new FilterByDateAdapter(sourceContext, groupList, DisruptionCollection);
        return expandableListAdapter;
    }

    private void elvPrepareData(){
        repo = DisruptionRepository.getInstance();
        disruptions = repo.disruptions;

        String disruptionBrief = "";
        String disruptionVerbose = "";

        // Data is expected in format (List<String> parentList,  Map<String, List<String>> disruptionCollection
        for (Disruption disruption: disruptions){
            disruptionBrief = disruption.getDisplayBrief();
            disruptionVerbose = disruption.getDisplayVerbose();

            groupList.add(disruptionBrief);

            // Need to pass childList by value... kinda.
            childList = new ArrayList<String>();
            childList.add(disruptionVerbose);
            DisruptionCollection.put(disruptionBrief, childList);

        }
    }

    public ExpandableListAdapter getELVAdapter(Context sourceContext, Date filterDate) {
        elvPrepareData(filterDate);
        FilterByDateAdapter expandableListAdapter = new FilterByDateAdapter(sourceContext, groupList, DisruptionCollection);
        return expandableListAdapter;
    }

    private void elvPrepareData(Date filterDate){
        repo = DisruptionRepository.getInstance();
        disruptions = repo.disruptions;

        String disruptionBrief = "";
        String disruptionVerbose = "";

        // Data is expected in format (List<String> parentList,  Map<String, List<String>> disruptionCollection
        for (Disruption disruption: disruptions){
            // Tried doing a stream filter but with our set size, keeping it simple and filtering here
            if(disruption.compareByDate(filterDate)){
                disruptionBrief = disruption.getDisplayBrief();
                disruptionVerbose = disruption.getDisplayVerbose();

                groupList.add(disruptionBrief);

                // Need to pass childList by value... kinda.
                childList = new ArrayList<String>();
                childList.add(disruptionVerbose);
                DisruptionCollection.put(disruptionBrief, childList);
            }
        }
    }

    public int collapseLastGroup(ExpandableListView expandableListView, int lastExpandedPosition, int i){
        if(lastExpandedPosition != -1 && i != lastExpandedPosition){
            expandableListView.collapseGroup(lastExpandedPosition);
        }
        lastExpandedPosition = i;

        return lastExpandedPosition;

    }

}
