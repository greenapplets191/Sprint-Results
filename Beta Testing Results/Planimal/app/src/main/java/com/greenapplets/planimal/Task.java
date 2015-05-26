package com.greenapplets.planimal;

import android.widget.Toast;

import java.security.Key;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ASUS on 3/14/2015.
 */
public class Task implements Comparable<Task> {
    private byte[] key = "GApplets3YoGiNiq".getBytes();
    String name;
    String PIC;
    String venue;
    String password;
    Calendar deadline;
    boolean expired=false; //expired ay yung expired na na di nagawa; pag nagawa, past man o hindi, completed siya
    boolean completed= false; //

    public Task(){
        name = new String();
        PIC = new String();
        venue = new String();
        password = new String();
        deadline = Calendar.getInstance();
    }

    public Task(String name, String PIC, String venue, String pw, Calendar deadline){
        this.name = name;
        this.PIC = PIC;
        this.venue = venue;
        this.password = pw;
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
        return this.password;
    }

    public void setPassword(String password) {
         this.password =  password;
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
	
	
	 public String encrypt(String pw){

           byte[] encrypted = new byte[0];
           try {
              //  String key = "GApplets3YoGiNiq"; // 128 bit key
                SecretKeySpec aesKey = new SecretKeySpec(key, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, aesKey);
                encrypted = cipher.doFinal(pw.getBytes());
           }
           catch(Exception e){
                Toast.makeText(null, "Ex", Toast.LENGTH_SHORT).show();
           }
        return encrypted.toString();
	 }


     public boolean decrypt(String p){
          byte[] decrypted = new byte[0];
          try {// 128 bit key
               SecretKeySpec aesKey = new SecretKeySpec(key, "AES");
               Cipher cipher = Cipher.getInstance("AES");
               cipher.init(Cipher.DECRYPT_MODE, aesKey);
               decrypted = cipher.doFinal(this.password.getBytes());
               if( decrypted.toString().compareTo(p)==0){
                   return true;
               }
          }
          catch(Exception e){

          }
          return false;
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
