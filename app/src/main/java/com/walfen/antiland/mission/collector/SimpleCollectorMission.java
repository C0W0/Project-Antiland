package com.walfen.antiland.mission.collector;

import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.items.Item;

public class SimpleCollectorMission extends CollectorMission {

    private int goldGain;

    public SimpleCollectorMission(String title, String desc, int itemId, int count, int id, int xpGain, int goldGain) {
        super(title, desc, id, 1, xpGain);
        targetItemID[0] = itemId;
        finalProgress[0] = count;
        this.goldGain = goldGain;
    }

    @Override
    public void receiveReward() {
        super.receiveReward();
        for(Item i: handler.getPlayer().getInventory().getInventoryItems())
            if(i.getId() == targetItemID[0])
                i.setCount(i.getCount() - finalProgress[0]);
        handler.getPlayer().changeWealth(goldGain);
    }

    public static class GotASpare extends SimpleCollectorMission{

        public GotASpare() {
            super("Got a Spare?", "Return the shop key", Item.key.getId(), 1, 3, 20, 50);
        }

        @Override
        public void receiveReward() {
            super.receiveReward();
            handler.getWorld().getEntityManager().modifyEntityHot("Mr. Fox Keeper", 1);
        }

    }

    public static class GhostTown extends SimpleCollectorMission{

        public GhostTown() {
            super("Ghost Town", "Fetch the Blacksmith's hammer", Item.hammer.getId(), 1, 4, 20, 0);
        }

        @Override
        public void receiveReward() {
            super.receiveReward();
            handler.getWorld().getEntityManager().modifyEntityHot("Ms. Chi-Ken", 1);
            handler.getWorld().getEntityManager().modifyEntityHot("Captain Cactus", 1);
            handler.getWorld().getEntityManager().modifyEntityHot("The Hermit", 1);
        }

    }
}
