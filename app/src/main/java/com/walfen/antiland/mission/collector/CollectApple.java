package com.walfen.antiland.mission.collector;


import com.walfen.antiland.items.Item;

public class CollectApple extends CollectorMission {

    public CollectApple(String title, String desc, int id, int count) {
        super(title, desc, id, 1, count/2);
        targetItemID[0] = Item.apple.getId();
        finalProgress[0] = count;
    }

    @Override
    public boolean isCompleted() {
        return super.isCompleted() && status != 2;
    }

    @Override
    public void receiveReward() {
        super.receiveReward();
        for(Item i: handler.getPlayer().getInventory().getInventoryItems())
            if(i.getId() == targetItemID[0])
                i.setCount(i.getCount() - finalProgress[0]);
    }
}
