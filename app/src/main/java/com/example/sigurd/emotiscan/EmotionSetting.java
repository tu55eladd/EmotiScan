package com.example.sigurd.emotiscan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sigurd on 25.01.2017.
 */

public class EmotionSetting {

    private String name;
    private boolean isOn;

    private EmotionSetting(String name, boolean isOn){
        this.name = name;
        this.isOn = isOn;
    }

    public void setIsOn(boolean value){
        this.isOn = value;
    }

    public String getName(){
        return this.name;
    }

    public boolean getIsOn(){
        return this.isOn;
    }

    public static Map<String, EmotionSetting> getListWithAllEmotionSettings(boolean defaultValue){
        Map<String, EmotionSetting> settings = new HashMap();

        for (int i = 0; i < EmotionUtils.emotions.length; i++) {
            settings.put( EmotionUtils.emotions[i],new EmotionSetting( EmotionUtils.emotions[i], defaultValue));
        }

        return settings;
    }


}
