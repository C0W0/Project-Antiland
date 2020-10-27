package com.walfen.antiland.mission.explore;

import android.graphics.Point;
import android.graphics.Rect;

import com.walfen.antiland.mission.Mission;

public abstract class ExploreMission extends Mission {

    protected Rect validRect;
    protected int worldID;

    public ExploreMission(String title, String desc, int id, int xpGain, Point destPos, int worldID, int validRange) { //validRange in blocks
        super(title, desc, id, xpGain);
        this.worldID = worldID;
        validRect = new Rect(destPos.x-validRange*128, destPos.y-validRange*128, destPos.x+validRange*128, destPos.y+validRange*128);
        finalProgress = new int[]{1};
        progress = new int[1];
    }

    public boolean isCompleted(){
        return validRect.contains((int)handler.getPlayer().getX(), (int)handler.getPlayer().getY()) &&
                handler.getGameWorldIndex() == worldID;
    }

}
