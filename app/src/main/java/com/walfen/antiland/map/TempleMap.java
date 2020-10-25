package com.walfen.antiland.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;

public class TempleMap extends Map{

    private int exitX, exitY, objectiveX, objectiveY, portalX, portalY, combatX, combatY;
    private int smallIconSize;
    private int playerMapX, playerMapY;
    private Bitmap objBoss, smallCombat;

    public TempleMap(Handler handler, int mapWidth, int mapHeight) {
        super(handler, Assets.localMap_0, mapWidth, mapHeight);
        smallIconSize = iconSize/2;
        exitX = (int)(xRatio*110+mapXDispute);
        exitY = (int)(yRatio*240+mapYDispute);
        objectiveX = (int)(xRatio*623+mapXDispute);
        objectiveY = (int)(yRatio*185+mapYDispute);
//        portalX = (int)(xRatio*620+mapXDispute);
//        portalY = (int)(yRatio*336+mapYDispute);
        combatX = (int)(xRatio*600+mapXDispute);
        combatY = (int)(yRatio*381+mapYDispute);
        objBoss = ImageEditor.scaleBitmapForced(objective, smallIconSize);
        smallCombat = ImageEditor.scaleBitmapForced(combat, smallIconSize);
        mapEvents[0] = () -> objBoss = ImageEditor.scaleBitmapForced(boss, smallIconSize);;
    }

    @Override
    protected void postDraw(Canvas canvas) {
        //180, 393
        canvas.drawBitmap(exit, null,
                new Rect(exitX, exitY, exitX+iconSize, exitY+iconSize),
                Constants.getRenderPaint());
        canvas.drawBitmap(objBoss, null, new Rect(objectiveX, objectiveY,
                objectiveX+iconSize, objectiveY+iconSize),
                Constants.getRenderPaint());
//        canvas.drawBitmap(portal, null, new Rect(portalX, portalY,
//                        portalX+iconSize, portalY+iconSize),
//                Constants.getRenderPaint());
        canvas.drawBitmap(smallCombat, null, new Rect(combatX, combatY,
                        combatX+smallIconSize, combatY+smallIconSize),
                Constants.getRenderPaint());
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(new Rect(playerMapX, playerMapY, playerMapX+12, playerMapY+12), paint);
    }

    @Override
    public void update() {
        computePlayerMapX();
        computePlayerMapY();
    }

    private void computePlayerMapX(){
        int playerTruePos = (int)((handler.getPlayer().getX()+64)/128);
        playerMapX = (int)(((playerTruePos+(handler.getGameWorldIndex()==0?28:0))*12+10)*xRatio)+mapXDispute;
    }

    private void computePlayerMapY(){
        int playerTruePos = (int)((handler.getPlayer().getY()+64)/128);
//        System.out.println(((playerTruePos+(handler.getGameWorldIndex()==0?14:0))*12+114)*yRatio+mapYDispute);
        playerMapY = (int)(((playerTruePos+(handler.getGameWorldIndex()==0?14:0))*12+114)*yRatio)+mapYDispute;
    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }
}
