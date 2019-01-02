package com.example.com.arvideoplayer;

import android.os.Bundle;

import com.samsungxr.SXRActivity;
import com.samsungxr.SXRContext;
import com.samsungxr.SXRMain;
import com.samsungxr.SXRNode;

public class MainActivity extends SXRActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setMain(new Main());
    }

    private final class Main extends SXRMain {

        @Override
        public void onInit(SXRContext sxrContext) {

            //Create a rectangle
            SXRNode quad = new SXRNode(sxrContext, 4, 2);
            quad.getTransform().setPosition(0, 0, -2);

            //Add rectangle to the scene
            sxrContext.getMainScene().addNode(quad);
        }

        @Override
        public SplashMode getSplashMode() {
            return SplashMode.NONE;
        }
    }
}
