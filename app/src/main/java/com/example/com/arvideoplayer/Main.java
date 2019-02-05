package com.example.com.arvideoplayer;

import android.opengl.GLES30;

import com.samsungxr.SXRContext;
import com.samsungxr.SXRMain;
import com.samsungxr.SXRMaterial;
import com.samsungxr.SXRMesh;
import com.samsungxr.SXRNode;
import com.samsungxr.SXRRenderData;
import com.samsungxr.mixedreality.IMixedReality;
import com.samsungxr.mixedreality.IMixedRealityEvents;
import com.samsungxr.mixedreality.IPlaneEvents;
import com.samsungxr.mixedreality.SXRMixedReality;
import com.samsungxr.mixedreality.SXRPlane;
import com.samsungxr.mixedreality.SXRTrackingState;
import com.samsungxr.utility.Log;

public class Main extends SXRMain {

    private final String TAG = Main.class.getSimpleName();
    private SXRContext mContext;

    @Override
    public void onInit(SXRContext sxrContext) {

        mContext = sxrContext;

        SXRMixedReality mMixedReality = new SXRMixedReality(sxrContext.getMainScene());
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
            SXRNode planeNode = createPlaneNode();
            planeNode.attachComponent(plane);
            mContext.getMainScene().addNode(planeNode);
        }

        @Override
        public void onPlaneStateChange(SXRPlane plane, SXRTrackingState trackingState) {

        }

        @Override
        public void onPlaneMerging(SXRPlane childPlane, SXRPlane parentPlane) {

        }

        @Override
        public void onPlaneGeometryChange(SXRPlane plane) {
            SXRMesh mesh = new SXRMesh(getSXRContext());
            mesh.setVertices(plane.get3dPolygonAsArray());

            plane.getOwnerObject().getRenderData().setMesh(mesh);
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

    private SXRNode createPlaneNode() {
        SXRMaterial mat = new SXRMaterial(mContext, SXRMaterial.SXRShaderType.Phong.ID);
        mat.setDiffuseColor(1, 1, 1, 0.5f);

        SXRRenderData renderData = new SXRRenderData(mContext);
        renderData.disableLight();
        renderData.setAlphaBlend(true);
        renderData.setRenderingOrder(SXRRenderData.SXRRenderingOrder.TRANSPARENT);
        renderData.setDrawMode(GLES30.GL_TRIANGLE_FAN);
        renderData.setMaterial(mat);

        SXRNode plane = new SXRNode(mContext);
        plane.attachComponent(renderData);
        plane.setName("Plane");

        return plane;
    }
}
