package com.walfen.antiland.ui.decorative;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.ui.UIObject;
import com.walfen.antiland.untils.StringSupplier;

public class UITextDecoration extends UIObject {

    private StringSupplier supplier;
    private int fontSize, colour;

    public UITextDecoration(float x, float y, StringSupplier supplier, int fontSize, int colour) {
        super(x, y, 0, 0);
        this.supplier = supplier;
        this.fontSize = fontSize;
        this.colour = colour;
    }

    @Override
    public void onTouchEvent(MotionEvent event) { }

    @Override
    public void update() { }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(colour);
        paint.setTextSize(fontSize);
        Rect r = new Rect();
        String text = supplier.getAsString();
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, x-r.width()/2.f, y+r.height()/2.f, paint);
    }
}
