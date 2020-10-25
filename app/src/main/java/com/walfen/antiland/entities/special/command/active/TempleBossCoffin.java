package com.walfen.antiland.entities.special.command.active;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.gfx.Assets;

public class TempleBossCoffin extends ActiveCommandEntity {

    public TempleBossCoffin() {
        super(1, 1, 3, 1301);
    }

    @Override
    public boolean checkEntityCollision(float xOffset, float yOffset) {
        return false; //no collision
    }

    @Override
    public Rect getCollisionBounds(float xOffset, float yOffset) {
        return new Rect(0, 0, 0, 0);
    }


    @Override
    protected void interact() {
        if(status == 1)
            return;
        status = 1;
//        handler.getPlayer().getMapManager().getMaps()[0].triggerMapEvent(0);
        handler.getWorld().triggerWorldEvent(0);
        Entity e = Entity.entityList[204].clone();
        e.initialize(handler, x, y+400, (int)(x-128), (int)y, 0);
        handler.getWorld().getEntityManager().addEntityHot(e);
    }

    @Override
    protected void drawHeadSign(Canvas canvas) {
        if(status == 1)
            return;
        int iX = (int)(x+width/2-32-handler.getGameCamera().getxOffset());
        int iY = (int)(y-68-handler.getGameCamera().getyOffset());
        Rect destRect = new Rect(iX, iY, iX+64, iY+64);
        canvas.drawBitmap(Assets.headSignOrange, null, destRect, Constants.getRenderPaint());
        canvas.drawBitmap(Assets.hsoMissionComplete, null, destRect, Constants.getRenderPaint());
    }

    @Override
    protected void drawEntity(Canvas canvas) {
        if(status != 1)
            return;
        //draw the cracks on the coffin
    }
}
