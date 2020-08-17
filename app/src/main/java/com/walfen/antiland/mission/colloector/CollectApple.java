package com.walfen.antiland.mission.colloector;


import com.walfen.antiland.items.Item;

public class CollectApple extends CollectorMission {

    public CollectApple(String title, String desc, int id, int count) {
        super(title, desc, id, 1, count/2);
        targetItemID[0] = Item.apple.getId();
        finalProgress[0] = count;
    }
}
