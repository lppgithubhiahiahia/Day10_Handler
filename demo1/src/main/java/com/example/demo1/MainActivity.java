package com.example.demo1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demo1.dispactherevent.EventListView;
import com.example.demo1.dispactherevent.EventScrollView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EventListView elv;
    private EventScrollView esll;
    private ArrayList<String> list;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        elv = (EventListView) findViewById(R.id.elv);
        esll = (EventScrollView) findViewById(R.id.esll);

        list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            list.add("ceshi"+i);
        }

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view=View.inflate(MainActivity.this,android.R.layout.simple_expandable_list_item_1,null);

                TextView viewById = view.findViewById(android.R.id.text1);

                viewById.setText(list.get(position));

                return view;
            }
        };

        elv.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}
