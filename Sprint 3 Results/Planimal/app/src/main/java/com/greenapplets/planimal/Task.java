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
* 03/14/15 - Yohannah Bautista
*    Revised the code
*    Renamed from Task2 to Task
*/

package com.greenapplets.planimal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Task implements Comparable<Task>{
     String name;
     String PIC;
     String venue;
     String password;
     Calendar deadline;
     boolean expired=false;

     public Task(){
          name = new String();
          PIC = new String();
          venue = new String();
          password = new String();
          deadline = Calendar.getInstance();
     }

     public Task(String name, String PIC, String venue, String password, Calendar deadline){
          this.name = name;
          this.PIC = PIC;
          this.venue = venue;
          this.password = password;
          this.deadline = deadline;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getPIC() {
          return PIC;
     }

     public void setPIC(String PIC) {
          this.PIC = PIC;
     }

     public String getVenue() {
          return venue;
     }

     public void setVenue(String venue) {
          this.venue = venue;
     }

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public Calendar getDeadline() {
          return deadline;
     }

     public String getStringDeadline(){
          DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
          String strDate = dateFormat.format(deadline.getTime());
          return strDate;
     }

     public void setDeadline(Calendar deadline) {
          this.deadline = deadline;
     }


     public Calendar convert(String date) throws ParseException {
          DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
          Calendar cal = Calendar.getInstance();
          cal.setTime(df.parse(date));
          return cal;
     }

     /*
    * method compareTo(Task2) is used in sorting the schedule
    * @param task2 is the task to be compared to this task
    * @return int is the relative difference of these two values
    * */
     @Override
     public int compareTo(Task task2) {

          return (this.deadline.compareTo(task2.deadline));
     }
}
