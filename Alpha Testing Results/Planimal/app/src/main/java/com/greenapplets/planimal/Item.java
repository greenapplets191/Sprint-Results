package com.greenapplets.planimal;

import android.widget.ImageButton;

/**
 * Created by ASUS on 4/26/2015.
 */
public class Item {
    private int value; // how much does it cost
    private int amount; // how many is available (for inventory)
    private String name;
    private String description;
     private int imageID;
//    private ImageButton imageButton; // icon of item

    public Item(int imageID, String name) {
        value = 0;
        amount = 0;
        this.name = new String();
         this.imageID = imageID;
        description = new String();
    }
//
//    public Item(int value,int amount, String name, String description, String source){
//        this.value = value;
//        this.name = name;
//        this.amount = amount;
//        this.description = description;
//    }

     public Item (int value, int amount, String name, String description, int imageID) {
          this.name = name;
          this.description = description;
          this.amount = amount;
          this.value = value;
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

//    public ImageButton getImageButton() {
//        return imageButton;
//    }
//
//    public void setImageButton(ImageButton imageButton) {
//        this.imageButton = imageButton;
//    }

     public int getImageID() {
          return imageID;
     }

     public void setImageID(int imageID) {
          this.imageID = imageID;
     }
}
