package com.example.sigurd.emotiscan;

/**
 * Created by Sigurd on 17.01.2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.sigurd.emotiscan.fragments.MainFragment;
import com.example.sigurd.emotiscan.fragments.SettingsFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static String TAG = "SectionsPagerAdapter: ";

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a MainFragment (defined as a static inner class outside).
        if(position == 0){
            Log.w(TAG, "Getting instace of fragment with position:"+position);
            return MainFragment.newInstance(position + 1);
        }
        else {
            Log.w(TAG, "Getting instace of fragment with position:"+position);
            return SettingsFragment.newInstance(position +1);
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }
}
