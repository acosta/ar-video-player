package com.example.com.arvideoplayer;

import android.opengl.GLES30;

import com.samsungxr.SXRContext;
import com.samsungxr.SXRMaterial;
import com.samsungxr.SXRMesh;
import com.samsungxr.SXRNode;
import com.samsungxr.SXRRenderData;
import com.samsungxr.SXRShaderId;
import com.samsungxr.mixedreality.IMixedReality;
import com.samsungxr.mixedreality.IMixedRealityEvents;
import com.samsungxr.mixedreality.SXRPointCloud;
import com.samsungxr.utility.Log;

public class PointCloud implements IMixedRealityEvents {
    private final String TAG = PointCloud.class.getSimpleName();

    private final SXRNode mPointCloudNode;
    private final SXRContext mContext;
    private SXRPointCloud mPointCloud;

    public PointCloud(SXRContext sxrContext) {

        mContext = sxrContext;

        SXRMaterial mat = new SXRMaterial(sxrContext,
                new SXRShaderId(PointCloudShader.class));
        //Configure the shaders inputs
        mat.setColor(1.0f, 1.0f, 1.0f);
        mat.setFloat("u_point_size", 5.0f);

        //Create the render data for using the material configured above
        SXRRenderData renderData = new SXRRenderData(sxrContext);
        renderData.setDrawMode(GLES30.GL_POINTS);
        renderData.setMaterial(mat);

        //Create the point cloud node which will be rendered on the scene
        //Also attach the render data on it to use the material (controlled by shader)
        mPointCloudNode = new SXRNode(sxrContext);
        mPointCloudNode.attachComponent(renderData);
    }

    private void enablePoints() {
        Log.d(TAG, "enabling points cloud");
        mContext.getMainScene().addNode(mPointCloudNode);
    }

    public void disablePoints() {
        Log.d(TAG, "disabling points cloud");
        mContext.getMainScene().removeNode(mPointCloudNode);
        mPointCloudNode.detachComponent(SXRRenderData.getComponentType());
    }

    @Override
    public void onMixedRealityStart(IMixedReality mr) {
        enablePoints();
    }

    @Override
    public void onMixedRealityStop(IMixedReality mr) {

    }

    @Override
    public void onMixedRealityUpdate(IMixedReality mr) {
        //Update the points on the scene according to the points updated by MixedReality
        SXRPointCloud newPointCloud = mr.acquirePointCloud();
        if (mPointCloud != newPointCloud && newPointCloud != null) {
            float[] cloudPoints = newPointCloud.getPoints();
            //Verify if there are points in Point Cloud
            if (cloudPoints.length == 0) {
                return;
            }

            SXRMesh mesh = new SXRMesh(mContext);
            mesh.setVertices(cloudPoints);
            mPointCloudNode.getRenderData().setMesh(mesh);

            mPointCloud = newPointCloud;
            newPointCloud.release();
        }
    }

    @Override
    public void onMixedRealityError(IMixedReality mr, String errmsg) {

    }
}
