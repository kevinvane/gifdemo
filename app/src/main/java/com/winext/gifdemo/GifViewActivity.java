package com.winext.gifdemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.felipecsl.gifimageview.library.GifImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GifViewActivity extends AppCompatActivity {
    GifImageView gifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        gifView = (GifImageView) findViewById(R.id.gifImageView);
        gifView.setBytes(AssetsHelper.getAssetsImageByte(getApplicationContext(), "test100.gif"));
    }


    @Override
    protected void onStart() {
        super.onStart();
        gifView.startAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gifView.stopAnimation();
    }
}
