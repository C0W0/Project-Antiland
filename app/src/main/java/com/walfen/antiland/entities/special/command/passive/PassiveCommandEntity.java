package com.walfen.antiland.entities.special.command.passive;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.special.SpecialHiddenEntity;

public abstract class PassiveCommandEntity extends SpecialHiddenEntity {


    public PassiveCommandEntity(int id) {
        super(1, 1, id);
    }



    @Override
    public void update() {
        if(isActionAllowed())
            commandAction();
    }

    protected abstract void commandAction();

    protected abstract boolean isActionAllowed();

}
