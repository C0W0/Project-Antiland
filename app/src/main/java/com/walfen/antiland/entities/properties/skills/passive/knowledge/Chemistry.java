package com.walfen.antiland.entities.properties.skills.passive.knowledge;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.skills.passive.PassiveSkill;
import com.walfen.antiland.gfx.Assets;

public class Chemistry extends PassiveSkill {

    public Chemistry(Handler handler) {
        super(handler, 10, null, Assets.knowledgeSkills[4]);
    }

    @Override
    protected void onLevelUp() {
        handler.getPlayer().changeMagicalDefence(1);
    }

    @Override
    public boolean levelUpReqMeet() {
        return handler.getPlayer().getSkillsManager().getKnowledge().getLevel() > level;
    }

    @Override
    public String getTitle() {
        return "Chemistry";
    }

    @Override
    public String getDesc() {
        return "A pre-requite of a lot of spells";
    }

    @Override
    public String getEffect() {
        return "Increase magical defence by 1";
    }

    @Override
    public String getReq() {
        return "Knowledge: Lv."+(level+1);
    }
}
