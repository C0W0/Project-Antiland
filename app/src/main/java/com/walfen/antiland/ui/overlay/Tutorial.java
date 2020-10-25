package com.walfen.antiland.ui.overlay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.GameHierarchyElement;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.ui.ChangeEvent;
import com.walfen.antiland.ui.ClickListener;

public class Tutorial implements GameHierarchyElement {

    private Rect target;
    private boolean active = false;
    private long lastFrame, frameTime;
    private int index;
    private ChangeEvent action;

    public Tutorial(){
        frameTime = 350;
        index = 0;
        action = Constants.EMPTY_EVENT;
    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN ||
                event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            float touchX = event.getX(pointerIndex);
            float touchY = event.getY(pointerIndex);
            if(target.contains((int)touchX, (int)touchY)){
                active = false;
                action.onChange();
                action = Constants.EMPTY_EVENT;
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if(!active)
            return;
        if(System.currentTimeMillis() - lastFrame > frameTime){
            index = index==1?0:1;
            lastFrame = System.currentTimeMillis();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(!active || index == 1)
            return;
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(new Rect(target.left-5, target.top-5, target.right+5, target.top), paint);
        canvas.drawRect(new Rect(target.left-5, target.top-5, target.left, target.bottom+5), paint);
        canvas.drawRect(new Rect(target.right, target.top-5, target.right+5, target.bottom+5), paint);
        canvas.drawRect(new Rect(target.left-5, target.bottom, target.right+5, target.bottom+5), paint);
    }

    public void setTarget(Rect target) {
        this.target = new Rect(target);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        if(active)
            lastFrame = System.currentTimeMillis();
    }

    public void setActive(boolean active, ChangeEvent action) {
        setActive(active);
        this.action = action;
    }

    public void setActive(){
        setActive(!active);
    }
}
