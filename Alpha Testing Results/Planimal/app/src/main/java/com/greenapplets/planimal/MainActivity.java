package com.greenapplets.planimal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;
/*
* I'm really sorry that it took a really long time to make this
* I wanted to remake everything because I realized I'd have a harder
* time coding if I just stuck to the code as it is, so I took the
* time to watch android tutorials from the start para di na magulo yung
* UI ;w; Hopefully it should be similar to the original code so putting in
* the DAO shouldn't be a problem? idk
* */
public class MainActivity extends FragmentActivity {
     ViewPager viewPager;
     public static DAO dao;
     {
          dao = new DAO(MainActivity.this);
     }


     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          viewPager = (ViewPager) findViewById(R.id.pager);

          FragmentManager fragmentManager = getSupportFragmentManager();
          viewPager.setAdapter(new PagerAdapter(fragmentManager));
     }


     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
          // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.menu_main, menu);
          return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
          // Handle action bar item clicks here. The action bar will
          // automatically handle clicks on the Home/Up button, so long
          // as you specify a parent activity in AndroidManifest.xml.
          int id = item.getItemId();

          //noinspection SimplifiableIfStatement
          if (id == R.id.action_settings) {
               return true;
          }

          return super.onOptionsItemSelected(item);
     }
}
