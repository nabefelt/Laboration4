package com.example.getlocation2.model;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class readData {
    public readData(){

    }



    /**
     * reads the saved mapPosters from "mapPosters.txt"
     * @param context
     * @return
     */
    public ArrayList<MapPoster> readMapPosters(Context context){
         ArrayList<MapPoster> mapPosters =new ArrayList<MapPoster>();
        try {
            String filename = "mapPosters.txt";
            File directory = context.getFilesDir();
            File file = new File(directory, filename);
            if(file.length()==0){

            }

            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mapPosters = (ArrayList<MapPoster>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();

        } catch (ClassNotFoundException cne) {
            System.out.println("class not found");
            cne.printStackTrace();

        }
        return mapPosters;
    }

    /**
     * read the picture path from file
     * @param context
     * @return
     */

    public String readPicturePath(Context context){

        String photoPath=null;

        try {
            String filename = "photoPath.txt";
            File directory = context.getFilesDir();
            File file = new File(directory, filename);
            if(file.length()==0){

            }
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
      photoPath = (String) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            photoPath=null;

        } catch (ClassNotFoundException cne) {
            System.out.println("class not found");
            cne.printStackTrace();

        }
        return photoPath;
    }


}
