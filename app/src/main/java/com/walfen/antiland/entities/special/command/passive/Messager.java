package com.walfen.antiland.entities.special.command.passive;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Messager extends PassiveCommandEntity {

    protected Rect actionBounds;

    public Messager(int id) {
        super(id);
        actionBounds = new Rect(0, 0, 128, 128);
    }

    @Override
    protected boolean isActionAllowed() {
        int left = (int) (x+actionBounds.left);
        int top = (int) (y+actionBounds.top);
        Rect currBounds = new Rect(left, top, left+actionBounds.width(), top+actionBounds.height());
        return handler.getPlayer().getCollisionBounds(0, 0).intersect(currBounds);
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
