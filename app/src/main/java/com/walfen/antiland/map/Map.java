package com.walfen.antiland.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.ChangeEvent;
import com.walfen.antiland.ui.TouchEventListener;
import com.walfen.antiland.ui.buttons.UIImageButton;

public abstract class Map implements TouchEventListener {

    protected int mapWidth, mapHeight, mapXDispute, mapYDispute;
    protected Handler handler;
    protected final Bitmap map;
    protected float xRatio, yRatio;
    protected int iconSize;
    protected Bitmap portal, exit, combat, boss, unknown, objective;

    protected ChangeEvent[] mapEvents;

    public Map(Handler handler, Bitmap map, int mapWidth, int mapHeight){
        this.handler = handler;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        xRatio = (float)mapWidth/map.getWidth();
        yRatio = (float)mapHeight/map.getHeight();
        iconSize = (int)(xRatio*39);
        portal = ImageEditor.scaleBitmapForced(Assets.portalIcon, iconSize);
        exit = ImageEditor.scaleBitmapForced(Assets.exitIcon, iconSize);
        combat = ImageEditor.scaleBitmapForced(Assets.combatIcon, iconSize);
        boss = ImageEditor.scaleBitmapForced(Assets.bossIcon, iconSize);
        unknown = ImageEditor.scaleBitmapForced(Assets.unknownIcon, iconSize);
        objective = ImageEditor.scaleBitmapForced(Assets.objectiveIcon, iconSize);
        mapXDispute = Constants.SCREEN_WIDTH/2-mapWidth/2;
        mapYDispute = Constants.SCREEN_HEIGHT/2-mapHeight/2;
        this.map = ImageEditor.scaleBitmap(map, mapWidth, mapHeight);
        mapEvents = new ChangeEvent[32];
    }

    public void load(String path){

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(map, null, new Rect
                        (mapXDispute, mapYDispute, mapXDispute+mapWidth, mapYDispute+mapHeight),
                Constants.getRenderPaint());
        postDraw(canvas);
    }

    protected abstract void postDraw(Canvas canvas);

    public void triggerMapEvent(int eventID){
        mapEvents[eventID].onChange();
    }

    public void save(){

    }
}
