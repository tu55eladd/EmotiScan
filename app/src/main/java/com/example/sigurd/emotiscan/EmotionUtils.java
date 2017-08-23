package com.example.sigurd.emotiscan;

import android.graphics.PointF;

import com.affectiva.android.affdex.sdk.detector.Face;
import com.example.sigurd.emotiscan.fragments.EmotionData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sigurd on 23.01.2017.
 */

public class EmotionUtils {

    public static String[] emotions = {"ANGER","CONTEMPT","DISGUST","ENGAGEMENT","FEAR","JOY","SADNESS","SURPRISE","VALENCE"};
    public static Map<Integer, String> emotionIndexes = new HashMap<>();

    public static void initializeEmotions() {
        emotionIndexes.put(0,emotions[0]);
        emotionIndexes.put(1,emotions[1]);
        emotionIndexes.put(2,emotions[2]);
        emotionIndexes.put(3,emotions[3]);
        emotionIndexes.put(4,emotions[4]);
        emotionIndexes.put(5,emotions[5]);
        emotionIndexes.put(6,emotions[6]);
        emotionIndexes.put(7,emotions[7]);
        emotionIndexes.put(8,emotions[8]);
    }

    public static EmotionData getMostDominantEmotion(Face face){
        //TODO: Maybe use same list again instead of constructing each time ?
        List<EmotionData> emotionValues = extractEmotionsFromFace(face);
        return getMostDominantEmotion(emotionValues);
    }

    public static List<EmotionData> getMostDominantEmotions(Face face, int numberOfEmotions){
        List<EmotionData> emotionValues = extractEmotionsFromFace(face);
        return getMostDominantEmotions(emotionValues, numberOfEmotions);
    }

    private static List<EmotionData> extractEmotionsFromFace(Face face){
        ArrayList<EmotionData> emotionValues = new ArrayList();
        emotionValues.add( new EmotionData( emotions[0], face.emotions.getAnger()) );
        emotionValues.add( new EmotionData( emotions[1], face.emotions.getContempt()) );
        emotionValues.add( new EmotionData( emotions[2], face.emotions.getDisgust()) );
        emotionValues.add( new EmotionData( emotions[3], face.emotions.getEngagement()) );
        emotionValues.add( new EmotionData( emotions[4], face.emotions.getFear()) );
        emotionValues.add( new EmotionData( emotions[5], face.emotions.getJoy()) );
        emotionValues.add( new EmotionData( emotions[6], face.emotions.getSadness()) );
        emotionValues.add( new EmotionData( emotions[7], face.emotions.getSurprise()) );
        emotionValues.add( new EmotionData( emotions[8], face.emotions.getValence()) );
        return emotionValues;
    }


    public static List<EmotionData> getMostDominantEmotions(List<EmotionData> emotionValues, int numberOfEmotions){
        Collections.sort(emotionValues, new Comparator<EmotionData>() {
            @Override
            public int compare(EmotionData lhs, EmotionData rhs) {
                if(lhs.value == rhs.value){
                    return 0;
                } else if(lhs.value < rhs.value){
                    return 1;
                } else {
                    return -1;
                }

            }
        });
        return emotionValues.subList(0, numberOfEmotions);
    }

    public static EmotionData getMostDominantEmotion(List<EmotionData> emotionValues){
        int index = 0;
        float max = emotionValues.get(0).value;

        for (int i = 0; i < emotionValues.size(); i++) {
            float nextValue = emotionValues.get(i).value;
            if( max < nextValue ){
                index = i;
                max = nextValue;
            }
        }

        return new EmotionData(emotionIndexes.get(index), max);
    }

    public static PointF getTopFacePoint(Face face){
        /* Find lowest facePoint */
        PointF[] facePoints = face.getFacePoints();
        PointF topPoint =  facePoints[0];
        for (PointF point : facePoints){
            if(topPoint.y > point.y) // Inverse y-coordinates
                topPoint.y = point.y;
        }
        return topPoint;
    }


    public static List<EmotionSetting> makeSortedListFromMap(Map<String, EmotionSetting> mapData){
        /* Need to sort list because it does not ArrayList constructor does not
            guarantee same results every time*/
        List<EmotionSetting> listData = new ArrayList<>();
        for (int i = 0; i < emotions.length; i++) {
            listData.add(mapData.get( emotions[i] ));
        }

        return listData;
    }
}
