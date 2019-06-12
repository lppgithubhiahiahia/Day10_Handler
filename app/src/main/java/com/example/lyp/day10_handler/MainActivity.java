package com.example.lyp.day10_handler;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.lyp.day10_handler.adapter.VpAdapter;
import com.example.lyp.day10_handler.fragments.AFragment;
import com.example.lyp.day10_handler.fragments.BFragment;

import java.util.ArrayList;
//https://www.wanandroid.com/project/list/1/json?cid=294
//https://www.baidu.com/
//http://yun918.cn/study/public/file_upload.php

public class MainActivity extends AppCompatActivity {

    private TabLayout tab;
    private ViewPager vp;
    private ArrayList<Fragment> list;
    private VpAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        vp = (ViewPager) findViewById(R.id.vp);

        list = new ArrayList<>();

        list.add(new AFragment());
        list.add(new BFragment());

        adapter = new VpAdapter(getSupportFragmentManager(), list);

        vp.setAdapter(adapter);

        tab.addTab(tab.newTab().setCustomView(R.layout.one_tab));
        tab.addTab(tab.newTab().setCustomView(R.layout.two_tab));

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
    }
}
