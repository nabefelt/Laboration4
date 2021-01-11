package com.example.getlocation2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.getlocation2.model.MapPoster;
import com.example.getlocation2.model.readData;

import androidx.appcompat.app.AppCompatDialogFragment;


/**
 * class for cutom view dialog
 */
public class SavedMapPosterDialog extends AppCompatDialogFragment {
    private TextView editText;
    private ImageView photo;
    private String photoPath;
    private boolean update;
    private boolean savedMapPoster;
    private TextView cordinates;


    private String text;
    private String path;
    private double longitude2;
    private double latitide2;


    private boolean viewSavedMapPosters =false;

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }


    @Override
    public void onResume() {
        super.onResume();


        if(update==true){ // if the camera has updated the fragment
            readData readData= new readData();
            try {
                photoPath=    readData.readPicturePath(getContext()); // read the path to the pictue

                if(photoPath==null){
                    Bitmap b2=  BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera); // set to standard empty photo icon if string is null
                    //     Bitmap bitmap= BitmapFactory.decodeFile(b);
                    photo.setImageBitmap(b2);
                }
                else{

                    Bitmap bitmap= BitmapFactory.decodeFile(photoPath);
                    photo.setImageBitmap(bitmap); // set photo to imageview
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            update=false;
        }
        else{

        }


    }





    public void setMapPoster(Context context, MapPoster mapPoster){
        text=mapPoster.getText();
        photoPath=mapPoster.getPhotoPath();
        longitude2=mapPoster.getLongitude();
        latitide2=mapPoster.getLatitude();
    }


    public void setViewSavedMapPosters(boolean viewSavedMapPosters) {
        this.viewSavedMapPosters = viewSavedMapPosters;
    }

    @Override
    public void onStart() {
        super.onStart();


            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            photo.setImageBitmap(bitmap);//sets the photo from mapPoster
            editText.setText(text);// sets the text from mapPoster
            cordinates.setText(longitude2 + ", " + latitide2);//sets the cordinates from mapPoster
            savedMapPoster = false;
            viewSavedMapPosters =false;



    }

    public void setSavedMapPoster(boolean savedMapPoster) {
        this.savedMapPoster = savedMapPoster;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        update=false;
        savedMapPoster=false;
        View view = inflater.inflate(R.layout.layout_savedmapdialog, null);
        builder.setView(view)
                .setTitle("MapPoster")
                .setNegativeButton("back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        editText = view.findViewById(R.id.saved_edit_text);
        photo=view.findViewById(R.id.saved_cameraPhoto);
        cordinates=view.findViewById(R.id.saved_cordinates);
        Bitmap b2=  BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera);
         photo.setImageBitmap(b2); //setting noCameraPicture in Imageview
        return builder.create();
    }





}
