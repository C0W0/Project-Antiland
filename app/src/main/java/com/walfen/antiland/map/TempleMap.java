package com.walfen.antiland.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.R;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.gfx.ImageLoader;

public class TempleMap extends Map{

    private int objectiveX, objectiveY, hiddenX, hiddenY, combatX, combatY;
    private int smallIconSize, hiddenWidth, hiddenHeight;
    private int playerMapX, playerMapY;
    private Bitmap objBoss, smallCombat, hidden;
    private boolean hiddenRevealed;

    public TempleMap(Handler handler, int mapWidth, int mapHeight) {
        super(handler, Assets.localMap_0, mapWidth, mapHeight);
        smallIconSize = iconSize/2;
        objectiveX = (int)(xRatio*623+mapXDispute);
        objectiveY = (int)(yRatio*185+mapYDispute);
        combatX = (int)(xRatio*600+mapXDispute);
        combatY = (int)(yRatio*381+mapYDispute);
        objBoss = ImageEditor.scaleBitmapForced(objective, smallIconSize);
        smallCombat = ImageEditor.scaleBitmapForced(combat, smallIconSize);
        hidden = ImageEditor.scaleBitmap(ImageLoader.loadImage(R.drawable.localmap_0_hidden), 480*xRatio, 48*yRatio);
        hiddenX = (int)(xRatio*110+mapXDispute);
        hiddenY = (int)(yRatio*235+mapYDispute);
        hiddenWidth = hidden.getWidth();
        hiddenHeight = hidden.getHeight();
        hiddenRevealed = false;
        mapEvents[0] = () -> objBoss = ImageEditor.scaleBitmapForced(boss, smallIconSize);
        mapEvents[1] = () -> hiddenRevealed = true;
    }

    @Override
    protected void postDraw(Canvas canvas) {
        //180, 393
        canvas.drawBitmap(objBoss, null, new Rect(objectiveX, objectiveY,
                objectiveX+iconSize, objectiveY+iconSize),
                Constants.getRenderPaint());
        canvas.drawBitmap(smallCombat, null, new Rect(combatX, combatY,
                        combatX+smallIconSize, combatY+smallIconSize),
                Constants.getRenderPaint());
        if(hiddenRevealed)
            canvas.drawBitmap(hidden, null, new Rect(hiddenX, hiddenY, hiddenX+hiddenWidth,
                    hiddenY+hiddenHeight), Constants.getRenderPaint());
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
