package com.winext.gifdemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GifTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GlideActivity extends AppCompatActivity {

    ImageView img;
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        img = (ImageView)findViewById(R.id.img);
        Glide.with(this).load(AssetsHelper.getAssetsImageByte(getApplicationContext(), "test100.gif")).asGif().into(img);

//        requestManager = Glide.with(this);
//        DrawableTypeRequest drawableTypeRequest = requestManager.
//        load(AssetsHelper.getAssetsImageByte(getApplicationContext(), "test100.gif"));
//        GifTypeRequest gifTypeRequest = drawableTypeRequest.asGif();
//        gifTypeRequest.into(img);

    }

    public void stop(View v){

        Glide.clear(img);
    }

}
