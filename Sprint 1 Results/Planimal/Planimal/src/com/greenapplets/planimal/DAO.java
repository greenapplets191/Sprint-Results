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
* 11/21/14 - Algina Castillo
*    Initial Code
* 02/11/15 - Nicle Vynique Bedia, Algina Castillo
*    Changed writing mode from append to private
*
*/


package com.greenapplets.planimal;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.ContextWrapper;

public class DAO extends ContextWrapper {
     private static final String DATA = "PlanimalSave.txt";
     static File file = new File(DATA); /* file where the tasks are written */
     static List<Task2> taskList = new ArrayList<Task2>();

     public DAO(Context base) {
          super(base);
     }

     /*
      * method writeToFile(Context) saves the taskList to a save file.
      */
     public static void writeToFile(Context ctx) {
          try {
               FileOutputStream fos = ctx.openFileOutput(DATA, Context.MODE_PRIVATE);

               for (int i = 0; i < taskList.size(); i++) {
                    Task2 task = taskList.get(i);
                    String temp = dateToString(task.deadline);
                    String data = task.taskname + ";" + task.desc + ";" + task.venue
                         + ";" + temp + ";" + task.pic.getPICName() + ";"
                         + task.pic.getPassword();
                    fos.write(data.getBytes());
                    fos.write(13);
                    fos.write(10);
               }
               fos.close();

          } catch (IOException e) {
               e.printStackTrace();
          }

     }

     /*
      * method readFromFile(Context) retrieves the tasks from the save file
      */
     public static void readFromFile(Context ctx) throws IOException,
          ParseException {
          BufferedReader br = null;
          FileInputStream fis = null;
          if (file != null) {
               try {
                    fis = ctx.openFileInput(DATA);
                    br = new BufferedReader(new InputStreamReader(fis));
                    String line = br.readLine();
                    while (line != null) {
                         String[] data = line.split(";");
                         String dltemp = data[3];
                         Task2 temp = new Task2();
                         Calendar datetemp = null;
                         try {
                              datetemp = temp.convert(dltemp);
                         } catch (ParseException e) {
                              e.printStackTrace();
                         }
                         Task2 b = new Task2(data[0], data[1], data[2],
                              datetemp, data[4], data[5]);
                         taskList.add(b);
                         line = br.readLine();
                    }
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

     /*
      * method dateToString(Calendar) converts the parameter Calendar deadline from Date to String
      * @param date is the date instance to be converted to a String
      * @return formatted is the String instance of the date
      */
     public static String dateToString(Calendar date) {
          String formatted = ((date.get(Calendar.MONTH) + 1) + "/"
               + date.get(Calendar.DAY_OF_MONTH) + "/"
               + date.get(Calendar.YEAR));

          return formatted;
     }

}
