/**
 * @author Knight-Rowley_Emillie_S1802083
 */
package com.example.KnightRowley_Emillie_S1802083.viewmodels;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.KnightRowley_Emillie_S1802083.models.Disruption;
import com.example.KnightRowley_Emillie_S1802083.repositories.DisruptionRepository;

import java.util.ArrayList;
import java.util.List;


public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<Disruption>> disruptions;
    private DisruptionRepository repo;

    private ArrayList<String> urls = new ArrayList<>();


    public void setDisruptions(String urlSource) {

        new Thread(new MainActivityViewModel.Task(urlSource)).start();
    }

    private class Task implements Runnable
    {
        private String url;
        public Task(String aurl)
        {
            url = aurl;
        }

        @Override
        public void run() {

            // -->--> Previously, getting all the XML data into classes.
            repo = DisruptionRepository.getInstance();
            disruptions = repo.getDisruptions();

        }
    }

    public MutableLiveData<List<Disruption>> getDisruptions(){
        return this.disruptions;
    }

}
