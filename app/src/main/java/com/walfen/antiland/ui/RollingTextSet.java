package com.walfen.antiland.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.states.State;
import com.walfen.antiland.untils.Utils;

import java.util.ArrayList;
import java.util.Arrays;


public class RollingTextSet implements TouchEventListener {

    private int yLocation;
    private ArrayList<String[]> attributionText;
    private Handler handler;

    public RollingTextSet(Handler handler) {
        yLocation = 512;
        attributionText = new ArrayList<>();
        String[] attributions = Utils.loadFileAsString("attribution.txt").
                replace("\r", "").replace("\n", "").
                replace("\r\n", "").split("/s/s/");
        for(String str: attributions){
            ArrayList<String> list = Utils.splitStringLong(str, 60);
            attributionText.add(list.toArray(new String[0]));
        }
        this.handler = handler;
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(yLocation < 0)
            State.setState(handler.getGame().getMenuState());
    }

    @Override
    public void update() {
        yLocation -= 6;
    }

    @Override
    public void draw(Canvas canvas) {
        int top = yLocation;
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(42);
        Rect r = new Rect();
        String attr = "Antiland Development Team:";
        paint.setFakeBoldText(true);
        paint.getTextBounds(attr, 0, attr.length(), r);
        top += 32;
        drawText(canvas, attr, 384, top, paint);
        top += 32+(r.height()+1);

        paint.setTextSize(36);
        attr = "App Architecture";
        paint.getTextBounds(attr, 0, attr.length(), r);
        top += 32+(r.height()+1);
        drawText(canvas, attr, 512, top, paint);
        top += 32+(r.height()+1);
        paint.setFakeBoldText(false);
        attr = "Luo Zha";
        paint.getTextBounds(attr, 0, attr.length(), r);
        drawText(canvas, attr, 384, top, paint);
        top += 32+(r.height()+1);
        top += 32;

        paint.setFakeBoldText(true);
        attr = "Programming";
        paint.getTextBounds(attr, 0, attr.length(), r);
        drawText(canvas, attr, 512, top, paint);
        top += 32+(r.height()+1);
        paint.setFakeBoldText(false);
        attr = "Luo Zha";
        paint.getTextBounds(attr, 0, attr.length(), r);
        drawText(canvas, attr, 384, top, paint);
        top += 32+(r.height()+1);
        attr = "Thompson Li";
        paint.getTextBounds(attr, 0, attr.length(), r);
        drawText(canvas, attr, 384, top, paint);
        top += 32+(r.height()+1);
        top += 32;

        paint.setFakeBoldText(true);
        attr = "Graphic Design";
        paint.getTextBounds(attr, 0, attr.length(), r);
        drawText(canvas, attr, 512, top, paint);
        top += 32+(r.height()+1);
        paint.setFakeBoldText(false);
        attr = "Harry Deng";
        paint.getTextBounds(attr, 0, attr.length(), r);
        drawText(canvas, attr, 384, top, paint);
        top += 32+(r.height()+1);
        attr = "Thompson Li";
        paint.getTextBounds(attr, 0, attr.length(), r);
        drawText(canvas, attr, 384, top, paint);
        top += 32+(r.height()+1);
        attr = "Aaron Zeng";
        paint.getTextBounds(attr, 0, attr.length(), r);
        drawText(canvas, attr, 384, top, paint);
        top += 32+(r.height()+1);
        attr = "Luo Zha";
        paint.getTextBounds(attr, 0, attr.length(), r);
        drawText(canvas, attr, 384, top, paint);
        top += 32+(r.height()+1);
        top += 32;

        paint.setFakeBoldText(true);
        attr = "World Design";
        paint.getTextBounds(attr, 0, attr.length(), r);
        drawText(canvas, attr, 512, top, paint);
        top += 32+(r.height()+1);
        paint.setFakeBoldText(false);
        attr = "Thompson Li";
        paint.getTextBounds(attr, 0, attr.length(), r);
        drawText(canvas, attr, 384, top, paint);
        top += 32+(r.height()+1);
        attr = "Luo Zha";
        paint.getTextBounds(attr, 0, attr.length(), r);
        drawText(canvas, attr, 384, top, paint);
        top += 32+(r.height()+1);
        top += 128;

        paint.setTextSize(42);
        paint.setFakeBoldText(true);
        attr = "Credits:";
        paint.getTextBounds(attr, 0, attr.length(), r);
        drawText(canvas, attr, 384, top, paint);
        top += 32+(r.height()+1);

        paint.setFakeBoldText(false);
        paint.setTextSize(36);
        for(String[] text: attributionText){
            paint.getTextBounds(text[0], 0, text[0].length(), r);
            for (String s : text) {
                top += 32 + (r.height() + 1);
                drawText(canvas, s, 384, top, paint);
            }
            top += 32;
        }
        if(top < -128)
            State.setState(handler.getGame().getMenuState());
    }

    private void drawText(Canvas canvas, String text, int x, int y, Paint paint){
        if(y > -64 && y < Constants.SCREEN_HEIGHT+128)
            canvas.drawText(text, x, y, paint);

    }
}
