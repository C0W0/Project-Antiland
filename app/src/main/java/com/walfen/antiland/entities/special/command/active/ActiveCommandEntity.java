package com.walfen.antiland.entities.special.command.active;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.walfen.antiland.entities.creatures.npc.NPC;
import com.walfen.antiland.gfx.Assets;

public abstract class ActiveCommandEntity extends NPC {

    public ActiveCommandEntity(int width, int height, int interRange, int id) {
        super(width, height, id);
        setInteractType(InteractionType.TRIGGER_ENTITY);
        this.interRange = interRange;
    }

    @Override
    protected void onDeath() { }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return Assets.NULL;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void receiveDamage(int num, int type) { }
}
