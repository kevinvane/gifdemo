package com.winext.gifdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelChangedListener;
import antistatic.spinnerwheel.WheelVerticalView;
import antistatic.spinnerwheel.adapters.ArrayWheelAdapter;

public class SpinnerWheelActivity extends AppCompatActivity {

    WheelVerticalView wheel;
    RequestManager requestManager;
    private boolean scrolling = false;
    String [] cities = new String[] {"New York", "Washington", "Chicago", "Atlanta", "Orlando", "Los Angeles", "Houston", "New Orleans"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        WheelVerticalView  wheel = new WheelVerticalView(this);
        wheel.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        RelativeLayout view = (RelativeLayout)findViewById(R.id.view);
        view.addView(wheel);
        //wheel = (WheelVerticalView) findViewById(R.id.wheel);
        wheel.setVisibleItems(5);
        ArrayWheelAdapter<String> adapter =
                new ArrayWheelAdapter<String>(this, cities);
        adapter.setTextSize(18);
        wheel.setViewAdapter(adapter);
        wheel.setCurrentItem(0);
        wheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(AbstractWheel abstractWheel, int i, int newValue) {
                if(!scrolling){

                }
            }
        });

    }


}
