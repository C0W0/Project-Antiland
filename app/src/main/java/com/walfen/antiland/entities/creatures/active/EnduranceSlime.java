package com.walfen.antiland.entities.creatures.active;

import com.walfen.antiland.mission.Mission;

public class EnduranceSlime extends Slime {

    public EnduranceSlime(){
        super(5, 210);
        setEntityHealth(50);
    }

    @Override
    public void update() {
        super.update();
        boolean missionInProgress = false;
        for(Mission m: handler.getPlayer().getMissionManager().getMissions()){
            if(m.getId() >= 9 && m.getId() <= 12){
                if(m.getId() == 12 && Mission.missions[12].isCompleted())
                    break;
                missionInProgress = true;
                break;
            }
        }
        if(!missionInProgress){
            health = 0;
            active = false;
        }

    }
}
