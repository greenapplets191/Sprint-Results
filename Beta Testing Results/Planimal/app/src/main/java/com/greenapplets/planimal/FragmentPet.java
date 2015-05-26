package com.greenapplets.planimal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by ASUS on 3/14/2015.
 */
public class FragmentPet extends Fragment {
     private GridView gridInventory;
     private ArrayList<Item> inventory;
     private MyPetFile mpf=MainActivity.mpf;
     private Item[] list= new Item[4];
     @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          View view = inflater.inflate(R.layout.fragment_pet, container, false);
          //  mpf = MainActivity.mpf;
          inventory = new ArrayList<Item>();
          if(mpf.p==null){
               mpf.p=new Pet();
          }
          //  Bundle bundle = this.getArguments();
          //  if (bundle != null) {
          //       mpf.p.setMoney (bundle.getInt("money", mpf.p.getMoney()));
          //  }
          final TextView petName = (TextView) view.findViewById(R.id.petName);
          petName.setText(mpf.p.getName());
          TextView moneyAmt = (TextView) view.findViewById(R.id.moneyAmt);
          moneyAmt.setText(mpf.p.getMoney() + " moji");
          TextView healthLevel = (TextView) view.findViewById(R.id.healthLevel);
          healthLevel.setText(mpf.p.getHealth() + "/100");
          TextView fullnessLevel = (TextView) view.findViewById(R.id.fullnessLevel);
          fullnessLevel.setText(mpf.p.getHunger() + "/100");

        /*
        * SAMPLE PET ONLY, THO DIS BE WHERE YOU CAN UPLOAD THE SAVED PET I GUESS
        * */
          try {
               inventory = new ArrayList<Item>();
               Resources res = getActivity().getResources();
               String[] totalItemNames = res.getStringArray(R.array.item_names);
               String[] itemDescriptions = res.getStringArray(R.array.item_descriptions);
               Item[] list = new Item[totalItemNames.length];
               int[] images = {R.drawable.sicon_bit_bento, R.drawable.sicon_apple_juice, R.drawable.sicon_sweet_nibble,
                       R.drawable.sicon_heal_potion};
               for (int i = 0; i < totalItemNames.length; i++) {
                    Item item = new Item(images[i], totalItemNames[i]);
                    item.setDescription(itemDescriptions[i]);
                    list[i] = item;
                    item.setAmount(1);
               }
               if (!mpf.readPetFile()) {

                    //ask the name of the pet
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("What is your pet's name?");

                    final EditText input = new EditText(getActivity());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    alertDialogBuilder.setView(input);

                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                              mpf.p.setName(input.getText().toString());
                              if (mpf.p.getName().isEmpty()) {
                                   mpf.p.setName("No Name");
                              }
                              petName.setText(mpf.p.getName());
                              mpf.writePetFile();
                              dialog.cancel();
                         }
                    });

                    alertDialogBuilder.show();

               } else {
                    if (mpf.readInventory()) {
                     for (int i = 0; i < mpf.myItems.size(); i++) {
                              Item it = mpf.myItems.get(i);
                              inventory.add(it);
                       }
                    }
               }
          }catch (ParseException e1) {
               e1.printStackTrace();
          } catch (IOException e1) {
               e1.printStackTrace();
          }


     catch(Exception e){}

          // TOTAL ITEMS THAT PET CAN HAVE

        /*
        * item array currently has all the items we thought of, it has the image ID, name, and description so
        * you can call on it when recreating the saved inventory instead of assigning the stuff one by one everytime
        * order: bit bento, apple juice, sweet nibble, heal potion
        * */
          // END

        /*
        * UPLOAD INVENTORY AND PLACE IT IN ARRAYLIST
        * NOTE YOU NEED TO FILL UP EVERYTHING REQUIRED FOR AN ITEM CLASS NA, USE list ARRAY TO MAKE THINGS EASIER
        * */


          gridInventory = (GridView) view.findViewById(R.id.gridView);
          gridInventory.setAdapter(new CustomGridAdapter(inventory, getActivity()));
          return view;
     }
}
