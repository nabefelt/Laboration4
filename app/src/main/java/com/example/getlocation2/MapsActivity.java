package com.example.getlocation2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.getlocation2.model.MapPoster;
import com.example.getlocation2.model.dataHolder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int location_permission = 3;
    private GoogleMap gMap;
    private FusedLocationProviderClient fusedLocationProviderClient; //to get the last location data
    private double longitude = 0;
    private double latitude = 0;
    private  Marker marker;

    protected com.example.getlocation2.model.dataHolder dataHolder=new dataHolder(this);

    private Button button;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    private Button save;



    @Override
    protected void onStop() {
        super.onStop();
       // dataHolder.saveMapPoster();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        dataHolder.readMapPosters();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item2:
                Toast.makeText(this,"Saved MapPosters",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(this,savedMapPosters.class);
                startActivity(i);
                gMap.clear();
                setMapPostMarkers();
                return true;
            case R.id.item3:
                Toast.makeText(this,"Instuctions Clicked",Toast.LENGTH_SHORT).show();
                instructions();
                return true;

        }


        return super.onOptionsItemSelected(item);
    }

    private void instructions() {
        InstructionDialog instructionDialog=new InstructionDialog();
        instructionDialog.show(getSupportFragmentManager(),"open instruction");



    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    /**
     * sets out the postmarkers on the map
     */
    public void setMapPostMarkers(){
    ArrayList<MapPoster> markers= dataHolder.getMapPosters();

    for(int i=0;i<markers.size();i++){
        LatLng marker=new LatLng(markers.get(i).getLatitude(),markers.get(i).getLongitude());
        BitmapDescriptor postMarkers=BitmapDescriptorFactory.fromResource(R.drawable.ic_dialog_map);
        gMap.addMarker(new MarkerOptions().position(marker).title(markers.get(i).getText()).draggable(false).icon(postMarkers));
    }
}


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
   setMapPostMarkers(); // set out the save mapPosters on the map

        button=findViewById(R.id.button);
        save=findViewById(R.id.save);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getApplicationContext());
        enableGpsToMylocation();
        getCurrentPosition();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapPoster();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableGpsToMylocation();
                getCurrentPosition();
                LatLng currentlocation=new LatLng(latitude, longitude);
                gMap.clear();
                setMapPostMarkers();

                BitmapDescriptor postMarkers=BitmapDescriptorFactory.fromResource(R.drawable.ic_dialog_map);
                 marker= gMap.addMarker(new MarkerOptions().position(currentlocation).title("PostMarker").draggable(true).icon(postMarkers)); // enable draggable so the user can move the marker


                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlocation,15)); // zoom into the location

            }
        });


            gMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {
                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }
                @Override
                public void onMarkerDragEnd(Marker marker) {
                    gMap.clear();
                    setMapPostMarkers();
                    BitmapDescriptor postMarkers=BitmapDescriptorFactory.fromResource(R.drawable.ic_dialog_map);
                    gMap.addMarker(new MarkerOptions().position(marker.getPosition()).title("postMarker").draggable(true).icon(postMarkers));
                    LatLng cordinatesFromMarker=   marker.getPosition();
                  longitude =  cordinatesFromMarker.longitude;
                  latitude=cordinatesFromMarker.latitude;
                    dataHolder.setLatitude(cordinatesFromMarker.latitude); // set the new latitude
                    dataHolder.setLongitude(cordinatesFromMarker.longitude); // set the new longitude

                }
            });

    }


    /**
     * getting the current postion of the user
     */
    private void getCurrentPosition() {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                Toast.makeText(this,"You have to give permisson to use the map", Toast.LENGTH_SHORT).show();
                return;
            }
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() { //for getting the most recent location
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    } else {


                    }
                }
            });


    }





    private void enableGpsToMylocation(){

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            gMap.setMyLocationEnabled(true);

        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},location_permission);
        }
    }

    /**
     * open a dialog for the mapPoster
     */
    private void openMapPoster() {
        MapPosterDialog mapPosterDialog=new MapPosterDialog();
         dataHolder.setLatitude(latitude); // sets latitude of the dataholder
        dataHolder.setLongitude(longitude);// sets longitude of the dataholder
        mapPosterDialog.show(getSupportFragmentManager(),"open mapposterDialgo");
    }



}