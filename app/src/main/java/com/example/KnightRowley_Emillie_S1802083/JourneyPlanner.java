/**
 * @author Knight-Rowley_Emillie_S1802083
 */
package com.example.KnightRowley_Emillie_S1802083;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;

import com.example.KnightRowley_Emillie_S1802083.viewmodels.FilterByDateViewModel;
import com.example.KnightRowley_Emillie_S1802083.viewmodels.JourneyPlannerViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.Date;

public class JourneyPlanner extends AppCompatActivity implements OnMapReadyCallback {
    private JourneyPlannerViewModel mJourneyPlanner;

    private DatePicker datePicker;
    private Button selectDateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_planner);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        selectDateButton = (Button) findViewById(R.id.selectDateButton);
        mJourneyPlanner = ViewModelProviders.of(this).get(JourneyPlannerViewModel.class);

        // Fragment to handle the actual map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final GoogleMap gMap = googleMap;

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Date(y, m, d) is depreciated now
                Calendar myCal = Calendar.getInstance();
                myCal.set(Calendar.YEAR, datePicker.getYear());
                myCal.set(Calendar.MONTH, datePicker.getMonth());
                myCal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

                Date filterDate = myCal.getTime();

                // Update Map

                mJourneyPlanner.getDisruptionsMapPins(gMap, filterDate);

            }

        });
    }
}
