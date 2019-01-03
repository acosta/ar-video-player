package com.example.com.arvideoplayer;

import android.os.Bundle;

import com.samsungxr.SXRActivity;

public class MainActivity extends SXRActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setMain(new Main());
    }
}
