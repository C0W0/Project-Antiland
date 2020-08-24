package com.walfen.antiland.ui.bars;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;

import java.util.function.IntSupplier;

public class BarB extends BarA {

    public BarB(float x, float y, int width, Bitmap barImage, IntSupplier maxValue, IntSupplier currValue) {
        super(x, y, width, barImage, maxValue, currValue);
    }

    public BarB(float x, float y, int width, int height, Bitmap barImage, IntSupplier maxValue, IntSupplier currValue){
        super(x, y, width, height, barImage, maxValue, currValue);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(barImage,
                new Rect(0, 0, (int)(barImage.getWidth()*(currentValue / totalValue)), barImage.getHeight()),
                new Rect((int)(x+scaleRatio*3), (int)(y+scaleRatio*3),
                        (int)(x+scaleRatio*3+barImage.getWidth()*(currentValue / totalValue)),(int)(y+barImage.getHeight()+scaleRatio*3)),
                Constants.getRenderPaint());
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        Rect r = new Rect();
        String text = (int)currentValue+"/"+(int)totalValue;
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, x+barFrame.getWidth()/2.f-r.width()/2.f,
                y+barFrame.getHeight()/2.f+r.height()/2.f, paint);
    }
}
