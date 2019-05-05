package com.example.myapplication.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.base.BaseFragment;

public class NetFragment extends BaseFragment {
    @Override
    protected View getLayout() {
        TextView tv = new TextView(getContext());
        tv.setText("网盘");
        tv.setBackgroundColor(Color.BLUE);
        return tv;
    }

    @Override
    protected void initView(View view) {

    }
}
