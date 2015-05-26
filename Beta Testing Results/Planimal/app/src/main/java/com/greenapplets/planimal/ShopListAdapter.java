package com.greenapplets.planimal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by USER on 5/3/2015.
 */
public class ShopListAdapter extends BaseAdapter implements ListAdapter{
     private ArrayList<Item> itemList;
     private Context context;
     private MyPetFile mpf= MainActivity.mpf;


     public ShopListAdapter (ArrayList<Item> itemList, Context context) {
          this.context = context;
          this.itemList = itemList;
          // mpf= new MyPetFile(context);
     }

     @Override
     public int getCount() {
          return itemList.size();
     }

     @Override
     public Object getItem(int position) {
          return itemList.get(position);
     }

     @Override
     public long getItemId(int position) {
          return itemList.get(position).getImageID();
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
          View view = convertView;
          ViewHolder viewHolder;
          final int pos = position;
          if (view == null) {
               LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               view = inflater.inflate(R.layout.list_item, null);



               TextView itemName = (TextView) view.findViewById(R.id.itemName);
               itemName.setText(itemList.get(pos).getName().toString());
               viewHolder = new ViewHolder(view);
               view.setTag(viewHolder);
          }
          else {
               viewHolder = (ViewHolder) view.getTag();
          }

          view.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptView = li.inflate(R.layout.prompt_buy, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    alertDialogBuilder.setView(promptView);

                    TextView viewItemDescription = (TextView) promptView.findViewById(R.id.viewItemDescription);
                    TextView viewAmount = (TextView) promptView.findViewById(R.id.viewValue);

                    viewAmount.setText("Price: " + itemList.get(pos).getValue() + " moji");
                    viewItemDescription.setText(itemList.get(pos).getDescription());

                    ImageView itemImage = (ImageView) promptView.findViewById(R.id.itemImage);
                    itemImage.setImageResource(itemList.get(pos).getImageID());



                    alertDialogBuilder.setCancelable(false).setPositiveButton("BUY",
                            new DialogInterface.OnClickListener() {

                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                      // communication with pet
                                      if(mpf.p.getMoney() >= itemList.get(pos).getValue()){
                                           mpf.p.setMoney(mpf.p.getMoney()-itemList.get(pos).getValue());
                                           mpf.writePetFile();
                                           try {
                                                mpf.readInventory();
                                           } catch (IOException e) {
                                                e.printStackTrace();
                                           } catch (ParseException e) {
                                                e.printStackTrace();
                                           }
                                          // mpf.myItems.add(itemList.get(pos));
                                           ArrayList<Item> ty=new ArrayList<Item>();
                                            ty=mpf.myItems;
                                           boolean exist=false;
                                           for(int y=0; y<ty.size(); y++) {
                                                if (ty.get(y).getName().compareTo(itemList.get(pos).getName())==0) {
                                                     mpf.p.inventory.get(y).setAmount(mpf.p.inventory.get(y).getAmount() + 1);
                                                     exist=true;
                                                     break;
                                                }
                                           }
                                           if(!exist){
                                                ty.add(itemList.get(pos));
                                           }
                                           mpf.p.addToInventory(ty);
                                           mpf.writeInventory();
                                           Toast.makeText(context, itemList.get(pos).getName()+ " is successfully added to your pet's inventory.", Toast.LENGTH_SHORT).show();
                                      }
                                      else{
                                           Toast.makeText(context, "Save more moji by working harder to avail this.", Toast.LENGTH_SHORT).show();
                                      }
                                      notifyDataSetChanged();
                                 }
                            }
                    ).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                              // Do nothing
                              dialog.dismiss();
                         }
                    }).setMessage(itemList.get(pos).getName());

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
               }
          });

          Item item = itemList.get(position);
          viewHolder.item.setImageResource(item.getImageID());

          /*  Intent intent=this.getIntent();
            Bundle bundle=intent.getExtras();
           Integer m= bundle.getInt("money");*/

          return view;
     }

     class ViewHolder{
          ImageView item;

          public ViewHolder(View view){
               item = (ImageView) view.findViewById(R.id.itemImage);
          }
     }
}