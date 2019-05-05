package com.example.myapplication.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

//如果不抽象，需要注册
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //共有的方法 ，都可以放这里
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        initView();
        initData();
    }

    protected abstract int getViewId();
    protected abstract void initView();
    protected abstract void initData();


    public void showTaost(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
