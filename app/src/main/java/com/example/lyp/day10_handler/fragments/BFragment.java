package com.example.lyp.day10_handler.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.lyp.day10_handler.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BFragment extends Fragment {


    private WebView web;

    public BFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        web = (WebView) view.findViewById(R.id.web);

        web.setWebViewClient(new WebViewClient());
        web.loadUrl("https://www.baidu.com/");
    }
}
