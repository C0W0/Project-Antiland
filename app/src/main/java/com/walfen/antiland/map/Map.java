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
import com.walfen.antiland.untils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Map implements TouchEventListener {

    protected int mapWidth, mapHeight, mapXDispute, mapYDispute;
    protected Handler handler;
    protected final Bitmap map;
    protected float xRatio, yRatio;
    protected int iconSize;
    protected Bitmap portal, exit, combat, boss, unknown, objective;

    protected ChangeEvent[] mapEvents;
    protected ArrayList<Integer> triggeredEvents;

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
        triggeredEvents = new ArrayList<>();
    }

    public void load(String path){
        try{
            String[] tokens = Utils.loadFileAsString(new FileInputStream(new File(path))).split("\\s+");
            if(tokens.length == 0 || tokens[0].equals(""))
                return;
            for(String str: tokens){
                triggerMapEvent(Utils.parseInt(str));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
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
        if(!triggeredEvents.contains(eventID)){
            mapEvents[eventID].onChange();
            triggeredEvents.add(eventID);
        }
    }

    public void save(String path){
        try{
            File file = new File(path);
            PrintWriter writer = new PrintWriter(file);
            for(int i: triggeredEvents)
                writer.print(i+" ");
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
