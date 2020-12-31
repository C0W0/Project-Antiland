package com.walfen.antiland.mission.explore;

import android.graphics.Point;

import com.walfen.antiland.Handler;
import com.walfen.antiland.R;

public class CheckBarricade extends ExploreMission {

    public CheckBarricade() {
        super("To Block or not to Block?", "Check up on the barricade near Far Harbour", 5, 25, new Point(7168, 8704), 2, 3);
    }//78, 80

    @Override
    public void update() {
        if(isCompleted())
            complete();
    }

    @Override
    public void receiveReward() {

    }

    @Override
    public void setHandler(Handler handler) {
        super.setHandler(handler);
        if(handler != null)
            handler.getGame().getMusicController().changeMusic(R.raw.ghost_town);
    }
}
