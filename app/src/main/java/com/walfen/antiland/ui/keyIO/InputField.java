package com.walfen.antiland.ui.keyIO;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.walfen.antiland.Handler;
import com.walfen.antiland.ui.TouchEventListener;

public abstract class InputField implements TouchEventListener, KeyEventListener {

    private boolean control = true, active = false;
    private Handler handler;
    private StringBuilder sb;
    private int x, y, width, height;

    public InputField(Handler handler, int x, int y, int width, int height){
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        sb = new StringBuilder();
    }

    public void onTouchEvent(MotionEvent event){
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN ||
                event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            if (new Rect((int) x, (int) y, (int) x + width, (int) y + height).contains
                    ((int) event.getX(pointerIndex), (int) event.getY(pointerIndex))) {
                control = false;
            }
        }
    }

    @Override
    public void update() {
        if(!active)
            return;
        if(!control) {
            ((InputMethodManager) handler.getGame().getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            control = true;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(!active)
            return;
        preDraw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(34);
        Rect r = new Rect();
        String text = sb.toString();
        paint.getTextBounds(text, 0, text.length(), r);
        paint.setColor(Color.BLACK);
        canvas.drawText(text, x+width/2.f-r.width()/2.f, y+height-5, paint);
    }

    public abstract void preDraw(Canvas canvas);

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {
        if(!active)
            return;
        if(keyCode != 59){
            if(keyCode == 67 && event.getUnicodeChar() == 0 && sb.length() > 0)
                sb.deleteCharAt(sb.length()-1);
            else
                sb.append((char)event.getUnicodeChar());
        }
    }

    @Override
    public void onKeyLongPress(int keyCode, KeyEvent event) {
        if(!active)
            return;
    }

    public void setControl(){
        control = !control;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setActive() {
        active = !active;
    }
}
