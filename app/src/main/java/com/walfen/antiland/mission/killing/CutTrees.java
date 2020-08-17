package com.walfen.antiland.mission.killing;

import com.walfen.antiland.entities.Entity;

public class CutTrees extends KillingMission {

    public CutTrees(String title, String desc, int id, int count) {
        super(title, desc, id, 1, 5);
        targetEntityID[0] = Entity.tree.getId();
        finalProgress[0] = count;
    }

    @Override
    public void receiveReward() {
        handler.getPlayer().increaseXp(xpGain);
    }

}
