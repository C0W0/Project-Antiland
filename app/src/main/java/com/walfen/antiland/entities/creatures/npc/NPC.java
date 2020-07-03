package com.walfen.antiland.entities.creatures.npc;


import android.graphics.Rect;

import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.tiles.Tile;

public abstract class NPC extends Creature {

    protected int interRange;
    protected String[] messages;


    public NPC(int width, int height, int id) {
        super(width, height, id);
    }

    public boolean interactionCheck(){
//        return isInRangeOf(handler.getWorld().getPlayer(), interRange*64);
        // the circular distance ("actual" distance)
        return new Rect((int)(x-interRange*Tile.TILEWIDTH), (int)(y-interRange*Tile.TILEHEIGHT),
                (int)(x+interRange*Tile.TILEWIDTH+width), (int)(y+interRange*Tile.TILEHEIGHT+height)).
                intersect(handler.getWorld().getPlayer().getCollisionBounds(0,0));
        // block distance
    }

    protected abstract void interact();

    protected abstract void assignMission(int missionID);

    @Override
    public void update() {
        if(interactionCheck()){
            interact();
        }
    }
}
