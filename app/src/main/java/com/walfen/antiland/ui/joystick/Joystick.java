package com.walfen.antiland.ui.joystick;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.UIObject;
import com.walfen.antiland.untils.Utils;

public class Joystick extends UIObject {

    private Handler handler;
    private final int centerX, centerY;
    private final int radius;
    private int pointerId = -1; //"legal" finger, never change
    private float inputX = 0, inputY = 0;
    private float deadZone = 0;
    private Bitmap pad, controller;
    private boolean lockedUp;

    public Joystick(Handler handler, float x, float y, int radius) {
        super(x, y, radius*2, radius*2);
        this.handler = handler;
        centerX = (int)(x+width/2);
        centerY = (int)(y+height/2);
        pad = ImageEditor.scaleBitmap(Assets.joystick_pad, width);
        controller = ImageEditor.scaleBitmap(Assets.joystick_controller, (float)width/3);
        this.radius = radius;
        lockedUp = false;
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(lockedUp){
            inputX = 0;
            inputY = 0;
            System.out.println("joystick lockedUp");
            return;
        }
        Player player = handler.getPlayer();
        if(player.getInventory().isActive() || player.getFabricator().isActive()
                || player.getMissionManager().isActive() || player.getSkillsManager().isActive()
        || player.getStatsWindow().isActive() || player.getTrade().isActive())
            return;
        int index = event.getActionIndex();
        int pointerIndex = event.findPointerIndex(event.getPointerId(index)); //increases and decreases
        float distance = Utils.getDistance(event.getX(pointerIndex), event.getY(pointerIndex), centerX, centerY);
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if(distance > radius+32 && event.getPointerId(index) == pointerId){
                    inputX = 0;
                    inputY = 0;
                } else if(distance > radius && distance < radius+32){
                    float ratio = (float)radius/distance;
                    inputX = (event.getX(pointerIndex) - centerX)*ratio;
                    inputY = (event.getY(pointerIndex) - centerY)*ratio;
                    pointerId = event.getPointerId(index);
                } else if(distance < radius){
                    inputX = event.getX(pointerIndex) - centerX;
                    inputY = event.getY(pointerIndex) - centerY;
                    pointerId = event.getPointerId(index);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                for(int i = 0; i < event.getPointerCount(); i++) {
                    distance = Utils.getDistance(event.getX(i), event.getY(i), centerX, centerY);
                    if (distance > radius && distance < radius + 32) {
                        float ratio = (float) radius / distance;
                        inputX = (event.getX(i) - centerX) * ratio;
                        inputY = (event.getY(i) - centerY) * ratio;
                    } else if (distance < radius) {
                        inputX = event.getX(i) - centerX;
                        inputY = event.getY(i) - centerY;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(event.getPointerId(index) == pointerId){
                    inputX = 0;
                    inputY = 0;
                }
        }
    }

    @Override
    public void update() { }

    @Override
    public void draw(Canvas canvas) {
        int cDestX = (int) (inputX + centerX - (float) width/6);
        int cDestY = (int) (inputY + centerY - (float) height/6);
        canvas.drawBitmap(pad, null, new Rect((int)x, (int)y,
                (int)x+width, (int)y+height), Constants.getRenderPaint());
        canvas.drawBitmap(controller, null,
                new Rect(cDestX, cDestY, (int)(cDestX+(float)width/3), (int)(cDestY+(float)height/3)),
                Constants.getRenderPaint());
    }

    /**
     * gets the raw position of the x-axis on the joystick
     * @return the the raw value of the x-axis, in between -1 and 1
     */
    public float getInputX(){
        return inputX/radius;
    }

    /**
     * gets the raw position of the y-axis on the joystick
     * @return the the raw value of the y-axis, in between -1 and 1
     */
    public float getInputY(){
        return inputY/radius;
    }

    /**
     * gets the position of the of the x-axis on the joystick
     * when dead zone is in consideration
     * @return the value of the x-axis, in between -1 and 1. 0 if in dead zone
     */
    public float getMappedInputX(){
        return Utils.Py.getC(getInputX(), getInputY())<deadZone?0:getInputX();
    }

    /**
     * gets the position of the of the y-axis on the joystick
     * when dead zone is in consideration
     * @return the value of the y-axis, in between -1 and 1. 0 if in dead zone
     */
    public float getMappedInputY(){
        return Utils.Py.getC(getInputX(), getInputY())<deadZone?0:getInputY();
    }

    public void setDeadZone(float deadZone) {
        this.deadZone = deadZone;
    }

    public int getRadius() {
        return radius;
    }

    public void reset(){
        inputX = 0;
        inputY = 0;
    }

    public void lockUp(){
        lockedUp = true;
    }

    public void unlock(){
        lockedUp = false;
    }

    public boolean isLockedUp() {
        return lockedUp;
    }
}
