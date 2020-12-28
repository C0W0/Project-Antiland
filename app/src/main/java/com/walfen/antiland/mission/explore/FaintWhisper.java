package com.walfen.antiland.mission.explore;

import android.graphics.Point;

public class FaintWhisper extends ExploreMission {

    public FaintWhisper() {
        super("Faint Whisper", "Identify the voice", 7, 25, new Point(9856, 9856), 2, 3);
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
