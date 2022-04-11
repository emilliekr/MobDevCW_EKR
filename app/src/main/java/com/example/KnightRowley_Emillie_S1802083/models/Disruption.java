// Knight-Rowley_Emillie_S1802083
package com.example.KnightRowley_Emillie_S1802083.models;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Disruption  {
    String disruptionType;
    String title;
    String XML_description;
    String link;
    String geo; // Not a string
    String author;
    String comments;
    String publicationDate; // Date, but needs worked into it
    // Calculated fields
    Date dateStart; // Not a String
    Date dateEnd; // Not a String
    int durationDays;

    String description;
    LatLng disruptionLatLong;

    public Disruption(){

    }

    public boolean compareByDate(Date filterDate){
        boolean result;
        // filterDate.after(this.dateStart) && filterDate.before(this.dateEnd) Doesn't include the start/end dates
        result = (!filterDate.before(this.dateStart) && !filterDate.after(this.dateEnd));

        return result;
    }

    public boolean compareByString(String filterText){
        boolean result;
        try{
            result = this.title.contains(filterText);

            return result;
        }
        catch (NullPointerException e){
            Log.e("CompareByString_Failure", "Discarded Record, " + this.title);
            return false;
        }


    }

    public String getDisplayBrief(){

        DateFormat targetFormat = new SimpleDateFormat("EEE - MMM d");
        String displayBrief = this.title.toUpperCase();
        if(this.durationDays == 0) {
            displayBrief = displayBrief + " (" + targetFormat.format(this.dateStart) + " [Ongoing]";

        } else {
            displayBrief = displayBrief + " (" + targetFormat.format(this.dateStart) + " to ";
            displayBrief = displayBrief + targetFormat.format(this.dateEnd) + ") \n";

        }

        displayBrief = displayBrief + "#!SPLIT_LOL!#" + String.valueOf(durationDays);

        return displayBrief;
    }

    public String getDisplayVerbose(){

        if(this.durationDays == 0){
            // Current Disruption
            DateFormat targetFormat = new SimpleDateFormat("d/M/yyyy");
            String displayVerbose = "";
            String EOL = "\n";
            // Build display string

            displayVerbose = "Disruption Type:" + this.disruptionType + EOL;
            displayVerbose += "Start Date: " + targetFormat.format(this.dateStart) + EOL;
            displayVerbose += "End Date: " + "Ongoing" + EOL;
            displayVerbose += "Total Duration: " + "TBC" + EOL;
            displayVerbose += "Event Details:" + EOL;
            displayVerbose += this.description + EOL;
            //displayVerbose += Co-Ordinates stuff;
            displayVerbose += "Published: " + this.publicationDate  + EOL;

            return displayVerbose;

        }else {
            DateFormat targetFormat = new SimpleDateFormat("d/M/yyyy");
            String displayVerbose = "";
            String EOL = "\n";
            // Build display string
            displayVerbose = "Disruption Type:" + this.disruptionType + EOL;
            displayVerbose = "Start Date: " + targetFormat.format(this.dateStart) + EOL;
            displayVerbose += "End Date: " + targetFormat.format(this.dateEnd) + EOL;
            displayVerbose += "Total Duration: " + String.valueOf(this.durationDays) + " Days" + EOL;
            displayVerbose += "Event Details:" + EOL;
            displayVerbose += this.description + EOL;
            //displayVerbose += Co-Ordinates stuff;
            displayVerbose += "Published: " + this.publicationDate  + EOL;

            return displayVerbose;
        }
    }

    public void setCalculatedFields(){
        DateFormat format = new SimpleDateFormat("E, d MMMM yyyy", Locale.ENGLISH);
        String[] parts = this.XML_description.split("<br />");

        if(this.XML_description.contains("Start Date") && parts.length > 1){
            // Get Start Date
            String datePart = parts[0];
            // Get just the date part
            datePart = datePart.substring(12, datePart.length() - 8);
            // Convert date part string to datetime
            try {
                this.dateStart = format.parse(datePart);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Get End Date
            datePart = parts[1];
            // Get just the date part
            datePart = datePart.substring(10, datePart.length() - 8);
            // Convert date part string to datetime
            try {
                this.dateEnd = format.parse(datePart);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Set Duration
            this.durationDays = (int) Math.round((this.dateEnd.getTime() - this.dateStart.getTime()) / (double) 86400000);

            if(parts.length > 2) {
                // Set Description
                this.description = parts[2];
            }
        } else {
            this.dateStart = new Date();
            this.dateEnd = new Date();

            this.description = this.XML_description;
        }

        // set lat long
        this.disruptionLatLong = this.setDisruptionLatLong();
    }


    public String getXML_description() {
        return XML_description;
    }

    public void setXML_description(String XML_description) {
        this.XML_description = XML_description;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        // Probs do some... geo shit?
        this.geo = geo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publication_date) {
        // Probs do some date conversion shit
        this.publicationDate = publication_date;
    }

    public void setDisruptionType(String disruptionType) {
        this.disruptionType = disruptionType;
    }

    public String getDisruptionType(){
        return this.disruptionType;
    }

    public LatLng getDisruptionLatLong(){
        return this.disruptionLatLong;
    }
    public LatLng setDisruptionLatLong(){
        // Parse Lat long
        String[] geoSplit = this.geo.split(" ");
        Double geoLat = Double.parseDouble(geoSplit[0]);
        Double geoLong = Double.parseDouble(geoSplit[1]);

        disruptionLatLong = new LatLng(geoLat, geoLong);

        return disruptionLatLong;
    }

}
