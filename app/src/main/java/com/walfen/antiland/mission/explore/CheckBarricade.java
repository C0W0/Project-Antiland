package com.walfen.antiland.mission.explore;

import android.graphics.Point;

public class CheckBarricade extends ExploreMission {

    public CheckBarricade() {
        super("To Block or not to Block?", "Check up on the barricade near Far Harbour", 5, 25, new Point(7167, 8704), 2, 3);
    }

    @Override
    public void update() {
        if(isCompleted())
            complete();
    }

    @Override
    public void receiveReward() {

    }
}
