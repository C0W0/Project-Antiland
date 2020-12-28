package com.walfen.antiland.mission.colloector;

import com.walfen.antiland.items.Item;

public class SimpleCollectorMission extends CollectorMission {

    public SimpleCollectorMission(String title, String desc, int itemId, int count, int id) {
        super(title, desc, id, 1, count/2);
        targetItemID[0] = itemId;
        finalProgress[0] = count;
    }
}
