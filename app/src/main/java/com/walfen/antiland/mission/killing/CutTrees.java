package com.walfen.antiland.mission.killing;

import com.walfen.antiland.entities.Entity;

public class CutTrees extends KillingMission {

    public CutTrees(String title, String desc, int id, int count) {
        super(title, desc, id, 1);
        targetEntityID[0] = Entity.tree.getId();
        finalProgress[0] = count;
    }

}
