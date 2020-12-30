package com.walfen.antiland.states;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.ui.RollingTextSet;

public class CreditsState extends State {

    private RollingTextSet textSet;

    public CreditsState(Handler handler) {
        super(handler);
        init();
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        textSet.onTouchEvent(event);
    }

    @Override
    public void update() {
        textSet.update();
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT), paint);
        textSet.draw(canvas);
    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {

    }

    @Override
    public void onKeyLongPress(int keyCode, KeyEvent event) {

    }

    public void init(){
        textSet = new RollingTextSet(handler);
    }
}
