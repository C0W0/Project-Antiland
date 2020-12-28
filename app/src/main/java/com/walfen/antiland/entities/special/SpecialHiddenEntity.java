package com.walfen.antiland.entities.special;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.gfx.Assets;

public abstract class SpecialHiddenEntity extends Entity {

    public SpecialHiddenEntity(int width, int height, int id) {
        super(width, height, id);
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
    public void receiveDamage(int num, int type) { }

    @Override
    protected void onDeath() { }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return Assets.NULL;
    }

    @Override
    public String getName() {
        return "You are not supposed to see this";
    }

}
