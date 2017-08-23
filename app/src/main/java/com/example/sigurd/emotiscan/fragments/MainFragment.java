package com.example.sigurd.emotiscan.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;
import com.example.sigurd.emotiscan.EmotionSetting;
import com.example.sigurd.emotiscan.EmotionUtils;
import com.example.sigurd.emotiscan.FaceSurfaceView;
import com.example.sigurd.emotiscan.MainActivity;
import com.example.sigurd.emotiscan.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static android.view.View.GONE;

/**
 * Created by Sigurd on 17.01.2017.
 */


/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment implements Detector.ImageListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private CameraDetector cameraDetector;
    private SurfaceView surfaceView;
    private FaceSurfaceView indicatorView;
    private FrameLayout surfaceViewWrapper;
    private Button startButton;
    private boolean detectorIsInitialized = false;
    private ProgressBar spinner;

    private String[] emotionStrings = {"Joy","Sadness","Anger","Contempt"};
    private int[] emotionColors = {Color.YELLOW, Color.BLUE, Color.RED, Color.rgb(85,26,139)};

    private String TAG = "MainFragment";


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        surfaceViewWrapper = (FrameLayout) rootView.findViewById(R.id.surfaceViewWrapper);
        indicatorView = (FaceSurfaceView) rootView.findViewById(R.id.indicatorView);
        startButton = (Button) rootView.findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDetector((TextView) v);
            }
        });
        spinner = (ProgressBar) rootView.findViewById(R.id.spinner);

        EmotionUtils.initializeEmotions();

        if(surfaceView == null){
            surfaceView = (SurfaceView) rootView.findViewById(R.id.surfaceView);
        }

        return rootView;
    }

    @Override
    public void onImageResults(List<Face> faces, Frame image, float timestamp){

        if(faces.size() == 0){
            return;
        }

        FaceSurfaceView.setFaces(faces);
        indicatorView.invalidate();
    }


    public CameraDetector.CameraEventListener getCameraEventListener(){
        return new CameraDetector.CameraEventListener(){
            public void onCameraSizeSelected(int width, int height, Frame.ROTATE rotation){
                int cameraPreviewWidth;
                int cameraPreviewHeight;

//cameraWidth and cameraHeight report the unrotated dimensions of the camera frames, so switch the width and height if necessary

                if (rotation == Frame.ROTATE.BY_90_CCW || rotation == Frame.ROTATE.BY_90_CW) {
                    cameraPreviewWidth = height;
                    cameraPreviewHeight = width;
                } else {
                    cameraPreviewWidth = width;
                    cameraPreviewHeight = height;
                }

                // retrieve the width and height of the ViewGroup object containing our SurfaceView
                // (in an actual application, we would want to consider the possibility that the mainLayout object may not have been sized yet)

                int layoutWidth = surfaceViewWrapper.getWidth();
                int layoutHeight = surfaceViewWrapper.getHeight();

                //compute the aspect Ratio of the ViewGroup object and the cameraPreview

                float layoutAspectRatio = (float)layoutWidth/layoutHeight;
                float cameraPreviewAspectRatio = (float)width/height;

                int newWidth;
                int newHeight;

                if (cameraPreviewAspectRatio > layoutAspectRatio) {
                    newWidth = layoutWidth;
                    newHeight =(int) (layoutWidth / cameraPreviewAspectRatio);
                } else {
                    newWidth = (int) (layoutHeight * cameraPreviewAspectRatio);
                    newHeight = layoutHeight;
                }

                //size the SurfaceView

                ViewGroup.LayoutParams params = surfaceView.getLayoutParams();
                params.height = newWidth;
                params.width = newHeight;

                Log.w(TAG, "Layoutparams: w, h" + params.height + " , " + params.width );

                surfaceView.setLayoutParams(params);
                makeSpinnerInVisible();
            }
        };
    }

    public void toggleDetector(TextView view){
        if(!detectorIsInitialized){
            initializeEmotionDetector();
            detectorIsInitialized = true;
        }

        if(cameraDetector.isRunning()){
            try {
                cameraDetector.stop();
                view.setText("Start");
            }
            catch (Exception e) {
                Log.w(TAG, "Catching camera stop");
            }
        }
        else{
            makeSpinnerVisible();
            try {
                setEmotionListeners();
                cameraDetector.start();
                view.setText("Stop");
            }
            catch (Exception e){
                Log.w(TAG, "Catching camera start");
            }
        }
    }

    interface Function {
        void call();
    }

    private void catchError(Function f){
        f.call();
    }

    private void initializeEmotionDetector(){

        Context context = getActivity();
        final int processRate = 5;
        try {
            cameraDetector = new CameraDetector(context, CameraDetector.CameraType.CAMERA_FRONT, surfaceView,1, Detector.FaceDetectorMode.LARGE_FACES);
        }
        catch (Exception e) {
            Log.w(TAG, "Catched cameraconstructor exception");
        }

        catchError(new Function() {
            @Override
            public void call() {cameraDetector.setMaxProcessRate(processRate);
            }
        });
        catchError(new Function() {
            @Override
            public void call() {cameraDetector.setSendUnprocessedFrames(false);
            }
        });
        final MainFragment self = this;
        catchError(new Function() {
            @Override
            public void call() {
                cameraDetector.setImageListener(self);
            }
        });
        catchError(new Function() {
            @Override
            public void call() {
                setEmotionListeners();
                //cameraDetector.setDetectAllEmotions(true);
            }
        });

        cameraDetector.setOnCameraEventListener(getCameraEventListener());

    }

    private void setEmotionListeners(){
        MainActivity activity = (MainActivity) getActivity();
        Map<String, EmotionSetting> settings = activity.getEmotionSettings();
        /* This kind of fragile, emotions in EmotionUtils are sorted alfabetically */
        cameraDetector.setDetectAnger( settings.get(EmotionUtils.emotions[0]).getIsOn() );
        cameraDetector.setDetectContempt( settings.get(EmotionUtils.emotions[1]).getIsOn() );
        cameraDetector.setDetectDisgust( settings.get(EmotionUtils.emotions[2]).getIsOn() );
        //cameraDetector.setDetectEngagement( settings.get(EmotionUtils.emotions[3]).getIsOn() );
        cameraDetector.setDetectFear( settings.get(EmotionUtils.emotions[4]).getIsOn() );
        cameraDetector.setDetectJoy( settings.get(EmotionUtils.emotions[5]).getIsOn() );
        cameraDetector.setDetectSadness( settings.get(EmotionUtils.emotions[6]).getIsOn() );
        cameraDetector.setDetectSurprise( settings.get(EmotionUtils.emotions[7]).getIsOn() );
        //cameraDetector.setDetectValence( settings.get(EmotionUtils.emotions[8]).getIsOn() );
    }

    private void makeSpinnerVisible(){
        //Log.w(TAG, "starting spinner");
        //spinner.setVisibility(View.VISIBLE);
        //startButton.setVisibility(GONE);
    }

    private void makeSpinnerInVisible(){
        //Log.w(TAG, "stopping spinner");
        //spinner.setVisibility(GONE);
        //startButton.setVisibility(View.VISIBLE);
    }
}
