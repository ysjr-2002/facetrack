package com.facetrack;

import android.app.Activity;
import android.os.Bundle;

import com.megvii.facepp.sdk.Facepp;

public class MainActivity extends Activity {

    Facepp facepp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void init() {

        facepp = new Facepp();
        facepp.init(MainActivity.this, null, 1);
    }
}
