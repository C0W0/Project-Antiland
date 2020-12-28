package com.walfen.antiland.mission.collector;

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
        handler.getPlayer().changeWealth(goldGain);
    }
}
