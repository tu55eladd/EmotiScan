package com.example.sigurd.emotiscan.fragments;

/**
 * Created by Sigurd on 24.01.2017.
 */

public class EmotionData {
    public String name;
    public float value;

    public EmotionData(String name, float value){
        this.name = name;
        this.value = value;
    }

    public String toString(){
        return String.format("Emotion: name: %s , value: %f", this.name, this.value);
    }

}
