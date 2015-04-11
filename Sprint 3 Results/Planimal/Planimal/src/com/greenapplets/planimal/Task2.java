package com.greenapplets.planimal;

/*
 *   Task.java is a Transfer Object Class.
 *   It defines the attributes of Person-in-charge entity and provides the methods needed such as setters/constructors and helping method like convert().
 *    Author: Algina Castillo
 */

import java.util.*;
import java.text.*;

public class Task2 implements Comparable<Task2>{
     String taskname;
     String desc;
     String venue;
     Calendar deadline;
     PersonInCharge pic;

     public Task2() {

     }

     public Task2(String taskname, String desc, String venue,
               Calendar deadline, String personIC, String password) {
          this.taskname = taskname;
          this.desc = desc;
          this.deadline = deadline;
          this.venue = venue;
          this.pic = new PersonInCharge(personIC, password);
     }

     /*
      * convert() method parse the given String to Date
      * @param date is the string to be parsed into Date
      * @return dd is the Date form or value of parameter date
      */
     public Calendar convert(String date) throws ParseException {
          DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
          Calendar cal = Calendar.getInstance();
          cal.setTime(df.parse(date));
          cal.add(Calendar.MONTH, 1);
          return cal;
     }
     /*
      * used in displaying the task after it is retrieved
      */
     public String getTask() {
          String task = new String();
          String days[] = { "Sunday", "Monday", "Tuesday", "Wednesday",
                    "Thursday", "Friday", "Saturday" };
          int year = deadline.get(Calendar.YEAR);
          int month = deadline.get(Calendar.MONTH);
          int day = deadline.get(Calendar.DAY_OF_MONTH);
          task = month + "/" + day + "/" + year + " - "
                    + days[deadline.get(Calendar.DAY_OF_WEEK) - 1] + "\n"
                    + taskname + "-" + venue + "\n";

          return task;
     }

    @Override
    public int compareTo(Task2 task2){
        return(this.deadline.compareTo(task2.deadline));
    }
}
