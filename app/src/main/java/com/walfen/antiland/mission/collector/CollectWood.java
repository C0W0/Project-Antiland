package com.walfen.antiland.mission.collector;


import com.walfen.antiland.items.Item;

public class CollectWood extends CollectorMission {

    public CollectWood(String title, String desc, int id, int count) {
        super(title, desc, id, 1, count/2);
        targetItemID[0] = Item.woodItem.getId(); // do it this way to make the code more readable
        finalProgress[0] = count;
    }

}
