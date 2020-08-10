package com.walfen.antiland.ui.slider;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.UIObject;

public class Slider extends UIObject {

    private int max, min, tickSpacing;
    protected int value;
    protected String name, label;
    private Bitmap slideTrack, slider, tickMark;

    /* example:
        Slider s = new Slider(128, 128, 256, 64, 100, 0, 10, "name");
        uiManager.addUIObject(s);
        uiManager.addUIObject(new SliderAdjuster(128+256+10, 128, 64, 64, 1, Assets.adjusterUp, s));
        uiManager.addUIObject(new SliderAdjuster(128+256+74, 128, 64, 64, -1, Assets.adjusterDown, s));
     */

    public Slider(int x, int y, int width, int height, int max, int min, int tickSpacing, String name){
        super((float)x,(float)y,width,height);
        bounds.left -= 5;
        bounds.right += 5;
        this.name = name;
        this.max = max;
        this.min = min;
        this.tickSpacing = tickSpacing;
        value = (max+min)/2;
        label = (name+value);
        slideTrack = ImageEditor.scaleBitmapForced(Assets.horizontalSlideTrack, width, height);
        slider = ImageEditor.scaleBitmapForced(Assets.horizontalSlider, height, height);
        tickMark = ImageEditor.scaleBitmapForced(Assets.horizontalTickMark, height/2.f, height);
    }

    @Override
    public void update(){
        if(!active){
            return;
        }
        label = name+" "+value;
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(!active){
            return;
        }
        int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
        if(!new Rect(bounds).contains((int)event.getX(pointerIndex), (int)event.getY(pointerIndex)))
            return;

        value = (int)((event.getX(pointerIndex)-x)/width*(max-min)+min+0.5);
        if(value < min){
            value = min;
        } else if(value > max){
            value = max;
        }
    }

    @Override
    public void draw(Canvas canvas){
        if(!active){
            return;
        }
        canvas.drawBitmap(slideTrack, null,
                new Rect((int)x, (int)y, (int)x+slideTrack.getWidth(), (int)y+slideTrack.getHeight()),
                Constants.getRenderPaint());
        int left;
        if(tickSpacing > 0){
            for(int i = 1; i <= max/tickSpacing-1; i++){
                left = (int)((float)i*tickSpacing/max*width+x);
                canvas.drawBitmap(tickMark, null,
                        new Rect(left,(int)y, left+tickMark.getWidth(), (int)(y+tickMark.getHeight())),
                        Constants.getRenderPaint());
            }
        }
        left = (int)((float)(value-min)/(max-min)*width+x);
        canvas.drawBitmap(slider, null,
                new Rect(left, (int)y, left+slider.getWidth(), (int)(y+slider.getHeight())),
                Constants.getRenderPaint());
        Rect r = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setColor(Color.BLACK);
        String text = label;
        paint.getTextBounds(label, 0, label.length(), r);
        canvas.drawText(label, (int)(x+width/2-r.width()/2),(int)(y+height*1.3+r.height()), paint);
    }

    //getters and setters
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setTickSpacing(int tickSpacing) {
        this.tickSpacing = tickSpacing;
    }
}
