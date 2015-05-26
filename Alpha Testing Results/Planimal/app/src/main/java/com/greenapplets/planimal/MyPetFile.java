package com.greenapplets.planimal;

import android.content.Context;
import android.content.ContextWrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Z370 on 4/13/2015.
 */
public class MyPetFile extends ContextWrapper{

     private static final String PET = "PetSave.txt";
     static File file = new File(PET); /* file where the tasks are written */
     static List<Item> myItems= new ArrayList <Item>();
     static Pet p;

     public MyPetFile(Context base) {
          super(base);
     }

     public void writePetFile(){
          Context ctx = getApplicationContext();
          try {
               FileOutputStream fos = ctx.openFileOutput(PET, Context.MODE_PRIVATE);
               String petStat=p.getName()+";"+ p.getScore() +";"+p.getMoney()+";"+ p.getHunger() + ";" + p.getHealth() + ";" ;
               fos.write(petStat.getBytes());
               for (int i = 0; i < myItems.size(); i++) {
                    Item mine = myItems.get(i);
                    String data = mine.getName() + ";" + mine.getDescription()
                            + ";" + mine.getAmount() + ";" + mine.getValue() + ";" + mine.getImageID() + ";";
                    fos.write(data.getBytes());
                    fos.write(13);
                    fos.write(10);
               }
               fos.close();
          } catch (IOException e) {
               e.printStackTrace();
          }
     }

     public void readPetFile() throws IOException,
             ParseException {
          Context ctx = getApplicationContext();
          BufferedReader br = null;
          FileInputStream fis = null;
          if (file != null) {
               try {
                    fis = ctx.openFileInput(PET);
                    br = new BufferedReader(new InputStreamReader(fis));
                    String petStat = br.readLine();
                    String[] stat= petStat.split(";");
                    p=new Pet(stat[0], Integer.valueOf(stat[1]), Integer.valueOf(stat[2]), Integer.valueOf(stat[3]), Integer.valueOf(stat[4]));
                    String line=br.readLine();
                    while (line != null) {
                         String[] data = line.split(";");
                         //Item i = new Item(data[0], Integer.valueOf(data[1]),data[2], Double.valueOf(data[3]), data[4]);
                         Item i = new Item(Integer.valueOf(data[2]), Integer.valueOf(data[3]), data[0], data[1],Integer.valueOf(data[4]));
                         p.addToInventory(i);
                         myItems.add(i);
                         line = br.readLine();
                    }
                    // p.mine=myItems;
               } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
               } catch (IOException e) {
                    e.printStackTrace();
               } finally {
                    if (br != null) {
                         try {
                              fis.close();
                              br.close();
                         } catch (IOException e) {
                              e.printStackTrace();
                         }
                    }
               }
          }
     }
}
