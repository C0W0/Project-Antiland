package com.walfen.antiland.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.BarA;

import java.util.function.IntSupplier;

public class BarC extends BarA {

    private int colour;

    public BarC(Handler handler, float x, float y, int width, int colour, IntSupplier maxValue, IntSupplier currValue) {
        super(handler, x, y, width, Assets.NULL, maxValue, currValue);
        this.colour = colour;
    }

    public BarC(Handler handler, float x, float y, int width, int height, int colour, IntSupplier maxValue, IntSupplier currValue){
        super(handler, x, y, width, height, Assets.NULL, maxValue, currValue);
        this.colour = colour;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(colour);
        canvas.drawRect(new Rect((int)(x+scaleRatio*3), (int)(y+scaleRatio*3),
                (int)(x+scaleRatio*3+barImage.getWidth()*(currentValue / totalValue)),(int)(y+barImage.getHeight()+scaleRatio*3)), paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        Rect r = new Rect();
        String text = (int)currentValue+"/"+(int)totalValue;
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, x+barFrame.getWidth()/2.f-r.width()/2.f,
                y+barFrame.getHeight()/2.f+r.height()/2.f, paint);
    }
}
