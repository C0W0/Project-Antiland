package com.walfen.antiland.mission.explore;

import android.graphics.Point;

import com.walfen.antiland.entities.properties.effect.passive.MentalUnrest;

public class TempleEscape extends ExploreMission {

    public TempleEscape() {
        super("Temple Escape", "Falling into a strange place for no reason. Have to somehow get out as soon as possible.", 1, 40, new Point(1792, 1664), 1, 3);
    }

    @Override
    public void update() {
        if(isCompleted())
            complete();
    }

    @Override
    public void receiveReward() {
        handler.getPlayer().increaseXp(25);
        handler.getPlayer().addEffect(new MentalUnrest(handler.getPlayer()));
    }
}
