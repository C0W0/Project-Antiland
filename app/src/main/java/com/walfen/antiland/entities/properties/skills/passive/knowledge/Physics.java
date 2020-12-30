package com.walfen.antiland.entities.properties.skills.passive.knowledge;

import android.graphics.Bitmap;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.skills.passive.PassiveSkill;
import com.walfen.antiland.gfx.Assets;

public class Physics extends PassiveSkill {

    public Physics(Handler handler) {
        super(handler, 10, null, Assets.knowledgeSkills[3]);
    }

    @Override
    protected void onLevelUp() {
        handler.getPlayer().changeMaxMp(1);
        handler.getPlayer().changeMp(1);
    }

    @Override
    public boolean levelUpReqMeet() {
        return handler.getPlayer().getSkillsManager().getKnowledge().getLevel() > level;
    }

    @Override
    public String getTitle() {
        return "Physics";
    }

    @Override
    public String getDesc() {
        return "A pre-requite of a lot of spells";
    }

    @Override
    public String getEffect() {
        return "Increase max mp by 1";
    }

    @Override
    public String getReq() {
        return "Knowledge: Lv."+(level+1);
    }
}
