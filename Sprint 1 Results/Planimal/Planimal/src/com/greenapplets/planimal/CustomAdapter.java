/*
*
* Copyright (c) 2015, Green Applets
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification,
* are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice,
* this list of conditions and the following disclaimer.
*
* 2. Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation
* and/or other materials provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
* IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
* INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
* BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
* OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* Author: Yohannah Bautista, Nicle Vynique Bedia, Algina Castillo
*
* This is a course requirement for CS 192 Software Engineering II
* under the supervision of Asst. Prof. Ma. Rowena C. Solamo
* of the Department of Computer Science, College of Engineering,
* University of the Philippines, Diliman for the AY 2014-2015
*
*
* Code History:
* 02/10/15 - Yohannah Bautista
*    Initial code
* 02/11/15 - Algina Castillo
*    Added the delete method
*
*/

package com.greenapplets.planimal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter implements ListAdapter {
     private ArrayList<String> list = new ArrayList<String>();
     private Context context;
     private DAO dao;
     private List<Task2> daoList;

     public CustomAdapter(ArrayList<String> list, Context context, List<Task2> daoList) {
          this.list = list;
          this.context = context;
          this.daoList = daoList;
          dao = new DAO(this.context);
     }

     /*
     * method getCount() gets the number of elements in the list
     * @return int is the number of elements in the list
     * */
     @Override
     public int getCount() {
          return list.size();
     }


     /*
     * method getItem(pos) gets the object at the specified position
     * @param pos is the index of the item to be taken
     * @return object is the object in the position specified
     * */
     @Override
     public Object getItem(int pos) {
          return list.get(pos);
     }


     /*
     * method getItemId() gets the id of the object in the list
     * @param pos is the position of the item
     * */
     @Override
     public long getItemId(int pos) {
          return 0;
     }

     /*
     * method getView(int, View, ViewGroup) implements the delete button and edit button features
     * */
     @Override
     public View getView(final int position, View convertView, ViewGroup parent) {
          View view = convertView;
          if (view == null) {
               LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               view = inflater.inflate(R.layout.list_task, null);
          }

          //Handle TextView and display string from your list
          TextView listItemText = (TextView) view.findViewById(R.id.list_item_string);
          listItemText.setText(list.get(position));

          //Handle buttons and add onClickListeners
          Button deleteBtn = (Button) view.findViewById(R.id.delete_btn);
          Button editBtn = (Button) view.findViewById(R.id.edit_btn);

          // add actions for delete button
          deleteBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    delete(position);
                    list.remove(position); //or some other task
                    notifyDataSetChanged();

               }
          });

          editBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    notifyDataSetChanged();
               }
          });

          return view;
     }

     /*
     * method delete(int) deletes the task from the taskList and from the savefile
     * @param index is the index of the task to be deleted in the list
     * */
     public void delete(int index) {
          daoList.remove(index);
          try {
               dao.writeToFile(this.context);
          } catch (Exception e) {
               e.printStackTrace();
          }

     }
}