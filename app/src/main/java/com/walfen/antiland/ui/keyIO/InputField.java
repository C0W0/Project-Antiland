package com.walfen.adrdtest.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.walfen.adrdtest.GameHierarchyElement;
import com.walfen.adrdtest.Handler;

public class InputField implements GameHierarchyElement, KeyEventListener {

    private boolean control = true;
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
        if(!control) {
            ((InputMethodManager) handler.getGame().getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            control = true;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        canvas.drawRect(new Rect(x, y+height-2, x+width, y+height), paint);
        paint.setTextSize(34);
        Rect r = new Rect();
        String text = sb.toString();
        paint.getTextBounds(text, 0, text.length(), r);
        paint.setColor(Color.BLACK);
        canvas.drawText(text, x+width/2.f-r.width()/2.f, y+height-5, paint);
    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode != 59){
            if(keyCode == 67 && event.getUnicodeChar() == 0 && sb.length() > 0)
                sb.deleteCharAt(sb.length()-1);
            else
                sb.append((char)event.getUnicodeChar());
        }
    }

    @Override
    public void onKeyLongPress(int keyCode, KeyEvent event) {

    }

    public void setControl(){
        control = !control;
    }
}
