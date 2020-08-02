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
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.entities.properties.skills.active.SharpWind;
import com.walfen.antiland.entities.properties.skills.passive.SimplePlayerSkill;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.ClickListener;
import com.walfen.antiland.ui.TouchEventListener;
import com.walfen.antiland.ui.UIObject;
import com.walfen.antiland.ui.buttons.SkillButton;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.ui.drag.DraggableUI;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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

    private SkillSlotIcon[] activeSkillSlot;

    //5 base:
    private SimplePlayerSkill strength, endurance, agility, knowledge, intelligence;
    //strength skills:
    private Skill sharpWind;

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
        activeSkillSlot = new SkillSlotIcon[3];

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
                (int)(78.f/512*skillWidth+1), (int)(40.f/384*skillHeight), Assets.unlock, this::upgradeSkill));
        for(int i = 0; i < 3; i++){
            int d = (int)(64.f/512*skillWidth);
            int x = (int)(38.f/512*skillWidth+xDispute+d*i);
            int y = (int)(293.f/384*skillHeight+yDispute);
            int sideLength = (int)(53.f/384*skillHeight);
            activeSkillSlot[i] = new SkillSlotIcon(new Rect(x, y, x+sideLength, y+sideLength));
        }
        skillUIObjects.addAll(Arrays.asList(activeSkillSlot));

        baseSkillIconSize *= 1.2;
        int skillL1X = (int)(128.f/512*skillWidth+xDispute-baseSkillIconSize/2);
        int skillL1Y = (int) (283.f/384*skillHeight+yDispute-baseSkillIconSize);
        strength = new SimplePlayerSkill(handler, 10,
                () -> {handler.getPlayer().changePhysicalDamage(strength.getLevel()); handler.getPlayer().changeMaxHp(1);},
                Assets.strengthR);
        strengthSkills.add(new UIImageButton(skillL1X, skillL1Y, baseSkillIconSize, baseSkillIconSize, Assets.strengthR, () -> selectedSkill = strength));

        endurance = new SimplePlayerSkill(handler, 10,
                () -> {handler.getPlayer().changeMaxHp(endurance.getLevel()); handler.getPlayer().changeDefence(1);},
                Assets.enduranceR);
        enduranceSkills.add(new UIImageButton(skillL1X, skillL1Y, baseSkillIconSize, baseSkillIconSize, Assets.enduranceR, () -> selectedSkill = endurance));

        agility = new SimplePlayerSkill(handler, 10,
                () -> handler.getPlayer().changeSpeed((int)Math.floor(agility.getLevel()/2.f+0.5)), Assets.agilityR);
        agilitySkills.add(new UIImageButton(skillL1X, skillL1Y, baseSkillIconSize, baseSkillIconSize, Assets.agilityR, () -> selectedSkill = agility));

        knowledge = new SimplePlayerSkill(handler, 10,
                () -> {handler.getPlayer().changeMaxMp(knowledge.getLevel()); handler.getPlayer().changeMagicalDamage(1);},
                Assets.knowledgeR);
        knowledgeSkills.add(new UIImageButton(skillL1X, skillL1Y, baseSkillIconSize, baseSkillIconSize, Assets.knowledgeR, () -> selectedSkill = knowledge));

        intelligence = new SimplePlayerSkill(handler, 10,
                () -> handler.getPlayer().changeMagicalDamage((int)Math.floor(intelligence.getLevel()/2.f+0.5)), Assets.intelligenceR);
        intelligenceSkills.add(new UIImageButton(skillL1X, skillL1Y, baseSkillIconSize, baseSkillIconSize, Assets.intelligenceR, () -> selectedSkill = intelligence));

        sharpWind = new SharpWind(handler);
        strengthSkills.add(new SkillIcon(skillL1X, skillL1Y-baseSkillIconSize-10, baseSkillIconSize, baseSkillIconSize,
                new Bitmap[]{Assets.sharpWindG, Assets.sharpWind}, sharpWind));
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
        System.out.println(selectedSkill);
        for(UIObject o: skillUIObjects)
            o.update();
        for(UIObject o: currentSkills)
            o.update();
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

    public void upgradeSkill(){
        if(selectedSkill == null || !selectedSkill.levelUpReqMeet() || handler.getPlayer().getPerkPoints() == 0)
            return;
        selectedSkill.levelUp();
        handler.getPlayer().changePerkPoints(-1);
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

    public void releaseDrag(Skill skill, int x, int y){
        SkillButton[] skillButtons = handler.getUIManager().getSkillButtons();
        for(int i = 0; i < 3; i++)
            if(new Rect(activeSkillSlot[i].getBounds()).contains(x, y)){
                for(int j = 0; j < 3; j++){
                    SkillButton sb = skillButtons[j];
                    if(sb.getSkill() != null && sb.getSkill().equals(skill)){
                        sb.removeSkill();
                        activeSkillSlot[j].removeSkill();
                    }
                }
                skillButtons[i].setSkill((ActiveSkill)skill);
                activeSkillSlot[i].setSkill(skill);
            }
    }

    private class SkillIcon extends DraggableUI {

        private Skill skill;
        private Bitmap[] skillImages;

        public SkillIcon(float x, float y, int width, int height, Bitmap[] image, Skill skill) {
            super(x, y, width, height, image[0]);
            this.skill = skill;
            skillImages = image;
        }

        @Override
        public void update() {
            if(skill.isActive())
                image = ImageEditor.scaleBitmapForced(skillImages[1], width, height);
            else
                image = ImageEditor.scaleBitmapForced(skillImages[0], width, height);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            if(event.getActionMasked() == MotionEvent.ACTION_DOWN ||
                    event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN)
                if (new Rect(bounds).contains((int) event.getX(pointerIndex), (int) event.getY(pointerIndex)))
                    selectedSkill = skill;
            if(!skill.isActive())
                return;
            super.onTouchEvent(event);
        }

        @Override
        protected void release(int x, int y) {
            releaseDrag(skill, x, y);
        }
    }

    private class SkillSlotIcon extends UIImageButton{

        private Skill skill;

        public SkillSlotIcon(Rect r) {
            super(r.left, r.top, r.width(), r.height(), Assets.NULL, () -> {});
            clicker = () -> selectedSkill = this.skill;
        }

        public void setSkill(Skill skill) {
            this.skill = skill;
            images[0] = ImageEditor.scaleBitmapForced(skill.getTexture(), 128);
            images[1] = ImageEditor.scaleBitmapForced(skill.getTexture(), 128);
        }

        public void removeSkill(){
            skill = null;
            images[0] = ImageEditor.scaleBitmapForced(Assets.NULL, width, height);
            images[1] = ImageEditor.scaleBitmapForced(Assets.NULL, width, height);
        }

        public Rect getBounds(){
            return bounds;
        }
    }

    public SimplePlayerSkill getStrength() {
        return strength;
    }

    public SimplePlayerSkill getEndurance() {
        return endurance;
    }

    public SimplePlayerSkill getAgility() {
        return agility;
    }

    public SimplePlayerSkill getKnowledge() {
        return knowledge;
    }

    public SimplePlayerSkill getIntelligence() {
        return intelligence;
    }
}
