package com.test.paint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new RadarGradientView(this));
        setContentView(new ZoomImageView(this));
//        setContentView(R.layout.activity_main);
        setContentView(new MyGradientView(this));
    }


}
