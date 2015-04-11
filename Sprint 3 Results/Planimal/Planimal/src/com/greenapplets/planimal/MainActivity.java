package com.greenapplets.planimal;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

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

public class MainActivity extends ActionBarActivity {
     private static final String DATA = "PlanimalSave.txt";

     final Context context = this;

     Button add;
     Button toPet;
     Button toSchedule;
     Button toShop;
     Button addPIC;

     boolean isPromptUp = false;

     DAO dao = new DAO(MainActivity.this);
     File file = new File(DATA);
     
     /*adds listener to the buttons*/
     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          getSupportActionBar().setDisplayShowHomeEnabled(true);
          getSupportActionBar().setIcon(R.drawable.ic_launcher);

          add = (Button) findViewById(R.id.addTask);
          dao.taskList = new ArrayList();
          try {
               readFile();
               populateList();
          } catch (IOException e1) {
               /* TODO Auto-generated catch block */
               e1.printStackTrace();
          } catch (ParseException e1) {
               /* TODO Auto-generated catch block */
               e1.printStackTrace();
          }

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
                                                           deadline.getMonth() + 1);
                                                   date.set(Calendar.DAY_OF_MONTH,
                                                           deadline.getDayOfMonth());

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
                                                   Calendar today = Calendar.getInstance();
                                                   Boolean invalidDL = today.after(date);

                                                   /*
                                                   * Error: Invalid input
                                                   */
                                                   if (editName.getText().toString().equals("") || editPIC.getText().toString().equals("") || editPassword.getText().toString().equals("") || invalidDL) {
                                                       //Kailangan ng error message dito YoYo
                                                   }
                                                   else {
                                                       dao.taskList.add(nuTask);
                                                       Collections.sort(dao.taskList);
                                                       populateList();

                                                       try {
                                                           writeFile(nuTask);
                                                       }
                                                       catch (IOException e) {
                                                           e.printStackTrace();
                                                       }
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
      * adds the tasks to the list and integrate it the schedule so that the user can view the newly added tasks as well 
      * the list stores all the tasks in the schedule
      */
     public void populateList() {
          String[] fill = new String[dao.taskList.size()];

          for (int i = 0; i < dao.taskList.size(); i++) {
               fill[i] = dao.taskList.get(i).getTask();
          }

          ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.list_task, fill);

          ListView list = (ListView) findViewById(R.id.taskList);
          list.setAdapter(adapter);

     }

     /* 
      * Inflate the menu; this adds items to the action bar if it is present.
      */
     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater().inflate(R.menu.main, menu);
          return true;
     }

     /* 
      * Handle action bar item clicks here. The action bar will
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
      * for loading the save file
      */
     public void readFile() throws IOException, ParseException {
          dao.readFromFile(MainActivity.this);
     }

     /* 
      * DAO reference
      * for saving the current schedule into a file 
      */
     public void writeFile(Task2 task) throws IOException {
          dao.writeToFile(MainActivity.this, task);
     }

}
