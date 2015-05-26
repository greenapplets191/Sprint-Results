package com.greenapplets.planimal;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by ASUS on 3/14/2015.
 */
public class FragmentPet extends Fragment {
    private GridView gridInventory;
    private ArrayList<Item> inventory;
    private Pet pet;

     @Override
     public void onPause() {
          super.onPause();
     }

     @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet, container, false);

        /*
        * SAMPLE PET ONLY, THO DIS BE WHERE YOU CAN UPLOAD THE SAVED PET I GUESS
        * */

        inventory = new ArrayList<Item>();
        pet = new Pet();

        // TOTAL ITEMS THAT PET CAN HAVE
        Resources res = getActivity().getResources();
        String[] totalItemNames = res.getStringArray(R.array.item_names);
        String[] itemDescriptions = res.getStringArray(R.array.item_descriptions);
        Item[] list = new Item[totalItemNames.length];
        int[] images = {R.drawable.sicon_bit_bento,R.drawable.sicon_apple_juice, R.drawable.sicon_sweet_nibble,
                R.drawable.sicon_heal_potion};

        for(int i = 0; i < totalItemNames.length; i++){
            Item  item = new Item(images[i], totalItemNames[i]);
            item.setDescription(itemDescriptions[i]);
            list[i] = item;
            item.setAmount(1);
            inventory.add(item);
        }
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
