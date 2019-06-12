package com.example.demo1.dispactherevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class EventScrollView extends ScrollView {
    private static final String TAG = "tag";

    public EventScrollView(Context context) {
        super(context);
        Log.i(TAG, "EventScrollView: ");
    }

    public EventScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
