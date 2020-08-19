package com.walfen.antiland.entities.special;

import android.graphics.Rect;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;

public abstract class CommandEntity extends Entity {

    protected Handler handler;

    public CommandEntity(Handler handler, int id) {
        super(1, 1, id);
        this.handler = handler;
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
    public void update() {
        if(isActionAllowed())
            commandAction();
    }

    protected abstract void commandAction();

    protected abstract boolean isActionAllowed();

}
