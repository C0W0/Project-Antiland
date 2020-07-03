package com.walfen.antiland.mission.colloector;


import com.walfen.antiland.items.Item;
import com.walfen.antiland.mission.Mission;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class CollectorMission extends Mission {

    protected int[] targetItemID;

    public CollectorMission(String title, String desc, int id, int subMissions) {
        super(title, desc, id);
        targetItemID = new int[subMissions];
        finalProgress = new int[subMissions];
        progress = new int[subMissions];
        for(int i = 0; i < subMissions; i++){
            progress[i] = 0;
        }
    }

    @Override
    public boolean isCompleted() {
        ArrayList<Item> inventoryItems = handler.getWorld().getPlayer().getInventory().getInventoryItems();
        for(int i = 0; i < inventoryItems.size(); i++){
            Item tempItem = inventoryItems.get(i);
            for(int j = 0; j < targetItemID.length; j++){
                if(tempItem.getId() == targetItemID[j]){
                    progress[j] = Math.min(tempItem.getCount(), finalProgress[j]);
                }
            }
        }
        return Arrays.equals(progress,finalProgress);
    }

    @Override
    public void complete() {

    }
}
