package com.greenapplets.planimal;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    public CustomGridAdapter(ArrayList<Item> inventory, Context context) {
        this.inventory = inventory;
        this.context = context;
        /*
        Resources res = context.getResources();
        String[] totalItemNames = res.getStringArray(R.array.item_names);
        String[] itemDescriptions = res.getStringArray(R.array.item_descriptions);
        list = new Item[totalItemNames.length];

        int[] images = {R.drawable.sicon_bit_bento,R.drawable.sicon_apple_juice, R.drawable.sicon_sweet_nibble,
                R.drawable.sicon_heal_potion};

        for(int i = 0; i < totalItemNames.length; i++){
            Item  item = new Item(images[i], totalItemNames[i]);
            item.setDescription(itemDescriptions[i]);
            list[i] = item;
        }
        */
    }

    public void addItem(Item item){
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
    }

    public void removeItem(Item item){
        boolean negative = false;
        int index = 0;
        for(int i = 0; i < inventory.size(); i++){
            if(item.getImageID() == inventory.get(i).getImageID()){
                inventory.get(i).setAmount(inventory.get(i).getAmount() - 1);
                negative = inventory.get(i).getAmount() <= 0;
                index = i;
                break;
            }
        }

        if(negative){
            inventory.remove(index);
        }

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

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_inventory, null);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // POP UP WHAT U WANNA DO WITH ITEM
                    Dialog dialog = new Dialog(context);

                    dialog.setContentView(R.layout.view_task);

                    TextView viewTaskName = (TextView) dialog.findViewById(R.id.viewTaskName);
                    TextView viewDeadline = (TextView) dialog.findViewById(R.id.viewDeadline);
                    TextView viewPIC = (TextView) dialog.findViewById(R.id.viewPIC);
                    TextView viewVenue = (TextView) dialog.findViewById(R.id.viewVenue);

                    viewTaskName.setText("Item name");
                    viewDeadline.setText("Item description");
                    viewPIC.setText("Good for what");
                    viewVenue.setText("Amount");

                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setTitle("Details");
                    dialog.show();
                }
            });


            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

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
