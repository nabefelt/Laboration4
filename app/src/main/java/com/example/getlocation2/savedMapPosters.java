package com.example.getlocation2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.getlocation2.model.MapPoster;
import com.example.getlocation2.model.dataHolder;

import java.util.ArrayList;

public class savedMapPosters extends AppCompatActivity {

    public com.example.getlocation2.model.dataHolder dataHolder=new dataHolder(this);
    ListAdapter adapter; //hold the list of markPosters
    TextView noMapPosters;
    ArrayList<MapPoster> mapPosters=new ArrayList<MapPoster>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_map_posters);
      dataHolder.readMapPosters();
      noMapPosters=findViewById(R.id.noPosters);
      mapPosters=dataHolder.getMapPosters();
        adapter=new ArrayAdapter<MapPoster>(this, android.R.layout.simple_list_item_1,mapPosters); // displays all the mapPosters
        ListView listView=(ListView) findViewById(R.id.mapPosterList);
        listView.setAdapter(adapter);

        if(mapPosters.size()==0){
            noMapPosters.setText("no mapPosters have been saved");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mapPosterSelected(position);// gets the clicked mapPosters postion and displays the selected mapPoster
            }
        });
    }

    /**
     *
     * creates a mapPostersDialog from the selected item
     */
    private void mapPosterSelected(int i){
        mapPosters.get(i);
        dataHolder.setLongitude(mapPosters.get(i).getLongitude());
        dataHolder.setLatitude(mapPosters.get(i).getLatitude());
        MapPoster mp=mapPosters.get(i);
        openSavedMapPoster(mp);
    }

    /**
     * opens the selected mapPoster into a custom view
     * @param mapPoster
     */
    private void openSavedMapPoster(MapPoster mapPoster) {
        SavedMapPosterDialog savedMapPosterDialog=new SavedMapPosterDialog();
        savedMapPosterDialog.setMapPoster(this,mapPoster);
        savedMapPosterDialog.setViewSavedMapPosters(true);
        savedMapPosterDialog.show(getSupportFragmentManager(),"open mapposterDialgo");
    }


}