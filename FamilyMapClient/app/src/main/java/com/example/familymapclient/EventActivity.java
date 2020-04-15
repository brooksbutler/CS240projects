package com.example.familymapclient;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class EventActivity extends AppCompatActivity {

    private String IdOfEvent = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IdOfEvent = getIntent().getExtras().getString("personID");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);

        FragmentManager fm = getSupportFragmentManager();
        MyMapFragment mapFrag = (MyMapFragment) fm.findFragmentById(R.id.map);


        if(mapFrag==null) {
            mapFrag=new MyMapFragment();
            mapFrag.setEventId(IdOfEvent);
            mapFrag.setComingFromPerson(true);
            mapFrag.setContext(this);
            fm.beginTransaction()
                    .replace(R.id.mapLayout, mapFrag)
                    .commit();
        }
    }

}