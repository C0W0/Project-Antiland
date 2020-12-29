package com.walfen.antiland.mission.talking;

import com.walfen.antiland.mission.Mission;

public class TalkingMission extends Mission {

    public TalkingMission(String title, String desc, int id, int xpGain) {
        super(title, desc, id, xpGain);
        finalProgress = new int[]{1};
        progress = new int[]{0};
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isCompleted() {
        return true;
    }

    @Override
    public void receiveReward() {
        handler.getPlayer().increaseXp(xpGain);
    }
}
