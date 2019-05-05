package com.example.myapplication;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.fragment.ClassifyFragment;
import com.example.myapplication.fragment.DirFragment;
import com.example.myapplication.fragment.NetFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);
    }

    @Override
    protected void initData() {
        fragmentList.add(new ClassifyFragment());
        fragmentList.add(new DirFragment());
        fragmentList.add(new NetFragment());
        //设置适配器
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //关联
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.BLUE);
    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        private String[] array = {"分类文件", "手机目录", "网盘"};

        @Override
        public CharSequence getPageTitle(int position) {
            return array[position];
        }
    }

}
