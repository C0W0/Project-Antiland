package com.walfen.antiland.gfx;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;

public class GameCamera {

    private float xOffset, yOffset;
    private Handler handler;

    public GameCamera(Handler handler, float xOffset, float yOffset){
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void hideBlankSpace(){

        if(xOffset < 0){
            xOffset = 0;
        } else if(xOffset > handler.getWorld().getWidth() * Constants.DEFAULT_SIZE - Constants.SCREEN_WIDTH){
            xOffset = handler.getWorld().getWidth() * Constants.DEFAULT_SIZE - Constants.SCREEN_WIDTH;
        }
        if(yOffset < 0){
            yOffset = 0;
        } else if(yOffset > handler.getWorld().getHeight() * Constants.DEFAULT_SIZE - Constants.SCREEN_HEIGHT){
            yOffset = handler.getWorld().getHeight() * Constants.DEFAULT_SIZE - Constants.SCREEN_HEIGHT;
        }
    }

    public void centerOnEntity(Entity e){
        xOffset = e.getX() - (float)Constants.SCREEN_WIDTH/2 + (float)e.getWidth() /2;
        yOffset = e.getY() - (float)Constants.SCREEN_HEIGHT/2 + (float)e.getHeight() /2;
        hideBlankSpace();
    }

    public void move(float xAmt, float yAmt){
        xOffset += xAmt;
        yOffset += yAmt;
        hideBlankSpace();
    }

    //getters and setters
    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
