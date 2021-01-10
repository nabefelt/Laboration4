package com.example.getlocation2;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class saveData {

    public saveData() {
    }

    /**
     * saves the picture in to a file called "PhotoPath"
     * @param context
     * @param photoPath
     */
    public void savePicturePath(Context context, String photoPath){
        String filename = "photoPath.txt";
        File directory = context.getFilesDir();
        File file = new File(directory, filename);
        String photoPath2=photoPath;

        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(photoPath2);
            out.close();
            fileOut.close();

        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * saves the mapposters into a file called "mapPosters.txt"
     * @param context
     * @param mapPosters
     */

    public void mapPosterSave(Context context, ArrayList<MapPoster> mapPosters){
        String filename = "mapPosters.txt";
        File directory = context.getFilesDir();
        File file = new File(directory, filename);
        ArrayList <MapPoster> mapPosters2=mapPosters;

        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(mapPosters2);
            out.close();
            fileOut.close();

        } catch (IOException i) {
            i.printStackTrace();
        }
    }




}
