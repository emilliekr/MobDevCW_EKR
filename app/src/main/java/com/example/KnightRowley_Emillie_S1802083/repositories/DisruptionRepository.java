/**
 * @author Knight-Rowley_Emillie_S1802083
 */
package com.example.KnightRowley_Emillie_S1802083.repositories;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.KnightRowley_Emillie_S1802083.models.Disruption;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

// Singleton
public class DisruptionRepository {

    private static DisruptionRepository instance;
    public ArrayList<Disruption> disruptions = new ArrayList<>();


    public static DisruptionRepository getInstance() {
        if (instance == null) {
            instance = new DisruptionRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Disruption>>  getDisruptions() {

        String rssFeed = "";

        String urlSourcePlanned="https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
        String urlSourceCurrentIncidents="https://trafficscotland.org/rss/feeds/currentincidents.aspx";
        String urlSourceRoadworks="https://trafficscotland.org/rss/feeds/roadworks.aspx";

        MutableLiveData<List<Disruption>> data = new MutableLiveData<>();


        // XML Reading Logic
        // Planned Roadworks
        rssFeed = readRSSFeed(urlSourcePlanned);
        disruptions.addAll(xmlParser(rssFeed, "PlannedRoadworks"));

        // Current Roadworks
        rssFeed = readRSSFeed(urlSourceRoadworks);
       disruptions.addAll(xmlParser(rssFeed, "Roadworks"));

        // Planned urlSourceCurrentIncidents
        rssFeed = readRSSFeed(urlSourceCurrentIncidents);
        disruptions = xmlParser(rssFeed, "CurrentIncidents");

        data.postValue(disruptions);
        return data;
    }

    private String readRSSFeed(String url) {
        URL aurl;
        URLConnection feedConnection;
        BufferedReader bfReader = null;
        String inputLine = "";
        String result = "";

        try {
            aurl = new URL(url);
            feedConnection = aurl.openConnection();
            bfReader = new BufferedReader(new InputStreamReader(feedConnection.getInputStream()));

            while ((inputLine = bfReader.readLine()) != null) {
                // Read the RSS feed line by line
                result = result + inputLine;
                Log.e("RSS_Feed", inputLine);

            }
            bfReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("DisruptionRepository", "readRSSFeed(): ioexception");
            e.printStackTrace();
        }

        return result;
    }

    private ArrayList<Disruption> xmlParser(String input, String disruptionType) {
        // Initialise Pull Parser Factory, to create XMLPullParser.
        XmlPullParserFactory factory = null;
        XmlPullParser xpp = null;
        int eventType = 0;
        // Prepare list to hold parsed Disruption objects
        //ArrayList<Disruption> disruptions = new ArrayList<Disruption>();
        Disruption currentDisruption = new Disruption();

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();

            // Set input for the parser, and record the initial XML event type.
            xpp.setInput(new StringReader(input));
            eventType = xpp.getEventType();

        } catch (XmlPullParserException e) {
            Log.e("DisruptionRepository", "xmlParser(): XmlPullParserException");
            e.printStackTrace();
        }

        // While Loop will go through each element in the XML until it reaches END_DOCUMENT tag
        while (eventType != XmlPullParser.END_DOCUMENT) {
            // Check for START_TAG to indicate the start of Disruption details
            if (eventType == XmlPullParser.START_TAG) {
                try {
                    switch (xpp.getName().toLowerCase()) {
                        case "title":
                            currentDisruption.setTitle(xpp.nextText());
                            break;
                        case "description":
                            currentDisruption.setXML_description(xpp.nextText());
                            break;
                        case "link":
                            currentDisruption.setLink(xpp.nextText());
                            break;
                        case "point":
                            currentDisruption.setGeo(xpp.nextText());
                            break;
                        case "author":
                            currentDisruption.setAuthor(xpp.nextText());
                            break;
                        case "comments":
                            currentDisruption.setComments(xpp.nextText());
                            break;
                        case "pubdate":
                            currentDisruption.setPublicationDate(xpp.nextText());
                            break;
                    }
                } catch (IOException e) {
                    Log.e("DisruptionRepository", "xmlParser(): IOException");
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    Log.e("DisruptionRepository", "xmlParser(): XmlPullParserException");
                    e.printStackTrace();
                }

            } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                // If we see the end tag for <item>, it means we've read a whole disruption and can store it.
                currentDisruption.setDisruptionType(disruptionType);
                currentDisruption.setCalculatedFields();
                disruptions.add(currentDisruption);

                // Reset current disruption for next run through the loop
                currentDisruption = new Disruption();
            }

            // After we've read a tag (ie. <title> "..." </title>), move to next tag.
            try {
                eventType = xpp.next();
            } catch (IOException e) {
                Log.e("DisruptionRepository", "xmlParser(): IOException");
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                Log.e("DisruptionRepository", "xmlParser(): XmlPullParserException");
                e.printStackTrace();
            }

        }

        return disruptions;
    }
}


