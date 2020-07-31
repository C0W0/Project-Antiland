package com.walfen.antiland.ui.drag;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.UIObject;

public abstract class DraggableUI extends UIObject {

    protected int fingerX, fingerY;
    protected boolean dragging;
    protected Bitmap image;

    public DraggableUI(float x, float y, int width, int height, Bitmap image) {
        super(x, y, width, height);
        this.image = ImageEditor.scaleBitmapForced(image, width, height);
        fingerX = (int)(x+width/2);
        fingerY = (int)(y+height/2);
        dragging = false;
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
        int tempX = (int)event.getX(pointerIndex);
        int tempY = (int)event.getY(pointerIndex);
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if(new Rect(bounds).contains(tempX, tempY))
                    dragging = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if(dragging){
                    fingerX = tempX;
                    fingerY = tempY;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(dragging){
                    release(tempX, tempY);
                    dragging = false;
                    fingerX = (int)(x+width/2);
                    fingerY = (int)(y+height/2);
                }
        }
    }

    protected abstract void release(int x, int y);

    @Override
    public void update() { }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, null,
                new Rect(fingerX-width/2, fingerY-height/2, fingerX+width/2, fingerY+height/2),
                Constants.getRenderPaint());
    }
}