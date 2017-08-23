package com.example.sigurd.emotiscan;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sigurd.emotiscan.fragments.MenuFragment;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<String, EmotionSetting> emotionSettings = EmotionSetting.getListWithAllEmotionSettings(true);
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        // Set up the ViewPager with the sections adapter.

        if(savedInstanceState == null){
            initializeFirstFragment();
        }

        //mViewPager = (ViewPager) findViewById(R.id.container);
        //mViewPager.setAdapter(mSectionsPagerAdapter);

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

    public Map<String, EmotionSetting> getEmotionSettings(){
        return emotionSettings;
    }

    public void initializeFirstFragment(){
        fragment = MenuFragment.newInstance(0);
        FragmentManager fm = getSupportFragmentManager();
        fm
                .beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }

}
