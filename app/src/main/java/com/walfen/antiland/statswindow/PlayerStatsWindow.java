package com.walfen.antiland.statswindow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.walfen.antiland.Handler;
import com.walfen.antiland.ui.TouchEventListener;

public class PlayerStatsWindow implements TouchEventListener {

    private Handler handler;

    private boolean active = false, buttonJustPressed = false;
    private int statsHeight, statsWidth;
    private int xDispute, yDispute;
    private final Bitmap statsScreen;

    public PlayerStatsWindow(Handler handler){
        this.handler = handler;
        statsScreen = null;
    }


    @Override
    public void onTouchEvent(MotionEvent event) {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }
}
