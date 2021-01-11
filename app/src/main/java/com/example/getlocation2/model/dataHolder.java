package com.example.getlocation2.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * this class in for managing the data regarding the mapPosters in the application
 */

public class dataHolder {

    private ArrayList<MapPoster> mapPosters=new ArrayList<MapPoster>();
    private String text;
    private String photoPath;
    private double latitude;
    private double longitude;
    private Context context;

    public dataHolder(Context context){
        this.context=context;

    }

    public ArrayList<MapPoster> getMapPosters() {
        return mapPosters;
    }

    public void readMapPosters(){
        readData readData=new readData();
       mapPosters= readData.readMapPosters(this.context);
    }

    public void saveMapPoster(){
        saveData saveData=new saveData();
        saveData.mapPosterSave(this.context,this.mapPosters);
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void creatMapPoster(){
        MapPoster mapPoster=new MapPoster(text,photoPath,latitude,longitude);
        mapPosters.add(mapPoster);
    }

    public int sizeOfposter(){
        return mapPosters.size();
    }




}
