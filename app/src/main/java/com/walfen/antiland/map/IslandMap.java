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

public class IslandMap extends Map{
    private int objectiveX, objectiveY;
    private int playerMapX, playerMapY;
    public IslandMap(Handler handler, int mapWidth, int mapHeight) {
        super(handler, Assets.localMap_1, mapWidth, mapHeight);
        objectiveX = (int)(xRatio*623+mapXDispute);
        objectiveY = (int)(yRatio*185+mapYDispute);
    }

    @Override
    protected void postDraw(Canvas canvas) {
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
        playerMapX=(int)((playerTruePos*5.15+142)*xRatio+mapXDispute);


    }

    private void computePlayerMapY(){
        int playerTruePos = (int)((handler.getPlayer().getY()+64)/128);
//        System.out.println(((playerTruePos+(handler.getGameWorldIndex()==0?14:0))*12+114)*yRatio+mapYDispute);
        playerMapY=(int)((playerTruePos*5.15+44)*yRatio+mapYDispute);
    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }
}
