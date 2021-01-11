package com.example.getlocation2.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class MapPoster implements Serializable {

    private String text;
    private String photoPath;
    private double latitude;
    private double longitude;

    public MapPoster(String text, String photoPath, double latitude, double longitude) {
        this.text = text;
        this.photoPath = photoPath;
        this.latitude = latitude;
        this.longitude = longitude;
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

    @Override
    public String toString() {
        return "MapPoster:"+text;
    }
}
