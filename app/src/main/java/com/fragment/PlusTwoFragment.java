package com.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winext.gifdemo.R;


/**
 * Created by Administrator on 2016/4/19.
 */
public class PlusTwoFragment extends Fragment{


    public static PlusTwoFragment newInstance(String param1) {
        PlusTwoFragment fragment = new PlusTwoFragment();
        Bundle args = new Bundle();
        args.putString("color", param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String color = null;
        if (getArguments() != null) {
            color = getArguments().getString("color");
        }else {
            color ="#FFFFFF";
        }
        View view = new View(getActivity());
        view.setBackgroundColor(Color.parseColor(color));
        return view;
    }
}
