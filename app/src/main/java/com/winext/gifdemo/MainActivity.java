package com.winext.gifdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wifi.WiFiListActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void wifi(View view){
        startActivity(new Intent(this, WiFiListActivity.class));
    }

    public void gifview(View view){
        startActivity(new Intent(this, GifViewActivity.class));
    }

    public void glide(View view){
        startActivity(new Intent(this,GlideActivity.class));
    }
}
