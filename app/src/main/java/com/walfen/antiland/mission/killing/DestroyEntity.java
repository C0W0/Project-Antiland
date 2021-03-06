package com.walfen.antiland.mission.killing;

import com.walfen.antiland.items.Item;
import com.walfen.antiland.ui.ChangeEvent;

public class DestroyEntity extends KillingMission {

    public DestroyEntity(String title, String desc, int[] targetEntities, int id, int[] count, int xpGain, int rewardItemID, int rewardItemCount) {
        super(title, desc, id, targetEntities.length, xpGain);
        finalProgress = count;
        targetEntityID = targetEntities;
        this.rewardItemID = rewardItemID;
        this.rewardItemCount = rewardItemCount;
    }

    @Override
    public void receiveReward() {
        handler.getPlayer().increaseXp(xpGain);
        if(rewardItemCount == -1)
            handler.getPlayer().changeWealth(30);
        else
            handler.getPlayer().getInventory().addItem(Item.items[rewardItemID].addToInv(rewardItemCount));
    }

    @Override
    public boolean isCompleted() {
        return super.isCompleted() && status != 2;
    }
}
