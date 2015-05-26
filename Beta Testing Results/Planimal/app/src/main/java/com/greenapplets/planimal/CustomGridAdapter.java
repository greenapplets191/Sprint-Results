package com.greenapplets.planimal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASUS on 5/2/2015.
 */
public class CustomGridAdapter extends BaseAdapter{
    Item[] list;   // will contain all items possible in planimal
    ArrayList<Item> inventory; // will contain items that owner currently has
    Context context;
    MyPetFile mpf= MainActivity.mpf;
    public CustomGridAdapter(ArrayList<Item> inventory, Context context) {
        this.inventory = inventory;
        this.context = context;
    }

   /*public void addItem(Item item){
        boolean same = true;
        for(int i = 0; i < inventory.size(); i++){
            if(item.getImageID() == inventory.get(i).getImageID()){
                inventory.get(i).setAmount(inventory.get(i).getAmount() + 1);
                same = false;
                break;
            }
        }

        if(!same){
            inventory.add(item);
        }

        notifyDataSetChanged();
    }*/

    public void removeItem(Item item){
        boolean negative = false;
        int index = 0;
        for(int i = 0; i < inventory.size(); i++){
            if(item.getImageID() == inventory.get(i).getImageID()){
                inventory.get(i).setAmount(inventory.get(i).getAmount() - 1);
                negative = inventory.get(i).getAmount() <= 0;
                index = i;
                mpf.p.inventory.get(i).setAmount(inventory.get(i).getAmount());
                break;
            }
        }

        if(negative){
            inventory.remove(index);
            mpf.p.inventory.remove(index);
            if(mpf.p.inventory.isEmpty()){
              //empty langs
            }
        }
         mpf.writePetFile();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return inventory.size();
    }

    @Override
    public Object getItem(int position) {
        return inventory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return inventory.get(position).getImageID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        final int pos = position;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_inventory, null);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

         view.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                   // POP UP WHAT U WANNA DO WITH ITEM
                   LayoutInflater li = LayoutInflater.from(context);
                   View promptView = li.inflate(R.layout.item_description, null);
                   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                   alertDialogBuilder.setView(promptView);

                   TextView viewItemDescription = (TextView) promptView.findViewById(R.id.viewItemDescription);
                   TextView viewAmount = (TextView) promptView.findViewById(R.id.viewValue);

                   viewAmount.setText(inventory.get(pos).getAmount() + " left");
                   viewItemDescription.setText(inventory.get(pos).getDescription());

                   ImageView itemImage = (ImageView) promptView.findViewById(R.id.itemImage);
                   itemImage.setImageResource(inventory.get(pos).getImageID());

                   alertDialogBuilder.setCancelable(false).setPositiveButton("USE ITEM",
                           new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                /*
                                * ITEM IS USED, REMOVE FROM INVENTORY
                                * */
                                     Item item = inventory.get(pos);
                                     if(item.getName().equals("Bit Bento")){
                                          // increase fullness bar
                                     }else if(item.getName().equals("Apple Juice")){
                                          // increase fullness bar
                                     }else if(item.getName().equals("Heal Potion")){
                                          // increase health
                                     }else if(item.getName().equals("Sweet Nibble")){
                                          // increase fullness and change status
                                     }
                                     removeItem(item);
                                     notifyDataSetChanged();
                                }
                           }
                   ).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                             // Do nothing
                             dialog.dismiss();
                        }
                   }).setMessage(inventory.get(pos).getName());

                   AlertDialog alertDialog = alertDialogBuilder.create();
                   alertDialog.show();
              }
         });
        Item item = inventory.get(position);
        viewHolder.item.setImageResource(item.getImageID());

        return view;
    }

    class ViewHolder{
        ImageView item;

        public ViewHolder(View view){
            item = (ImageView) view.findViewById(R.id.itemView);
        }
    }
}
