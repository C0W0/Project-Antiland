package com.walfen.antiland.entities.special.command.passive;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Handler;

public class WorldGate extends PassiveCommandEntity {

    private long playerEnterTime;
    private Rect actionBounds;
    private int targetWorld, targetX, targetY;

    public WorldGate(int id, int targetWorld, int targetX, int targetY) {
        super(id);
        playerEnterTime = -1;
        actionBounds = new Rect(0, 0, 128, 128);
        this.targetWorld = targetWorld;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    protected void commandAction() {
        handler.setGameWorld(targetWorld, targetX, targetY);
        handler.getGame().getGameState().autoSave();
    }

    @Override
    protected boolean isActionAllowed() {
        int left = (int) (x+actionBounds.left);
        int top = (int) (y+actionBounds.top);
        Rect currBounds = new Rect(left, top, left+actionBounds.width(), top+actionBounds.height());
        if(handler.getPlayer().getCollisionBounds(0, 0).intersect(currBounds) && playerEnterTime == -1){
            playerEnterTime = System.currentTimeMillis();
            return false;
        }
        else if (!handler.getPlayer().getCollisionBounds(0, 0).intersect(currBounds) && playerEnterTime != -1){
            playerEnterTime = -1;
            return false;
        }
        return playerEnterTime != -1 && System.currentTimeMillis() - playerEnterTime > 500;
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
