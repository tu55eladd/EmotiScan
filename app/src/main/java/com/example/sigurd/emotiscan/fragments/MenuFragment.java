package com.example.sigurd.emotiscan.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.sigurd.emotiscan.R;

/**
 * Created by Sigurd on 26.01.2017.
 */

public class MenuFragment extends Fragment {

    private static String ARG_SECTION_NUMBER = "section_number";
    private ImageButton videoScan;

    public static MenuFragment newInstance(int sectionNumber){
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_menus, container, false);
        ImageButton videoScan = (ImageButton) rootView.findViewById(R.id.videoScan);
        videoScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoVideoScanView();
            }
        });

        return rootView;
    }


    public void gotoVideoScanView(){
        FragmentManager fm = getFragmentManager();
        Fragment videoScanFragment = FaceScanContainerFragment.newInstance(0);
        fm
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_from_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right)
                .replace(R.id.container, videoScanFragment, null)
                .addToBackStack(null)
                .commit();
    }


}
