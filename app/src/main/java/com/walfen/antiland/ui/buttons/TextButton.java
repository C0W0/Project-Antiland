package com.walfen.antiland.ui.buttons;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.ui.ClickListener;
import com.walfen.antiland.ui.UIObject;

public class TextButton extends UIObject {

    protected final String text;
    protected ClickListener clicker;
    protected int textSize;
    protected int colour;

    public TextButton(float centreX, float centreY, int textSize, String text, ClickListener clicker) {
        super(0, 0, 128, 128); // data are placeholder
        Paint paint = new Paint();
        Rect r = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), r);
        x = centreX-r.width()/2.f;
        y = centreY-r.height()/2.f;
        width = r.width();
        height = r.height();
        bounds = new Rect((int)x-16, (int)y-16, (int) (x+width+16), (int) (y+height+16));
        this.clicker = clicker;
        this.text = text;
        this.textSize = textSize;
        colour = Constants.TEXT_COLOUR;
    }

    public TextButton(float centreX, float centreY, int textSize, String text, int colour, ClickListener clicker) {
        super(0, 0, 128, 128); // data are placeholder
        Paint paint = new Paint();
        Rect r = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), r);
        x = centreX-r.width()/2.f;
        y = centreY-r.height()/2.f;
        width = r.width();
        height = r.height();
        bounds = new Rect((int)x-16, (int)y-16, (int) (x+width+16), (int) (y+height+16));
        this.clicker = clicker;
        this.text = text;
        this.textSize = textSize;
        this.colour = colour;
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(!active)
            return;
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN ||
                event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            if (new Rect(bounds).contains((int) event.getX(pointerIndex), (int) event.getY(pointerIndex))) {
                clicker.onClick();
            }
        }
    }

    @Override
    public void update() {}

    @Override
    public void draw(Canvas canvas) {
        if(!active)
            return;
        Paint paint = new Paint();
        paint.setColor(colour);
        paint.setTextSize(textSize);
        canvas.drawText(text, x, y+height, paint);
    }
}
