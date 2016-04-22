package com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartkit.widgets.DragLayout;
import com.winext.gifdemo.R;

/**
 * Created by Administrator on 2016/4/19.
 */
public class PlusFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_plus,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PlusVerticalScroFragment test  = new PlusVerticalScroFragment();
        PlusTwoFragment firstFragment = PlusTwoFragment.newInstance("#FF4081");
        PlusTwoFragment secondFragment = PlusTwoFragment.newInstance("#3F51B5");
        getFragmentManager().beginTransaction()
                .add(R.id.first, firstFragment).add(R.id.second, test)
                .commit();

        DragLayout.ShowNextPageNotifier nextIntf = new DragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
                // TODO: 16/3/10 如果是开机状态才进入下一页的加载数据

            }
        };

        DragLayout mdraglayout = (DragLayout) view.findViewById(R.id.draglayout);
        mdraglayout.setNextPageListener(nextIntf);

    }
}
