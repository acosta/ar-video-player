package com.example.com.arvideoplayer;

import com.samsungxr.SXRContext;
import com.samsungxr.SXRMain;
import com.samsungxr.mixedreality.IMixedReality;
import com.samsungxr.mixedreality.IMixedRealityEvents;
import com.samsungxr.mixedreality.IPlaneEvents;
import com.samsungxr.mixedreality.SXRMixedReality;
import com.samsungxr.mixedreality.SXRPlane;
import com.samsungxr.mixedreality.SXRTrackingState;
import com.samsungxr.utility.Log;

public class Main extends SXRMain {

    private final String TAG = Main.class.getSimpleName();
    private SXRMixedReality mMixedReality;

    @Override
    public void onInit(SXRContext sxrContext) {

        mMixedReality = new SXRMixedReality(sxrContext.getMainScene());
        mMixedReality.getEventReceiver().addListener(planeEventsListener);
        mMixedReality.getEventReceiver().addListener(mixedRealityEventsListener);
        mMixedReality.resume();
    }

    @Override
    public SplashMode getSplashMode() {
        return SplashMode.NONE;
    }

    private IPlaneEvents planeEventsListener = new IPlaneEvents() {
        @Override
        public void onPlaneDetected(SXRPlane plane) {
            Log.d(TAG, "on plane detected");
        }

        @Override
        public void onPlaneStateChange(SXRPlane plane, SXRTrackingState trackingState) {

        }

        @Override
        public void onPlaneMerging(SXRPlane childPlane, SXRPlane parentPlane) {

        }

        @Override
        public void onPlaneGeometryChange(SXRPlane plane) {

        }
    };

    private IMixedRealityEvents mixedRealityEventsListener = new IMixedRealityEvents() {
        @Override
        public void onMixedRealityStart(IMixedReality mr) {
            mr.setPlaneFindingMode(SXRMixedReality.PlaneFindingMode.VERTICAL);
        }

        @Override
        public void onMixedRealityStop(IMixedReality mr) {

        }

        @Override
        public void onMixedRealityUpdate(IMixedReality mr) {

        }
    };
}
