package com.walfen.antiland.entities.special.command.active;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Assets;

public class TempleBossCoffin extends ActiveCommandEntity {

    private boolean interacted;

    public TempleBossCoffin() {
        super(1, 1, 3, 1301);
        interacted = false;
    }


    @Override
    protected void interact() {
        if(interacted)
            return;
        handler.getPlayer().getMapManager().getMaps()[0].triggerMapEvent(0);
//        Entity e = Entity.entityList[202].clone();
//        e.initialize(handler, x, y+400, (int)(x-128), (int)y);
//        handler.getWorld().getEntityManager().addEntityHot(e);
    }

    @Override
    protected void drawHeadSign(Canvas canvas) {
        if(interacted)
            return;
        int iX = (int)(x+width/2-32-handler.getGameCamera().getxOffset());
        int iY = (int)(y-68-handler.getGameCamera().getyOffset());
        Rect destRect = new Rect(iX, iY, iX+64, iY+64);
        canvas.drawBitmap(Assets.headSignOrange, null, destRect, Constants.getRenderPaint());
        canvas.drawBitmap(Assets.hsoMissionComplete, null, destRect, Constants.getRenderPaint());
    }

    @Override
    protected void drawEntity(Canvas canvas) {
        if(!interacted)
            return;
        //draw the cracks on the coffin
    }
}
