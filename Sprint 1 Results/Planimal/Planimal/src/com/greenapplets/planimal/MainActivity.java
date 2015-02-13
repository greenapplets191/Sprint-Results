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
* 11/19/14 - Yohannah Bautista
*    Initial Prototype
*    Add Task function (not checking for correct input, front end)
* 11/21/14 - Nicle Vynique Bedia
*    Added the writing and reading to file function
*    Add Task function (Added Task are now saved into a save file)
* 02/09/15 - Yohannah Bautista, Nicle Vynique Bedia, Algina Castillo
*    Added the sorting function
*    Added the checking for correct input function
* 02/11/15 - Yohannah Bautista, Nicle Vynique Bedia, Algina Castillo
*    Added the Delete Task function
*
*
*/


package com.greenapplets.planimal;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import android.annotation.TargetApi;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
     private static final String DATA = "PlanimalSave.txt";
     public CustomAdapter ca;

     final Context context = this;

     Button add;
     Button toPet;
     Button toSchedule;
     Button toShop;
     Button addPIC;

     boolean isPromptUp = false;

     DAO dao = new DAO(MainActivity.this);
     File file = new File(DATA);

     /* method onCreate(Bundle) adds listener to the buttons, makes it possible to add tasks to the list*/
     @Override
     @TargetApi(11)
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          getSupportActionBar().setDisplayShowHomeEnabled(true);
          getSupportActionBar().setIcon(R.drawable.ic_launcher);
          dao.taskList = new ArrayList<Task2>();
          add = (Button) findViewById(R.id.addTask);
          readFile();
          populateList();
          /* add listener to add button */
          add.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    /* TODO Auto-generated method stub */
                    if (!isPromptUp) {
                         isPromptUp = true;

                         LayoutInflater li = LayoutInflater.from(context);
                         View promptView = li
                              .inflate(R.layout.prompt_add, null);

                         AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                              context);

                         alertDialogBuilder.setView(promptView);

                         final EditText editPIC = (EditText) promptView
                              .findViewById(R.id.editPIC);
                         final EditText editName = (EditText) promptView
                              .findViewById(R.id.editName);
                         final EditText editPassword = (EditText) promptView
                              .findViewById(R.id.editPassword);
                         final EditText editVenue = (EditText) promptView
                              .findViewById(R.id.editVenue);
                         final DatePicker deadline = (DatePicker) promptView
                              .findViewById(R.id.datePicker1);

                         deadline.setCalendarViewShown(false);

                         alertDialogBuilder
                              .setCancelable(false)
                              .setPositiveButton(
                                   "OK",
                                   new DialogInterface.OnClickListener() {
                                        @SuppressWarnings("deprecation")
                                        public void onClick(
                                             DialogInterface dialog,
                                             int id) {


                                             Calendar date = Calendar
                                                  .getInstance();
                                             date.set(Calendar.YEAR,
                                                  deadline.getYear());
                                             date.set(Calendar.MONTH,
                                                  deadline.getMonth());
                                             date.set(Calendar.DAY_OF_MONTH,
                                                  deadline.getDayOfMonth());
                                             date.set(Calendar.HOUR_OF_DAY, 23);

                                             Task2 nuTask = new Task2(
                                                  editName.getText()
                                                       .toString(),
                                                  " ",
                                                  editVenue.getText()
                                                       .toString(),
                                                  date,
                                                  editPIC.getText()
                                                       .toString(),
                                                  editPassword
                                                       .getText()
                                                       .toString());
                                                   /*
                                                   * Error: Invalid input
                                                   */
                                             if (nuTask.isInvalid(hasDuplicate(nuTask))) {
                                                  //Kailangan ng error message dito YoYo
                                                  //like fill in all the fields required, make sure it isn't in past and no duplicates
                                                  Toast.makeText(getApplicationContext(), "Invalid Entry", Toast.LENGTH_SHORT).show();
                                             } else {
                                                  dao.taskList.add(nuTask);
                                                  Collections.sort(dao.taskList);
                                                  populateList();
                                                  writeFile();

                                             }

                                             isPromptUp = false;
                                        }
                                   })
                              .setNegativeButton(
                                   "Cancel",
                                   new DialogInterface.OnClickListener() {
                                        public void onClick(
                                             DialogInterface dialog,
                                             int id) {
                                             isPromptUp = false;
                                             dialog.cancel();
                                        }
                                   });

                         AlertDialog alertDialog = alertDialogBuilder.create();

                         alertDialog.show();
                    }
               }
          });
     }

     /*
      * method populateList() adds the tasks to the list and integrate it the schedule so that the user can view the newly added tasks as well
      * the list stores all the tasks in the schedule
      */
     public void populateList() {
          ArrayList<String> list = new ArrayList<String>();

          int size = dao.taskList.size();

          for (int i = 0; i < size; i++) {
               list.add(dao.taskList.get(i).getTask());
          }

          ca = new CustomAdapter(list, this, dao.taskList);

          //handles listview and assign adapter
          ListView lView = (ListView) findViewById(R.id.taskList);
          lView.setAdapter(ca);
     }


     /*
      * method onCreateOptionsMenu(Menu) Inflate the menu; this adds items to the action bar if it is present.
      */
     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater().inflate(R.menu.main, menu);
          return true;
     }

     /* 
      * method onOptionsItemSelected(MenuItem)Handles action bar item clicks. The action bar will
      * automatically handle clicks on the Home/Up button, so long
      * as you specify a parent activity in AndroidManifest.xml.
      */
     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
          int id = item.getItemId();
          if (id == R.id.action_settings) {
               return true;
          }
          return super.onOptionsItemSelected(item);
     }

     /* 
      * DAO reference
      * method readFile() is for loading the save file
      */
     public void readFile() {
          try {
               dao.readFromFile(MainActivity.this);
          } catch (IOException io) {

          } catch (ParseException pe) {

          }
     }

     /* 
      * DAO reference
      * method writeFile() is for saving the current schedule into a file
      */
     public void writeFile() {
          dao.writeToFile(MainActivity.this);
     }


     /*
      * checks if task is already in the schedule, i.e. duplicate,  is therefore an invalid entry
      */
     public boolean hasDuplicate(Task2 task) {
          for (int i = 0; i < dao.taskList.size(); i++) {
               if (dao.taskList.get(i).duplicate(task)) {
                    return true;
               }
          }
          return false;

     }


}
