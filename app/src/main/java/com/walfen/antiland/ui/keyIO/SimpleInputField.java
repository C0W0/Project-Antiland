package com.walfen.antiland.ui.keyIO;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;

import com.walfen.antiland.Handler;
import com.walfen.antiland.states.State;

public class SimpleInputField extends InputField {

    public SimpleInputField(Handler handler, int x, int y, int width, int height) {
        super(handler, x, y, width, height);
        active = true;
    }

    @Override
    public void preDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(new Rect(x, y+height-4, x+width, y+height), paint);
    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
    }
}
