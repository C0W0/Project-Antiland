package com.walfen.antiland.entities.properties.skills.active.knowledge;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.entities.properties.skills.passive.PassiveSkill;
import com.walfen.antiland.gfx.Assets;

public class Meditation extends ActiveSkill {


    public Meditation(Handler handler) {
        super(handler, 5, 10000, Assets.knowledgeSkills[0], 301);
    }

    @Override
    protected void onLevelUp() {

    }

    @Override
    public boolean levelUpReqMeet() {
        return handler.getPlayer().getSkillsManager().getKnowledge().getLevel() > level;
    }

    @Override
    public String getTitle() {
        return "Meditation";
    }

    @Override
    public String getDesc() {
        return "Empty your mind and listen to the voice of the universe.";
    }

    @Override
    public String getEffect() {
        return "Regenerate "+level+" mana";
    }

    @Override
    public String getReq() {
        return "Knowledge: Lv."+(level+1);
    }

    @Override
    protected void onTrigger() {
        handler.getPlayer().changeMp(level);
    }

    @Override
    protected void updateData() {

    }

    @Override
    public void draw(Canvas canvas) {

    }
}
