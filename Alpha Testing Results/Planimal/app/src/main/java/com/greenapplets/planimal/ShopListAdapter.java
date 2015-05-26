package com.greenapplets.planimal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;

/**
 * Created by USER on 5/3/2015.
 */
public class ShopListAdapter extends BaseAdapter implements ListAdapter{
     private ArrayList<Item> itemList;
     private Context context;

     public ShopListAdapter () {
          populateShop();
     }

     private void populateShop() {
          Item newItemA = new Item(10, 5, "Apple Juice", "apple juice description", 1);
          Item newItemB = new Item(10, 5, "Bit Bento", "bit bento description", 2);
          Item newItemH = new Item(10, 5, "Heal Potion", "heal potion description", 3);
          Item newItemS = new Item(10, 5, "Sweet Nibble", "sweet nibble description", 4);

          itemList.add(newItemA);
          itemList.add(newItemB);
          itemList.add(newItemH);
          itemList.add(newItemS);
     }

     @Override
     public int getCount() {
          return 0;
     }

     @Override
     public Object getItem(int position) {
          return null;
     }

     @Override
     public long getItemId(int position) {
          return 0;
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
          View view = convertView;

          if (view == null) {
               LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

          }


          return view;
     }
}
