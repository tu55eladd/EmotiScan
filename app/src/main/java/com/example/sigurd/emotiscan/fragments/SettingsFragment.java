package com.example.sigurd.emotiscan.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sigurd.emotiscan.EmotionSetting;
import com.example.sigurd.emotiscan.EmotionUtils;
import com.example.sigurd.emotiscan.MainActivity;
import com.example.sigurd.emotiscan.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Sigurd on 25.01.2017.
 */

public class SettingsFragment extends Fragment {

    private static String ARG_SECTION_NUMBER = "section_number";

    ListView emotionListView;
    EmotionListAdapter emotionListAdapter;
    MainActivity activity;

    public static SettingsFragment newInstance(int sectionNumber){
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        activity = (MainActivity) getActivity();
        emotionListView = (ListView) rootView.findViewById(R.id.emotionListView);
        List<EmotionSetting> emotionSettings = EmotionUtils.makeSortedListFromMap(activity.getEmotionSettings());

        emotionListAdapter = new EmotionListAdapter(getContext(), emotionSettings);
        emotionListView.setAdapter(emotionListAdapter);

        return rootView;
    }




    public class EmotionListAdapter extends ArrayAdapter<EmotionSetting> {

        public EmotionListAdapter(Context context, List<EmotionSetting> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View view = convertView;

            if(view == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.setting_list_item, null);
            }

            /* Get data */
            final EmotionSetting setting = this.getItem(position);

            final Switch detectionSwitch = (Switch) view.findViewById(R.id.detectEmotionSwitch);
            detectionSwitch.setChecked(setting.getIsOn());
            TextView textView = (TextView) view.findViewById(R.id.emotionName);
            textView.setText(setting.getName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detectionSwitch.toggle();
                    setting.setIsOn(!setting.getIsOn());
                }
            });

            return view;
        }

    }
}
