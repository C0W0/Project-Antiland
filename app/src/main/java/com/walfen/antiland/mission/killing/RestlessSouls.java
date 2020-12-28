package com.walfen.antiland.mission.killing;

public class RestlessSouls extends KillingMission {

    public RestlessSouls() {
        super("Restless Souls", "Defeat the Spirit Worrior", 8, 1, 50);
        finalProgress = new int[]{1};
        targetEntityID = new int[]{204};
    }

    @Override
    public void update() {
        super.update();
        if(isCompleted())
            complete();
    }

    @Override
    public void receiveReward() {

    }
}
