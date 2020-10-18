package com.walfen.antiland.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.TouchEventListener;
import com.walfen.antiland.ui.buttons.UIImageButton;

public abstract class Map implements TouchEventListener {

    protected int mapWidth, mapHeight, mapXDispute, mapYDispute;
    protected UIImageButton mapSwitchButton, closeButton;
    protected Handler handler;
    protected boolean active = false, buttonJustPressed = false;
    protected final Bitmap mapBackGround, map;

    public Map(Handler handler, Bitmap map){
        this.handler = handler;
        mapBackGround = ImageEditor.scaleBitmap(Assets.mapBackground,
                Constants.UI_SCREEN_WIDTH, Constants.UI_SCREEN_HEIGHT);
        mapWidth = mapBackGround.getWidth();
        mapHeight = mapBackGround.getHeight();
        mapXDispute = Constants.SCREEN_WIDTH/2-mapWidth/2;
        mapYDispute = Constants.SCREEN_HEIGHT/2-mapHeight/2;
        this.map = ImageEditor.scaleBitmap(map, mapWidth, mapHeight);
        mapSwitchButton = new UIImageButton(mapXDispute+5, mapYDispute+mapHeight-Constants.UI_CLOSE_SIZE-5,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                Assets.switchFlip, () -> System.out.println("Open another map"));
        closeButton = new UIImageButton(mapXDispute+mapWidth-Constants.UI_CLOSE_SIZE, mapYDispute,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                Assets.close, () -> setActive(false));
    }


    @Override
    public void onTouchEvent(MotionEvent event) {
        if(!active)
            return;

        if(buttonJustPressed) {
            buttonJustPressed = false;
            return;
        }
        mapSwitchButton.onTouchEvent(event);
        closeButton.onTouchEvent(event);
        postTouchEvent(event);
    }

    protected abstract void postTouchEvent(MotionEvent event);

    @Override
    public void draw(Canvas canvas) {
        if(!active)
            return;
        canvas.drawBitmap(mapBackGround, null, new Rect
                (mapXDispute, mapYDispute, mapXDispute+mapWidth, mapYDispute+mapHeight),
                Constants.getRenderPaint());
        canvas.drawBitmap(map, null, new Rect
                        (mapXDispute, mapYDispute, mapXDispute+mapWidth, mapYDispute+mapHeight),
                Constants.getRenderPaint());
        postDraw(canvas);
        mapSwitchButton.draw(canvas);
        closeButton.draw(canvas);
    }

    protected abstract void postDraw(Canvas canvas);

    public boolean isActive() {
        return active;
    }

    public void setActive(){
        buttonJustPressed = true;
        active = !active;
    }

    public void setActive(boolean active) {
        buttonJustPressed = true;
        this.active = active;
    }
}
