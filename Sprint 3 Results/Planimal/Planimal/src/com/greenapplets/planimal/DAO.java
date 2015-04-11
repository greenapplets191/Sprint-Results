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
      * appends the newly added task to the save file.
      */
     
     public static void writeToFile(Context ctx, Task2 task) throws IOException {
          try {
               FileOutputStream fos = ctx.openFileOutput(DATA,
                         Context.MODE_PRIVATE);
               String temp = dateToString(task.deadline);
               String data = task.taskname + ";" + task.desc + ";" + task.venue
                         + ";" + temp + ";" + task.pic.getPICName() + ";"
                         + task.pic.getPassword();
               fos.write(data.getBytes());
               /* for line separator (CR, LF)*/
               /* for newline */
               fos.write(13);
               /* for newline */
               fos.write(10);
               fos.close();
          } catch (IOException e) {
               e.printStackTrace();
          }
     }
     /*
      * retrieves the tasks from the save file
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
      * converts the deadline from Date to String  
      */
     public static String dateToString(Calendar date) {
          String formatted = (date.get(Calendar.MONTH) + "/"
                    + date.get(Calendar.DAY_OF_MONTH) + "/"
                    + date.get(Calendar.YEAR));

          return formatted;
     }

}
