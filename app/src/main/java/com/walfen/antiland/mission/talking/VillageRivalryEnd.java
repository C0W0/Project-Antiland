package com.walfen.antiland.mission.talking;

import com.walfen.antiland.items.Item;

public class VillageRivalryEnd extends TalkingMission {
    public VillageRivalryEnd() {
        super("Village Rivalry 4", "Settle the dispute between two villagers", 17, 5);
    }

    @Override
    public void receiveReward() {
        super.receiveReward();
        handler.getPlayer().getInventory().addItem(Item.fish.addToInv(1));
    }
}
