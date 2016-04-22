package com.winext.gifdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.loopview.LoopView;
import com.loopview.OnItemSelectedListener;
import com.wifi.WiFiListActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void loopview(View view){
        List<String> DegreesFList = new ArrayList<String>();
        DegreesFList.add("OFF");//80 off
        DegreesFList.add("71°F");//F1
        DegreesFList.add("75°F");//F2
        DegreesFList.add("78°F");//F3
        DegreesFList.add("82°F");//F4
        DegreesFList.add("86°F");//F5
        DegreesFList.add("99°F");//F6
        createDialogLoopView(DegreesFList);
    }
    public void container(View view){
        startActivity(new Intent(this, FragmentContainerAct.class));
    }

    public void spinner(View view){
        startActivity(new Intent(this,SpinnerWheelActivity.class));
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




    private AlertDialog wheelTempSelectordialog = null;
    public void createDialogLoopView(List<String> mcelsiusList){
        if (wheelTempSelectordialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.loopview_layout, null);
            wheelTempSelectordialog = new AlertDialog.Builder(this).create();
            wheelTempSelectordialog.setTitle("LoopView");
            final LoopView loopView = (LoopView) view.findViewById(R.id.loopview);
            //设置是否循环播放
            //loopView.setNotLoop();
            //滚动监听
            loopView.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {

                }
            });

            loopView.setViewPadding(100, 0, 100, 0);
            loopView.setItems(mcelsiusList);
            //设置初始位置
            loopView.setInitPosition(5);
            //设置字体大小
            loopView.setTextSize(30);
            wheelTempSelectordialog.setView(view);
            //Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_warining_tips);
            //wheelTempSelectordialog.setIcon(drawable);
            wheelTempSelectordialog.setCanceledOnTouchOutside(false);

            wheelTempSelectordialog.setButton(DialogInterface.BUTTON_POSITIVE, this.getString(android.R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("debug", "Item " + loopView.getSelectedItem());
                            wheelTempSelectordialog.dismiss();
                            wheelTempSelectordialog = null;

                        }
                    });

            wheelTempSelectordialog.setButton(DialogInterface.BUTTON_NEGATIVE, this.getString(android.R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            wheelTempSelectordialog.dismiss();
                            wheelTempSelectordialog = null;

                        }
                    });
        }

        wheelTempSelectordialog.show();
    }

}
