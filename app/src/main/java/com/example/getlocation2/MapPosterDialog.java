package com.example.getlocation2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

public class MapPosterDialog extends AppCompatDialogFragment {
    private EditText editText;

    private ImageButton imageButton;
    private ImageView photo;
    private String photoPath;
    private boolean update;
    private boolean savedMapPoster;
    private TextView cordinates;

    private boolean viewSavedMapPosters =false;

    private String text;
    private String path;
    private double longitude2;
    private double latitide2;



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






    public void setMapPoster(Context context,MapPoster mapPoster){
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

        if(viewSavedMapPosters ==true) { // watching saved mapPoster
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            photo.setImageBitmap(bitmap);
            editText.setText(text);
            cordinates.setText(longitude2 + ", " + latitide2);
            savedMapPoster = false;

            viewSavedMapPosters =false;
        }
        else{// normal framagment for saving the mapPoster
            double lati= ((MapsActivity)getActivity()).dataHolder.getLatitude();
            double longi= ((MapsActivity)getActivity()).dataHolder.getLongitude();
            cordinates.setText(lati+". "+longi);
        }

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
        View view = inflater.inflate(R.layout.layout_mapdialog, null);
        builder.setView(view)
                .setTitle("MapPoster")
                .setNegativeButton("back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = editText.getText().toString();
                        ((MapsActivity)getActivity()).dataHolder.setPhotoPath(photoPath);
                        ((MapsActivity)getActivity()).dataHolder.setText(editText.getText().toString());
                        ((MapsActivity)getActivity()).dataHolder.creatMapPoster();
                        ((MapsActivity)getActivity()).dataHolder.saveMapPoster();
                            //Saves the mapPoster
                    }
                });

        editText = view.findViewById(R.id.edit_text);
        imageButton=view.findViewById(R.id.imageButton);
        photo=view.findViewById(R.id.cameraPhoto);
        cordinates=view.findViewById(R.id.cordinates);

        Bitmap b2=  BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera);
         photo.setImageBitmap(b2); //settings noCameraPicture in Imageview



        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getContext(),camerActivity.class);
                startActivity(i);
                update=true;

            }
        });
        return builder.create();
    }





}
