package com.example.sigurd.emotiscan.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sigurd.emotiscan.R;
import com.example.sigurd.emotiscan.SectionsPagerAdapter;

/**
 * Created by Sigurd on 26.01.2017.
 */

public class FaceScanContainerFragment extends Fragment {

    private static String TAG = "FaceScanContainerFrag: ";
    private static String ARG_SECTION_NUMBER;

    private ViewPager viewPager;
    private SectionsPagerAdapter pagerAdapter;

    public static FaceScanContainerFragment newInstance(int sectionNumber){
        FaceScanContainerFragment fragment = new FaceScanContainerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_face_scan_container, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        pagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        Log.w(TAG, "Creating view");
        return rootView;
    }

}
