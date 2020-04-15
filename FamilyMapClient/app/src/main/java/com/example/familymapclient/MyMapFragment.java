package com.example.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Result.EventIDResult;
import Result.PersonIDResult;

public class MyMapFragment extends Fragment  {
    private String centeredID;
    private Context whereICameFrom;
    private GoogleMap mMap;
    private SupportMapFragment myfrag;
    private EventIDResult mapsEventSelected;
    private Boolean markerClicked = false;
    private Boolean comingFromPerson = false;

    public MyMapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (comingFromPerson){
            setHasOptionsMenu(false);
        } else {
            setHasOptionsMenu(true);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu myMenu, MenuInflater inflater){
        inflater.inflate(R.menu.activity_main, myMenu);
        super.onCreateOptionsMenu(myMenu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Intent intentThree = new Intent(getActivity(), SearchActivity.class);
                startActivity(intentThree);
                return true;
            case R.id.settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_map, container, false);


        myfrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        myfrag.getMapAsync(new OnMapReadyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onMapReady(GoogleMap googleMap){
                mMap = googleMap;

                ClientModel cm = ClientModel.getInstance();

                Map<String, EventIDResult> events = cm.getEventMap();

                Colors myColors = Colors.getInstance();
                List<String> colorsToUse = myColors.getMyColors();

                Set<String> masterEventList = new ArraySet<String>();

                for (int i = 0; i < cm.getEventTypesForUser().size(); i++){
                    masterEventList.add(cm.getEventTypesForUser().get(i).toLowerCase());
                }

                for (int i = 0; i < cm.getEventTypesForFemaleAncestors().size(); i++){
                    masterEventList.add(cm.getEventTypesForFemaleAncestors().get(i).toLowerCase());
                }

                for (int i = 0; i < cm.getEventTypesForMaleAncestors().size(); i++){
                    masterEventList.add(cm.getEventTypesForMaleAncestors().get(i).toLowerCase());
                }

                List<String> finalList = new ArrayList<>();

                finalList.addAll(masterEventList);

                cm.setAllEventTypes(finalList);

                for(Map.Entry<String, EventIDResult> entry : events.entrySet()){
                    EventIDResult event = entry.getValue();

                    if(cm.isShowFemaleEvents() &&
                            cm.getPersonById(event.getPersonID()).getGender().equals("f")){
                        int indexOfColorToUse = finalList.indexOf(event.getEventType().toLowerCase());
                        String colorHexValue = colorsToUse.get(indexOfColorToUse % 8);

                        LatLng eventLatLong2 = new LatLng(event.getLatitude(), event.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(eventLatLong2)
                                .icon(getMarkerIcon(colorHexValue))).setTag(event);
                    }

                    if (cm.isShowMaleEvents() &&
                            cm.getPersonById(event.getPersonID()).getGender().equals("m")){
                        int indexOfColorToUse = finalList.indexOf(event.getEventType().toLowerCase());
                        String colorHexValue = colorsToUse.get(indexOfColorToUse % 8);

                        LatLng eventLatLong2 = new LatLng(event.getLatitude(), event.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(eventLatLong2)
                                .icon(getMarkerIcon(colorHexValue))).setTag(event);
                    }

                }

                EventIDResult eventToCenter = events.get(centeredID);
                LatLng placeToCenter = new LatLng(eventToCenter.getLatitude(), eventToCenter.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(placeToCenter));

                if (comingFromPerson){
                    EventIDResult eventSelected = cm.getEventById(centeredID);
                    mapsEventSelected = eventSelected;
                    clickMarker(v);
                }

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        EventIDResult eventSelected = (EventIDResult) marker.getTag();
                        mapsEventSelected = eventSelected;
                        clickMarker(v);
                        Log.d("Click","was clicked");
                        return false;
                    }
                });
            }
        });


        LinearLayout bottomOfScreen = v.findViewById(R.id.bottomOfScreen);
        bottomOfScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (markerClicked){
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    Bundle mBundle  = new Bundle();

                    mBundle.putString("personID", mapsEventSelected.getPersonID());
                    intent.putExtras(mBundle);

                    startActivity(intent);
                }
            }
        });

        return v;
    }

    public void clickMarker(View v){
        ClientModel cm = ClientModel.getInstance();

        PersonIDResult personSelected = cm.getPersonById(mapsEventSelected.getPersonID());

        ImageView img = v.findViewById(R.id.iconImageView);

        if (personSelected.getGender().equals("f")){
            Drawable genderIcon = new IconDrawable(getActivity(),
                    FontAwesomeIcons.fa_female).colorRes(R.color.femaleColor).sizeDp(40);
            img.setImageDrawable(genderIcon);
        } else {
            Drawable genderIcon = new IconDrawable(getActivity(),
                    FontAwesomeIcons.fa_male).colorRes(R.color.maleColor).sizeDp(40);
            img.setImageDrawable(genderIcon);
        }

        TextView textTop = v.findViewById(R.id.textTop);
        TextView textBottom = v.findViewById(R.id.textBottom);

        textTop.setText(personSelected.getDescription());
        textBottom.setText(mapsEventSelected.getDescription());
        markerClicked = true;
    }

    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    public void setEventId(String in){
        centeredID = in;
    }

    public void setContext(Context in){ whereICameFrom = in; }

    public void setComingFromPerson(Boolean bool){
        comingFromPerson = bool;
    }
}