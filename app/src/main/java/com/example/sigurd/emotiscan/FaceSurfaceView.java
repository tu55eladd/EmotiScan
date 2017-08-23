package com.example.sigurd.emotiscan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.TextView;

import com.affectiva.android.affdex.sdk.detector.Face;
import com.example.sigurd.emotiscan.fragments.EmotionData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sigurd on 23.01.2017.
 */

public class FaceSurfaceView extends SurfaceView {

    private static String TAG = "FaceSurfaceView: ";
    private static List<Face> faces = new ArrayList<>();

    /* Component constants */
    private static float BAR_MARGIN = 5f;
    private static float BARS_TEXT_MARGIN = 15f;
    private static Paint BAR_FILL_EMPTY;
    private static Paint BAR_FILL_FULL;
    private static Paint TEXT_PAINT;
    private static float BAR_LENGTH = 25f;
    private static float BAR_HEIGHT = 8f;
    private static float COMPONENT_MARGIN_BOTTOM = 10f;
    private static float NUMBER_OF_BARS = 3f;
    private static float TEXT_SIZE = 25f;
    private static Rect BAR;

    /* Thresholds */
    private static float[] BAR_THRESHOLDS = { 1.0f, 10.0f, 50.0f };

    /* This might cause some threading issues ? */
    public FaceSurfaceView(Context context, AttributeSet attrs){
        super(context, attrs);
        initPaint();
        setWillNotDraw(false);
    }

    private static void initPaint(){
        BAR_FILL_EMPTY = new Paint();
        BAR_FILL_EMPTY.setStyle(Paint.Style.STROKE);
        BAR_FILL_EMPTY.setStrokeWidth(3f);
        BAR_FILL_EMPTY.setColor(Color.WHITE);

        BAR_FILL_FULL = new Paint();
        BAR_FILL_FULL.setStyle(Paint.Style.FILL_AND_STROKE);
        BAR_FILL_FULL.setStrokeWidth(3f);
        BAR_FILL_FULL.setColor(Color.WHITE);

        TEXT_PAINT = new Paint();
        TEXT_PAINT.setColor(Color.WHITE);
        TEXT_PAINT.setTextSize(TEXT_SIZE);
    }

    public static void setFaces(List<Face> _faces){
        faces = _faces;
    }

    @Override
    public void onDraw(Canvas canvas){
        if(faces.size() == 0) {
            Log.w(TAG, "NO faces found");
            return;
        };
        Log.w(TAG, "Face(s) found!");

        drawElements(canvas, faces.get(0));
    }

    private void drawElements(Canvas canvas, Face face){
        List<EmotionData> dominantEmotions = EmotionUtils.getMostDominantEmotions(face, 2);

        PointF startingPoint = new PointF(5f, BAR_HEIGHT + BAR_MARGIN + TEXT_SIZE);

        for (EmotionData emotion: dominantEmotions) {
            if(emotion.value < BAR_THRESHOLDS[0]) return;

            PointF barStartingPoint = new PointF(startingPoint.x, startingPoint.y);
            PointF textStartingPoint = new PointF(startingPoint.x , startingPoint.y - BARS_TEXT_MARGIN);

            drawFaceEmotionLabel(canvas, emotion.name, textStartingPoint);
            Log.w(TAG, emotion.toString());
            drawBars(canvas, barStartingPoint, emotion);

            startingPoint.y += TEXT_SIZE + BAR_HEIGHT + BAR_MARGIN;
        }

    }

    private void drawFaceEmotionLabel(Canvas canvas, String emotionName, PointF startingPoint){
        canvas.drawText(emotionName, startingPoint.x, startingPoint.y, TEXT_PAINT);
    };

    private void drawBars(Canvas canvas, PointF startingPoint, EmotionData emotionData){
        for (int i = 0; i < NUMBER_OF_BARS; i++) {
            drawBar(canvas, startingPoint, decideFill(i, emotionData.value));
            startingPoint.x += BAR_LENGTH + BAR_MARGIN;
        }
    }

    private Paint decideFill(int index, float emotionValue){
        if(emotionValue >= BAR_THRESHOLDS[index]){
            return BAR_FILL_FULL;
        }
        return BAR_FILL_EMPTY;
    }

    private void drawBar(Canvas canvas, PointF startingPoint, Paint paint){
        float left = startingPoint.x;
        float bottom = startingPoint.y;
        float right = left + BAR_LENGTH;
        float top = bottom - BAR_HEIGHT;
        canvas.drawRect(left, top, right, bottom, paint);
    }

}
