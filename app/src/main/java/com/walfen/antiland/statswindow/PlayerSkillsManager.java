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
import com.walfen.antiland.entities.properties.skills.active.HeadBash;
import com.walfen.antiland.entities.properties.skills.active.RedStamp;
import com.walfen.antiland.entities.properties.skills.active.SharpWind;
import com.walfen.antiland.entities.properties.skills.active.TestShield;
import com.walfen.antiland.entities.properties.skills.passive.BigLeague;
import com.walfen.antiland.entities.properties.skills.passive.SimplePlayerSkill;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.TouchEventListener;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.UIObject;
import com.walfen.antiland.ui.buttons.SkillButton;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.ui.drag.DraggableUI;
import com.walfen.antiland.untils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayerSkillsManager implements TouchEventListener {

    private Handler handler;

    private boolean active = false, buttonJustPressed = false;
    private int skillHeight, skillWidth;
    private int xDispute, yDispute;
    private int pointsCentreX, pointsCentreY;
    private int selectedIconCentreX, selectedIconY;
    private int selectedIconSize;
    private final Bitmap skillScreen;
    private Bitmap selectedSkillTexture;

    private Skill selectedSkill;
    private ArrayList<UIObject> strengthSU, enduranceSU, agilitySU, knowledgeSU, intelligenceSU;

    private ArrayList<UIObject> skillUIObjects;
    private ArrayList<UIObject> currentSkills;

    private SkillSlotIcon[] activeSkillSlot;

    private UIImageButton switchButton, closeButton;

    //5 base:
    private SimplePlayerSkill strength, endurance, agility, knowledge, intelligence;
    //Skill lists
    private ArrayList<Skill> strengthSL, enduranceSL, agilitySL, knowledgeSL, intelligenceSL;

    public PlayerSkillsManager(Handler handler){

        this.handler = handler;

        skillScreen = ImageEditor.scaleBitmap(Assets.skillScreen, Constants.UI_SCREEN_WIDTH, Constants.UI_SCREEN_HEIGHT);
        skillHeight = skillScreen.getHeight();
        skillWidth = skillScreen.getWidth();
        xDispute = Constants.SCREEN_WIDTH/2 - skillWidth/2;
        yDispute = Constants.SCREEN_HEIGHT/2 - skillHeight/2;
        selectedIconCentreX = (int)(383.f/512*skillWidth+xDispute);
        selectedIconY = (int)(56.f/384*skillHeight+yDispute);
        skillUIObjects = new ArrayList<>();
        strengthSU = new ArrayList<>();
        enduranceSU = new ArrayList<>();
        agilitySU = new ArrayList<>();
        knowledgeSU = new ArrayList<>();
        intelligenceSU = new ArrayList<>();
        activeSkillSlot = new SkillSlotIcon[3];

        currentSkills = strengthSU;
        float baseSkillBX = 42.f/512*skillWidth+xDispute;
        float baseSkillBY = 43.f/384*skillHeight+yDispute;
        float baseSkillDX = 36.f/512*skillWidth;
        int skillIconSize = (int)(30.f/512*skillWidth);
        pointsCentreX = (int)(331.f/512*skillWidth+xDispute);
        pointsCentreY = (int)(316.f/384*skillHeight+yDispute);
        skillUIObjects.add(new UIImageButton(baseSkillBX, baseSkillBY, skillIconSize, skillIconSize,
                Assets.strength, () -> currentSkills = strengthSU));
        skillUIObjects.add(new UIImageButton(baseSkillBX+baseSkillDX, baseSkillBY, skillIconSize, skillIconSize,
                Assets.endurance, () -> currentSkills = enduranceSU));
        skillUIObjects.add(new UIImageButton(baseSkillBX+baseSkillDX*2, baseSkillBY, skillIconSize, skillIconSize,
                Assets.agility, () -> currentSkills = agilitySU));
        skillUIObjects.add(new UIImageButton(baseSkillBX+baseSkillDX*3, baseSkillBY, skillIconSize, skillIconSize,
                Assets.knowledge, () -> currentSkills = knowledgeSU));
        skillUIObjects.add(new UIImageButton(baseSkillBX+baseSkillDX*4, baseSkillBY, skillIconSize, skillIconSize,
                Assets.intelligence, () -> currentSkills = intelligenceSU));
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

        closeButton = new UIImageButton(xDispute + skillWidth - Constants.UI_CLOSE_SIZE, yDispute,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                Assets.close, () -> setActive(false));
        switchButton = new UIImageButton(xDispute, yDispute + skillHeight - Constants.UI_CLOSE_SIZE,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                Assets.switchFlip, this::setActive);

        skillIconSize *= 1.2;
        int skillL1X = (int)(128.f/512*skillWidth+xDispute-skillIconSize/2);
        int skillL1Y = (int) (283.f/384*skillHeight+yDispute-skillIconSize);
        strengthSL = new ArrayList<>();
        enduranceSL = new ArrayList<>();
        agilitySL = new ArrayList<>();
        knowledgeSL = new ArrayList<>();
        intelligenceSL = new ArrayList<>();

        strength = new SimplePlayerSkill(handler, 10,
                () -> {handler.getPlayer().changePhysicalDamage(strength.getLevel());
                handler.getPlayer().changeMaxHp(1);}, Assets.strengthR,
                "Strength", "Physical strength.", "Increases physical attack damage and hit points.");
        strengthSU.add(new SkillStaticIcon(skillL1X, skillL1Y, skillIconSize, skillIconSize, Assets.strengthR, strength));
        strengthSL.add(strength);

        endurance = new SimplePlayerSkill(handler, 10,
                () -> {handler.getPlayer().changeMaxHp(endurance.getLevel());
                handler.getPlayer().changeDefence(1);}, Assets.enduranceR,
                "Endurance", "Resistance to damage.", "Increases hit points and physical defence.");
        enduranceSU.add(new SkillStaticIcon(skillL1X, skillL1Y, skillIconSize, skillIconSize, Assets.enduranceR, endurance));
        enduranceSL.add(endurance);

        agility = new SimplePlayerSkill(handler, 10,
                () -> handler.getPlayer().changeSpeed((int)Math.floor(agility.getLevel()/2.f+0.5)), Assets.agilityR,
                "Agility", "Speed and agility.", "Increases speed and dodge.");
        agilitySU.add(new SkillStaticIcon(skillL1X, skillL1Y, skillIconSize, skillIconSize, Assets.agilityR, agility));
        agilitySL.add(agility);

        knowledge = new SimplePlayerSkill(handler, 10,
                () -> {handler.getPlayer().changeMaxMp(knowledge.getLevel());
                handler.getPlayer().changeMagicalDamage(1); }, Assets.knowledgeR,
                "Knowledge", "Knowledge of the world.", "Increases magic points and magical attack damage.");
        knowledgeSU.add(new SkillStaticIcon(skillL1X, skillL1Y, skillIconSize, skillIconSize, Assets.knowledgeR, knowledge));
        knowledgeSL.add(knowledge);

        intelligence = new SimplePlayerSkill(handler, 10,
                () -> handler.getPlayer().changeMagicalDamage((int)Math.floor(intelligence.getLevel()/2.f+0.5)), Assets.intelligenceR,
                "Intelligence", "Situational awareness and ability to learn.", "Increases magical attack damage");
        intelligenceSU.add(new SkillStaticIcon(skillL1X, skillL1Y, skillIconSize, skillIconSize, Assets.intelligenceR, intelligence));
        intelligenceSL.add(intelligence);

        selectedIconSize = skillIconSize;

        //strength skills:
        Skill sharpWind = new SharpWind(handler);
        strengthSU.add(new SkillIcon(skillL1X+20+skillIconSize, skillL1Y-skillIconSize-10, skillIconSize, skillIconSize,
                new Bitmap[]{Assets.strengthSkillsG[0], Assets.strengthSkills[0]}, sharpWind));
        strengthSL.add(sharpWind);
        Skill headBash = new HeadBash(handler);
        strengthSU.add(new SkillIcon(skillL1X-20-skillIconSize, skillL1Y-skillIconSize-10, skillIconSize, skillIconSize,
                new Bitmap[]{Assets.strengthSkillsG[1], Assets.strengthSkills[1]}, headBash));
        strengthSL.add(headBash);
        Skill bigLeague = new BigLeague(handler);
        strengthSU.add(new SkillIcon(skillL1X, skillL1Y-skillIconSize-10, skillIconSize, skillIconSize,
                new Bitmap[]{Assets.strengthSkillsG[2], Assets.strengthSkills[2]}, bigLeague));
        strengthSL.add(bigLeague);
        Skill redStamp = new RedStamp(handler);
        strengthSU.add(new SkillIcon(skillL1X-10-skillIconSize/2.f, skillL1Y-skillIconSize*2-20, skillIconSize, skillIconSize,
                new Bitmap[]{Assets.strengthSkillsG[3], Assets.strengthSkills[3]}, redStamp));
        strengthSL.add(redStamp);

        //endurance skills:

    }

    public void initSkills(String path, UIManager manager){
        //FILE IO:
        File skillFile = new File(path+"/player/skills.wld");
        ArrayList<String> tokens;
        try{
            tokens = Utils.loadFileAsArrayList(new FileInputStream(skillFile));
            String[] token = tokens.get(0).split("\\s+");
            for(int i = 0; i < token.length; i++)
                strengthSL.get(i).setLevel(Utils.parseInt(token[i]));
            token = tokens.get(1).split("\\s+");
            for(int i = 0; i < token.length; i++)
                enduranceSL.get(i).setLevel(Utils.parseInt(token[i]));
            token = tokens.get(2).split("\\s+");
            for(int i = 0; i < token.length; i++)
                agilitySL.get(i).setLevel(Utils.parseInt(token[i]));
            token = tokens.get(3).split("\\s+");
            for(int i = 0; i < token.length; i++)
                knowledgeSL.get(i).setLevel(Utils.parseInt(token[i]));
            token = tokens.get(4).split("\\s+");
            for(int i = 0; i < token.length; i++)
                intelligenceSL.get(i).setLevel(Utils.parseInt(token[i]));
            token = tokens.get(5).split("\\s+");
            SkillButton[] skillButtons = manager.getSkillButtons();
            ArrayList<Skill>[] lists = new ArrayList[]{strengthSL, enduranceSL, agilitySL, knowledgeSL, intelligenceSL};
            for(int i = 0; i < 3; i++){
                int hID = Utils.parseInt(token[i]);
                if(hID == -1)
                    continue;
                int arrID = (int)(hID/100);
                int arrIndex = hID%100;
                skillButtons[i].setSkill((ActiveSkill)lists[arrID].get(arrIndex));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(!active)
            return;
        if(buttonJustPressed){
            buttonJustPressed = false;
            return;
        }
        for(UIObject o: skillUIObjects)
            o.onTouchEvent(event);
        for(UIObject o: currentSkills)
            o.onTouchEvent(event);
        closeButton.onTouchEvent(event);
        switchButton.onTouchEvent(event);
    }

    @Override
    public void update() {
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
        String token = Integer.toString(handler.getPlayer().getPerkPoints());
        paint.getTextBounds(token, 0, token.length(), r);
        canvas.drawText(token, pointsCentreX-r.width()/2.f, pointsCentreY+r.height()/2.f, paint);

        closeButton.draw(canvas);
        switchButton.draw(canvas);

        if(selectedSkill == null)
            return;
        int left = selectedIconCentreX-selectedSkillTexture.getWidth()/2;
        int top = selectedIconY;
        canvas.drawBitmap(selectedSkillTexture, null,
                new Rect(left, top, left+selectedSkillTexture.getWidth(),
                        selectedIconY+selectedSkillTexture.getHeight()), Constants.getRenderPaint());
        paint.setTextSize(36);
        paint.setColor(Color.BLACK);
        token = selectedSkill.getTitle();
        paint.getTextBounds(token, 0, token.length(), r);
        left = selectedIconCentreX-r.width()/2;
        top += selectedSkillTexture.getHeight()+10;
        canvas.drawText(token, left, top+r.height(), paint);
        paint.setTextSize(32);
        ArrayList<String> tokens = Utils.splitString(selectedSkill.getDesc(), 20);
        top += r.height()+20;
        for(String str: tokens){
            paint.getTextBounds(str, 0, str.length(), r);
            left = selectedIconCentreX-r.width()/2;
            canvas.drawText(str, left, top+r.height(), paint);
            top += 5+r.height();
        }
        tokens = Utils.splitString(selectedSkill.getEffect(), 20);
        top += 10;
        paint.setColor(Color.MAGENTA);
        for(String str: tokens){
            paint.getTextBounds(str, 0, str.length(), r);
            left = selectedIconCentreX-r.width()/2;
            canvas.drawText(str, left, top+r.height(), paint);
            top += 5+r.height();
        }
        if(selectedSkill.levelUpReqMeet())
            return;
        tokens = Utils.splitString("Requirement missing: "+selectedSkill.getReq(), 20);
        top += 15;
        paint.setColor(Color.RED);
        for(String str: tokens){
            paint.getTextBounds(str, 0, str.length(), r);
            left = selectedIconCentreX-r.width()/2;
            canvas.drawText(str, left, top+r.height(), paint);
            top += 5+r.height();
        }
    }

    public void upgradeSkill(){
        if(selectedSkill == null || !selectedSkill.levelUpReqMeet() || handler.getPlayer().getPerkPoints() == 0)
            return;
        selectedSkill.levelUp();
        handler.getPlayer().changePerkPoints(-1);
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
            if(skill.isActive() || skill.levelUpReqMeet())
                image = ImageEditor.scaleBitmapForced(skillImages[1], width, height);
            else
                image = ImageEditor.scaleBitmapForced(skillImages[0], width, height);
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            Paint paint = new Paint();
            Rect r = new Rect();
            paint.setTextSize(22);
            paint.setColor(Color.BLACK);
            paint.setFakeBoldText(true);
            String level = "Lv. "+skill.getLevel();
            paint.getTextBounds(level, 0, level.length(), r);
            canvas.drawText(level, (float)(x+width-r.width()/1.5), y+height, paint);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            if(event.getActionMasked() == MotionEvent.ACTION_DOWN ||
                    event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN)
                if (new Rect(bounds).contains((int) event.getX(pointerIndex), (int) event.getY(pointerIndex))){
                    selectedSkill = skill;
                    selectedSkillTexture = skill.getTexture(width);
                }
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
            clicker = () -> {selectedSkill = this.skill; selectedSkillTexture = skill.getTexture(selectedIconSize);};
        }

        public void setSkill(Skill skill) {
            this.skill = skill;
            images[0] = skill.getTexture(128);
            images[1] = skill.getTexture(128);
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

    private class SkillStaticIcon extends UIImageButton{

        private Skill skill;
        private Bitmap[] skillImages;

        public SkillStaticIcon(float x, float y, int width, int height, Bitmap image, Skill skill) {
            super(x, y, width, height, image, () -> {selectedSkill = skill;
                    selectedSkillTexture = skill.getTexture(width);});
            this.skill = skill;
            skillImages = null;
        }

        public SkillStaticIcon(float x, float y, int width, int height, Bitmap[] images, Skill skill) {
            super(x, y, width, height, images[0], () -> {selectedSkill = skill;
                selectedSkillTexture = skill.getTexture(width);});
            this.skill = skill;
            skillImages = images;
        }

        @Override
        public void update() {
            if(skillImages == null)
                return;
            if(skill.isActive() || skill.levelUpReqMeet()){
                images[0] = ImageEditor.scaleBitmapForced(skillImages[1], width, height);
                images[1] = ImageEditor.scaleBitmapForced(skillImages[1], width, height);
            }else {
                images[0] = ImageEditor.scaleBitmapForced(skillImages[0], width, height);
                images[1] = ImageEditor.scaleBitmapForced(skillImages[0], width, height);
            }
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            Paint paint = new Paint();
            Rect r = new Rect();
            paint.setTextSize(22);
            paint.setColor(Color.BLACK);
            paint.setFakeBoldText(true);
            String level = "Lv. "+skill.getLevel();
            paint.getTextBounds(level, 0, level.length(), r);
            canvas.drawText(level, (float)(x+width-r.width()/1.5), y+height, paint);
        }
    }

    public void saveSkills(String path) throws IOException{
        File skillFile = new File(path+"/player/skills.wld");
        skillFile.delete();
        skillFile.createNewFile();
        PrintWriter editor = new PrintWriter(skillFile);
        for(Skill s: strengthSL)
            editor.print(s.getLevel()+" ");
        editor.println();
        for(Skill s: enduranceSL)
            editor.print(s.getLevel()+" ");
        editor.println();
        for(Skill s: agilitySL)
            editor.print(s.getLevel()+" ");
        editor.println();
        for(Skill s: knowledgeSL)
            editor.print(s.getLevel()+" ");
        editor.println();
        for(Skill s: intelligenceSL)
            editor.print(s.getLevel()+" ");
        editor.println();
        SkillButton[] skillButtons = handler.getUIManager().getSkillButtons();
        for(int i = 0; i < 3; i++){
            ActiveSkill s = skillButtons[i].getSkill();
            if(s != null)
                editor.print(s.getHierarchyID()+" ");
            else
                editor.print(-1+" ");
        }
        editor.close();
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

    public ArrayList<Skill> getStrengthSL() {
        return strengthSL;
    }

    public ArrayList<Skill> getEnduranceSL() {
        return enduranceSL;
    }

    public ArrayList<Skill> getAgilitySL() {
        return agilitySL;
    }

    public ArrayList<Skill> getKnowledgeSL() {
        return knowledgeSL;
    }

    public ArrayList<Skill> getIntelligenceSL() {
        return intelligenceSL;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(){
        active = !active;
        buttonJustPressed = true;
        handler.getPlayer().getStatsWindow().setActive(!active);
    }

    public void setActive(boolean active) {
        buttonJustPressed = true;
        this.active = active;
    }
}
