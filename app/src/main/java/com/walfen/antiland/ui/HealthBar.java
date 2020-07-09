package com.walfen.antiland.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;


public class HealthBar extends UIObject {

    private float healthTotal, healthCurrent;
    private Handler handler;
    private final Bitmap hpBar, barFrame;
    private float scaleRatio;

    public HealthBar(Handler handler, float x, float y, int width) {
        super(x, y, width, width/10);
        this.handler = handler;
        healthTotal = healthCurrent = 0;
        scaleRatio = (float)width/640.f;
        barFrame = ImageEditor.scaleBitmap(Assets.bar_frame, width, height);
        hpBar = ImageEditor.scaleBitmapForced(Assets.hp_bar, width-scaleRatio*4, height-scaleRatio*4);
    }

    @Override
    public void onTouchEvent(MotionEvent event) { }

    @Override
    public void update() {
        healthTotal = (float) handler.getPlayer().getMaxHP();
        healthCurrent = (float) handler.getPlayer().getHealth();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(barFrame, null,
                new Rect((int)x, (int)y, (int)(x+barFrame.getWidth()), (int)(y+barFrame.getHeight())),
                Constants.getRenderPaint());
        canvas.drawBitmap(hpBar,
                new Rect(0, 0, (int)(hpBar.getWidth()*(healthCurrent/healthTotal)), hpBar.getHeight()),
                new Rect((int)(x+scaleRatio*2), (int)(y+scaleRatio*2),
                        (int)(x+hpBar.getWidth()*(healthCurrent/healthTotal)),(int)(y+hpBar.getHeight())),
                Constants.getRenderPaint());
    }
}
