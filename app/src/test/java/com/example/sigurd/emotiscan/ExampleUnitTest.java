package com.example.sigurd.emotiscan;

import com.affectiva.android.affdex.sdk.detector.Face;
import com.example.sigurd.emotiscan.fragments.EmotionData;
import com.example.sigurd.emotiscan.fragments.SettingsFragment;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testEmotionUtil(){
        ArrayList<EmotionData> emotionValues = new ArrayList<>();
        emotionValues.add( new EmotionData( EmotionUtils.emotions[0] ,2.1f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[1] ,2.2f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[2] ,2.3f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[3] ,2.4f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[4] ,2.5f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[5] ,2.6f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[6] ,2.7f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[7] ,2.8f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[8] ,2.9f) );

        EmotionUtils.initializeEmotions();

        EmotionData emotionData = EmotionUtils.getMostDominantEmotion(emotionValues);

        assertEquals(emotionData.value, 2.9f, 0.01);
    }

    @Test
    public void testGetMostDominantEmotions(){
        ArrayList<EmotionData> emotionValues = new ArrayList<>();
        emotionValues.add( new EmotionData( EmotionUtils.emotions[0] ,2.1f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[1] ,2.2f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[2] ,2.3f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[3] ,2.4f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[4] ,2.5f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[5] ,2.6f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[6] ,2.7f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[7] ,2.8f) );
        emotionValues.add( new EmotionData( EmotionUtils.emotions[8] ,2.9f) );

        EmotionUtils.initializeEmotions();

        List<EmotionData> emotionDatas = EmotionUtils.getMostDominantEmotions(emotionValues, 2);

        assertEquals(2, emotionDatas.size());
        assertEquals(EmotionUtils.emotions[8], emotionDatas.get(0).name);
        assertEquals(EmotionUtils.emotions[7], emotionDatas.get(1).name);

    }

}