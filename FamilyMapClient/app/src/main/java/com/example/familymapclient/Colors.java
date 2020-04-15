package com.example.familymapclient;

import java.util.ArrayList;
import java.util.List;

public class Colors {

    private static Colors myObj;

    private List<String> myColors;


    private Colors() {
        myColors = new ArrayList<>();

        myColors.add("#ff1d18"); //red : 0
        myColors.add("#3bc3ea"); //blue : 1
        myColors.add("#a4f161"); //green : 2
        myColors.add("#ffe049"); // Yellow : 3
        myColors.add("#51f0c3"); //Cyan : 4
        myColors.add("#eb78c8"); //Pink : 5
        myColors.add("#f9903b"); //orange : 6
        myColors.add("#761999"); // purple : 7
    }

    public static Colors getInstance() {
        if (myObj == null) {
            myObj = new Colors();
        }
        return myObj;
    }

    public String getColorByIndex(int in){
        return myColors.get(in);
    }

    public List<String> getMyColors(){
        return myColors;
    }

}