package com.walfen.antiland.mission.killing;

public class TempleMasterMind extends KillingMission {

    public TempleMasterMind() {
        super("Temple Master Mind", "The evil spirit is attempting to use your life as energy to break open the temple. Kill him or be killed.", 2, 1, 15);
        finalProgress = new int[]{1};
        targetEntityID = new int[]{204};
    }

    @Override
    public void receiveReward() {
        handler.getPlayer().increaseXp(xpGain);
    }

    @Override
    public void update() {
        super.update();
        if(isCompleted())
            complete();
    }
}
