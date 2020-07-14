package com.walfen.antiland.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;

import java.util.function.IntSupplier;


public class BarA extends UIObject {

    private float totalValue, currentValue;
    private Handler handler;
    private final Bitmap barImage, barFrame;
    private float scaleRatio;
    private IntSupplier maxValue, currValue;

    public BarA(Handler handler, float x, float y, int width, Bitmap barImage, IntSupplier maxValue, IntSupplier currValue) {
        super(x, y, width, width/10);
        this.handler = handler;
        totalValue = currentValue = 0;
        scaleRatio = (float)width/640.f;
        barFrame = ImageEditor.scaleBitmap(Assets.bar_frame, width, height);
        this.barImage = ImageEditor.scaleBitmapForced(barImage, width-scaleRatio*4, height-scaleRatio*4);
        this.maxValue = maxValue;
        this.currValue = currValue;
    }

    @Override
    public void onTouchEvent(MotionEvent event) { }

    @Override
    public void update() {
        totalValue = maxValue.getAsInt();
        currentValue = currValue.getAsInt();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(barFrame, null,
                new Rect((int)x, (int)y, (int)(x+barFrame.getWidth()), (int)(y+barFrame.getHeight())),
                Constants.getRenderPaint());
        canvas.drawBitmap(barImage,
                new Rect(0, 0, (int)(barImage.getWidth()*(currentValue / totalValue)), barImage.getHeight()),
                new Rect((int)(x+scaleRatio*2), (int)(y+scaleRatio*2),
                        (int)(x+scaleRatio*2+barImage.getWidth()*(currentValue / totalValue)),(int)(y+barImage.getHeight()+scaleRatio*2)),
                Constants.getRenderPaint());
    }
}
