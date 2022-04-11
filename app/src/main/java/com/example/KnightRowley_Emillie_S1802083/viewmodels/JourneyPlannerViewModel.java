/**
 * @author Knight-Rowley_Emillie_S1802083
 */
package com.example.KnightRowley_Emillie_S1802083.viewmodels;


import androidx.lifecycle.ViewModel;
import com.example.KnightRowley_Emillie_S1802083.models.Disruption;
import com.example.KnightRowley_Emillie_S1802083.repositories.DisruptionRepository;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;

public class JourneyPlannerViewModel  extends ViewModel {

    // Get Disruption Data
    private ArrayList<Disruption> disruptions;
    private DisruptionRepository repo;
    LatLng GCU = new LatLng(55.866798, -4.254270);


    public void getDisruptionsMapPins(GoogleMap googleMap, Date filterDate) {
        repo = DisruptionRepository.getInstance();
        disruptions = repo.disruptions;
        googleMap.addMarker(new MarkerOptions().position(GCU).title("Development HQ!"));

        for (Disruption disruption : disruptions) {
            if (disruption.compareByDate(filterDate)) {
                googleMap.addMarker(new MarkerOptions().position(disruption.getDisruptionLatLong()).title(disruption.getDisplayBrief()));

            }
        }

        // Center map to default position
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GCU, 10));
    }
}

