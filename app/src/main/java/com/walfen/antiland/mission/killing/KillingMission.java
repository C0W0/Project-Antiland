package com.walfen.antiland.mission.killing;

import com.walfen.antiland.Handler;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.mission.Mission;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class KillingMission extends Mission {

    protected int[] targetEntityID;
    protected int[] beginningCount;

    public KillingMission(String title, String desc, int id, int subMissions, int xpGain) {
        super(title, desc, id, xpGain);
        targetEntityID = new int[subMissions];
        finalProgress = new int[subMissions];
        progress = new int[subMissions];
        beginningCount = new int[subMissions];
        for(int i = 0; i < subMissions; i++){
            progress[i] = 0;
        }
    }

    @Override
    public void update() {
        for(int i = 0; i < targetEntityID.length; i++)
            progress[i] = Math.min(handler.getPlayer().getTracker().getTrackingCount(targetEntityID[i]) -
                    beginningCount[i], finalProgress[i]);
    }

    @Override
    public boolean isCompleted() {
        return Arrays.equals(progress,finalProgress);
    }

    @Override
    public void setHandler(Handler handler) {
        super.setHandler(handler);
        if(handler == null || handler.getPlayer() == null)
            return;
        for(int i = 0; i < beginningCount.length; i++){
            beginningCount[i] = handler.getPlayer().getTracker().trackKillCount(targetEntityID[i]);
        }
    }

    @Override
    public void setProgress(int[] progress, KillTracker tracker) {
        super.setProgress(progress);
        for(int i = 0; i < beginningCount.length; i++){
            tracker.trackKillCount(targetEntityID[i]);
            beginningCount[i] = -progress[i];
        }
    }
}
