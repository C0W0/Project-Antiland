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

public class MapManager implements TouchEventListener {

    private final Bitmap mapBackGround;
    private int mapWidth, mapHeight, mapXDispute, mapYDispute;

    private int currentMap;
    private Map[] maps;

    private UIImageButton mapSwitchButton, closeButton;
    private boolean active = false, buttonJustPressed = false;
    private Handler handler;
    private Map globalMap;

    public MapManager(Handler handler) {
        this.handler = handler;
        mapBackGround = ImageEditor.scaleBitmap(Assets.mapBackground,
                Constants.UI_SCREEN_WIDTH, Constants.UI_SCREEN_HEIGHT);
        mapWidth = mapBackGround.getWidth();
        mapHeight = mapBackGround.getHeight();
        mapXDispute = Constants.SCREEN_WIDTH/2-mapWidth/2;
        mapYDispute = Constants.SCREEN_HEIGHT/2-mapHeight/2;
        mapSwitchButton = new UIImageButton(mapXDispute+5, mapYDispute+mapHeight-Constants.UI_CLOSE_SIZE-5,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                Assets.switchFlip, this::switchMap);
        closeButton = new UIImageButton(mapXDispute+mapWidth-Constants.UI_CLOSE_SIZE, mapYDispute,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                Assets.close, () -> setActive(false));

        currentMap = 0;
        globalMap = new GlobalMap(handler, mapWidth, mapHeight);

        maps = new Map[10];
        maps[0] = new TempleMap(handler, mapWidth, mapHeight);

    }

    public void loadMap(String path){

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
        if(currentMap == -1)
            globalMap.onTouchEvent(event);
        else
            maps[currentMap].onTouchEvent(event);
    }

    @Override
    public void update() {
        if(!active)
            return;
        if(currentMap == -1)
            globalMap.update();
        else
            maps[currentMap].update();

    }

    @Override
    public void draw(Canvas canvas) {
        if(!active)
            return;
        canvas.drawBitmap(mapBackGround, null, new Rect
                        (mapXDispute, mapYDispute, mapXDispute+mapWidth, mapYDispute+mapHeight),
                Constants.getRenderPaint());
        if(currentMap == -1)
            globalMap.draw(canvas);
        else
            maps[currentMap].draw(canvas);
        mapSwitchButton.draw(canvas);
        closeButton.draw(canvas);
    }

    private void switchMap(){
        if(currentMap == -1)
            currentMap = handler.getGameWorldIndex();
        else
            currentMap = -1;
    }

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

    public Map getCurrentMap() {
        return currentMap==-1?globalMap:maps[currentMap];
    }

    public Map[] getMaps() {
        return maps;
    }
}
