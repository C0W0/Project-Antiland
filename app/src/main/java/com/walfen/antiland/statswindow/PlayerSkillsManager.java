package com.walfen.antiland.statswindow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.skills.Skill;
import com.walfen.antiland.entities.properties.skills.passive.SimplePlayerSkill;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.ClickListener;
import com.walfen.antiland.ui.TouchEventListener;
import com.walfen.antiland.ui.UIObject;
import com.walfen.antiland.ui.buttons.UIImageButton;

import java.io.File;
import java.util.ArrayList;

public class PlayerSkillsManager implements TouchEventListener {

    private Handler handler;

    private boolean active = false, buttonJustPressed = false;
    private int skillHeight, skillWidth;
    private int xDispute, yDispute;
    private int pointsCentreX, pointsCentreY;
    private final Bitmap skillScreen;

    private Skill selectedSkill;
    private ArrayList<UIObject> strengthSkills, enduranceSkills, agilitySkills, knowledgeSkills, intelligenceSkills;

    private ArrayList<UIObject> skillUIObjects;
    private ArrayList<UIObject> currentSkills;

    private SimplePlayerSkill strength, endurance, agility, knowledge, intelligence;

    public PlayerSkillsManager(Handler handler, File skillFiles){

        this.handler = handler;

        skillScreen = ImageEditor.scaleBitmap(Assets.skillScreen, Constants.UI_SCREEN_WIDTH, Constants.UI_SCREEN_HEIGHT);
        skillHeight = skillScreen.getHeight();
        skillWidth = skillScreen.getWidth();
        xDispute = Constants.SCREEN_WIDTH/2 - skillWidth/2;
        yDispute = Constants.SCREEN_HEIGHT/2 - skillHeight/2;
        skillUIObjects = new ArrayList<>();
        strengthSkills = new ArrayList<>();
        enduranceSkills = new ArrayList<>();
        agilitySkills = new ArrayList<>();
        knowledgeSkills = new ArrayList<>();
        intelligenceSkills = new ArrayList<>();
        currentSkills = strengthSkills;
        float baseSkillBX = 42.f/512*skillWidth+xDispute;
        float baseSkillBY = 43.f/384*skillHeight+yDispute;
        float baseSkillDX = 36.f/512*skillWidth;
        int baseSkillIconSize = (int)(30.f/512*skillWidth);
        pointsCentreX = (int)(331.f/512*skillWidth+xDispute);
        pointsCentreY = (int)(316.f/384*skillHeight+yDispute);
        skillUIObjects.add(new UIImageButton(baseSkillBX, baseSkillBY, baseSkillIconSize, baseSkillIconSize,
                Assets.strength, () -> currentSkills = strengthSkills));
        skillUIObjects.add(new UIImageButton(baseSkillBX+baseSkillDX, baseSkillBY, baseSkillIconSize, baseSkillIconSize,
                Assets.endurance, () -> currentSkills = enduranceSkills));
        skillUIObjects.add(new UIImageButton(baseSkillBX+baseSkillDX*2, baseSkillBY, baseSkillIconSize, baseSkillIconSize,
                Assets.agility, () -> currentSkills = agilitySkills));
        skillUIObjects.add(new UIImageButton(baseSkillBX+baseSkillDX*3, baseSkillBY, baseSkillIconSize, baseSkillIconSize,
                Assets.knowledge, () -> currentSkills = knowledgeSkills));
        skillUIObjects.add(new UIImageButton(baseSkillBX+baseSkillDX*4, baseSkillBY, baseSkillIconSize, baseSkillIconSize,
                Assets.intelligence, () -> currentSkills = intelligenceSkills));
        skillUIObjects.add(new UIImageButton(378.f/512*skillWidth+xDispute, 296.f/384*skillHeight+yDispute,
                (int)(78.f/512*skillWidth+1), (int)(40.f/384*skillHeight), Assets.unlock, () -> System.out.println("unlock")));

        int skillL1X = (int)(128.f/512*skillWidth+xDispute-32);
        int skillL1Y = (int) (270.f/384*skillHeight+yDispute-64);
        strength = new SimplePlayerSkill(handler, 10,
                () -> {handler.getPlayer().changePhysicalDamage(strength.getLevel()); handler.getPlayer().changeMaxHp(1);});
        strengthSkills.add(new UIImageButton(skillL1X, skillL1Y, 64, 64, Assets.strength, () -> selectedSkill = strength));

        endurance = new SimplePlayerSkill(handler, 10,
                () -> {handler.getPlayer().changeMaxHp(endurance.getLevel()); handler.getPlayer().changeDefence(1);});
        enduranceSkills.add(new UIImageButton(skillL1X, skillL1Y, 64, 64, Assets.endurance, () -> selectedSkill = endurance));

        agility = new SimplePlayerSkill(handler, 10,
                () -> handler.getPlayer().changeSpeed((int)Math.floor(agility.getLevel()/2.f+0.5)));
        agilitySkills.add(new UIImageButton(skillL1X, skillL1Y, 64, 64, Assets.agility, () -> selectedSkill = agility));

        knowledge = new SimplePlayerSkill(handler, 10,
                () -> {handler.getPlayer().changeMaxMp(knowledge.getLevel()); handler.getPlayer().changeMagicalDamage(1);});
        knowledgeSkills.add(new UIImageButton(skillL1X, skillL1Y, 64, 64, Assets.knowledge, () -> selectedSkill = knowledge));

        intelligence = new SimplePlayerSkill(handler, 10,
                () -> handler.getPlayer().changeMagicalDamage((int)Math.floor(intelligence.getLevel()/2.f+0.5)));
        intelligenceSkills.add(new UIImageButton(skillL1X, skillL1Y, 64, 64, Assets.intelligence, () -> selectedSkill = intelligence));

    }
    @Override
    public void onTouchEvent(MotionEvent event) {
        if(!active)
            return;
        for(UIObject o: skillUIObjects)
            o.onTouchEvent(event);
        for(UIObject o: currentSkills)
            o.onTouchEvent(event);
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Canvas canvas) {
        if(!active)
            return;
        canvas.drawBitmap(skillScreen, null, new Rect(xDispute, yDispute, xDispute+skillWidth, yDispute+skillHeight),
                Constants.getRenderPaint());
        for(UIObject o: skillUIObjects)
            o.draw(canvas);
        for(UIObject o: currentSkills)
            o.draw(canvas);
        Rect r = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(44);
        paint.setFakeBoldText(true);
        paint.setColor(Color.BLUE);
        String perkPoints = Integer.toString(handler.getPlayer().getPerkPoints());
        paint.getTextBounds(perkPoints, 0, perkPoints.length(), r);
        canvas.drawText(perkPoints, pointsCentreX-r.width()/2.f, pointsCentreY+r.height()/2.f, paint);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(){
        buttonJustPressed = true;
        active = !active;
    }

    public void setActive(boolean active) {
        buttonJustPressed = true;
        this.active = active;
    }
}
