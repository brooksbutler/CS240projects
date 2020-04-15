package com.example.familymapclient;

public class DisplayRow {
    String topRow;
    String bottomRow;
    String type;
    String IDOfRow;

    public DisplayRow(String topRow, String bottomRow, String type, String ID){
        this.topRow = topRow;
        this.bottomRow = bottomRow;
        this.type = type;
        this.IDOfRow = ID;
    }

    public String getTopRow(){ return topRow; }

    public String getBottomRow(){ return bottomRow; }

    public String getType(){ return type; }

    public String getIdOfRow() { return IDOfRow; }

}