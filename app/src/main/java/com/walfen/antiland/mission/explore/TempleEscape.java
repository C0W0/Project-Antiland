package com.walfen.antiland.mission.explore;

import android.graphics.Point;

import com.walfen.antiland.entities.properties.effect.passive.MentalUnrest;

public class TempleEscape extends ExploreMission {

    public TempleEscape() {
        super("Temple Run", "Escape the temple", 1, 40, new Point(1792, 1664), 1, 3);
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
