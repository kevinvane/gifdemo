package com.winext.gifdemo;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fragment.PlusFragment;
import com.fragment.PlusOneFragment;
import com.fragment.PlusTwoFragment;

public class FragmentContainerAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        getSupportFragmentManager().beginTransaction().add(R.id.container,new PlusFragment()).commit();
    }




}
