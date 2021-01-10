package com.example.getlocation2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

public class camerActivity extends Activity {


    private String currentPhotoPath=null; // the path for the picture
    public static final int camera_code = 7; // the code in request permission
    public static final int camera_request = 8; //sets the request code for the camera in activityresult



    public camerActivity(){

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissionForCamera();
            takePicture();

    }

    /**
     * method for taking a picture
     */
    public void takePicture(){
        String filename="photo";
        File storageDirectory=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            Toast.makeText(this,"You have to give permisson to use the camera", Toast.LENGTH_SHORT).show();
            super.onBackPressed(); // returns to fragment if permission is denied
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){ // if permission granted
            try {
                File picFile= File.createTempFile(filename,".jpg",storageDirectory); // creates file for the picture
                Uri picUri=FileProvider.getUriForFile(getApplicationContext(),"com.example.getlocation2.fileprovider",picFile); // the authority which is in the manifest
                currentPhotoPath=picFile.getAbsolutePath(); // the path to the picture
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // camera Activity
                intent.putExtra(MediaStore.EXTRA_OUTPUT,picUri);// will write path for the picture from the fileprovider
                startActivityForResult(intent,camera_request); //start the cameraActivity

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true); // go back to fragment
    }

    /**
     * saved the path to the picture
     */

    public void saveData(){
        saveData saveData=new saveData();
        saveData.savePicturePath(this,currentPhotoPath); // saves the
    }


    /**
     * request the user for using the camera
     */
    private void requestPermissionForCamera() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, camera_code);

        }

        else {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(),"You have to give permission to use the camera",Toast.LENGTH_LONG);
        if (requestCode == camera_request && resultCode == RESULT_OK) {
            saveData();
            super.onBackPressed(); // returns to fragment
        }
        if(resultCode==RESULT_CANCELED){

            new File(currentPhotoPath).getAbsoluteFile().delete();
            currentPhotoPath=null;
            saveData();
            super.onBackPressed(); // returns to fragment
        }

        else{

        }
    }



}