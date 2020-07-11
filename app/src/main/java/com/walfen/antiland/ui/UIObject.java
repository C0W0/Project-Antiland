package com.walfen.antiland.ui;


import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public abstract class UIObject implements TouchEventListener{

    protected float x, y;
    protected int width, height;
    protected boolean active = true;
    protected boolean pressed = false;
    protected Rect bounds;

    public UIObject(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rect((int)x, (int)y, (int) (x+width), (int) (y+height));
    }

    //getters and setters
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void setActive() {
        active = !active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
