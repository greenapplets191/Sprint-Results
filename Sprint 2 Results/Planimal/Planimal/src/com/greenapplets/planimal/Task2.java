

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
*    Initial code
* 02/09/15 - Algina Castillo
*    Added method compareTo(Task2) for sorting
*    Added method duplicate(Task2)
*    Added method isInPast()
*    Added method isInvalid(Boolean);
*
*/
package com.greenapplets.planimal;


import java.util.*;
import java.text.*;

public class Task2 implements Comparable<Task2> {
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
          return cal;
     }

     /*
      * getTask() method is used in displaying the task after it is retrieved
      * @return task is the String representation of this task
      */
     public String getTask() {
          String task = new String();
          String days[] = {"Sunday", "Monday", "Tuesday", "Wednesday",
               "Thursday", "Friday", "Saturday"};
          int year = deadline.get(Calendar.YEAR);
          int month = deadline.get(Calendar.MONTH) + 1;
          int day = deadline.get(Calendar.DAY_OF_MONTH);
          task = month + "/" + day + "/" + year + " - "
               + days[deadline.get(Calendar.DAY_OF_WEEK) - 1] + "\n"
               + taskname + "-" + venue + "\n";

          return task;
     }

     /*
     * method compareTo(Task2) is used in sorting the schedule
     * @param task2 is the task to be compared to this task
     * @return int is the relative difference of these two values
     * */
     @Override
     public int compareTo(Task2 task2) {

          return (this.deadline.compareTo(task2.deadline));
     }

     /*
     * method duplicate(Task2) checks if the specified task already exists
     * @param task is the task to be checked
     * @return true if the task has a duplicate. False otherwise
     *
     * */
     public boolean duplicate(Task2 task) {
          return (this.taskname.compareTo(task.taskname) == 0 && this.venue.compareTo(task.venue) == 0 && this.desc.compareTo(task.desc) == 0 && this.deadline.compareTo(task.deadline) == 0 && this.pic.same(task.pic));
     }

     /*
      * method isInPast() checks if this task is in past with today as reference
      * @return false if the task's deadline is today or in the future
      */

     public boolean isInPast() {
          Calendar today = Calendar.getInstance();
          return today.after(this.deadline);
     }

     /*
     * method isInvalid(boolean) checks if this task is invalid entry, i.e. has duplicate/s , is in past, or has empty field/s(except for venue)
     * @return true if invalid
     * */
     public boolean isInvalid(boolean hasDuplicate) {
          return (this.taskname.equals("") || this.pic.getPICName().equals("") || this.pic.getPassword().equals("") || hasDuplicate || isInPast());
     }

}
