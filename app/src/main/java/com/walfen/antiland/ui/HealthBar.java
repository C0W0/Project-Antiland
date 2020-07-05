package com.walfen.antiland.ui;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;


public class HealthBar extends UIObject {

    private float healthTotal,healthCurrent;
    private Handler handler;

    public HealthBar(Handler handler, float x, float y, int width, int height) {
        super(x, y, width, height);
        this.handler= handler;
        healthTotal=healthCurrent=0;
    }

    @Override
    public void onTouchEvent(MotionEvent event) { }

    @Override
    public void update() {
        healthTotal=(float) handler.getWorld().getPlayer().getMaxHP();
        healthCurrent=(float) handler.getWorld().getPlayer().getHealth();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(Assets.hp_bar, null, new Rect((int)x, (int)y,(int)(x+width*(healthCurrent/healthTotal)),(int)y+height),Constants.getRenderPaint());
    }
}
