package com.greenapplets.planimal;

import android.nfc.Tag;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageButton;

/**
 * Created by ASUS on 4/26/2015.
 */
public class Item {
    private int value; // how much does it cost
    private int amount; // how many is available (for inventory)
    private int imageID;
    private String name;
    private String description;
    private boolean forWhat=true; //true for fullness,false for health
    private int howMuch ; //value it adds to the forWhat



    public Item(int imageID, String name) {
        value = 0;
        amount = 0;
        this.name = name;
        this.imageID = imageID;
        description = new String();
    }

    public Item(int value,int amount, String name, String description, int imageID){
        this.imageID = imageID;
        this.value = value;
        this.name = name;
        this.amount = amount;
        this.description = description;
        /* if(imageID==4) {
              this.forWhat = false;
         }*/
    }

     public Item() {

     }

     public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
