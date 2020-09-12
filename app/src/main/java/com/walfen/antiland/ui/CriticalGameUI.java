package com.walfen.antiland.ui;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.ui.buttons.SkillButton;
import com.walfen.antiland.ui.joystick.Joystick;
import com.walfen.antiland.ui.overlay.EnemyInfoPanel;
import com.walfen.antiland.ui.overlay.MissionPanel;

import java.util.ArrayList;
import java.util.Arrays;

public class CriticalGameUI implements TouchEventListener{

    private Handler handler;
    private ArrayList<UIObject> criticalUIs;
    private Joystick movement, attack;
    private SkillButton[] skillButtons;
    private EnemyInfoPanel enemyInfoPanel;
    private MissionPanel missionPanel;

    public CriticalGameUI(Handler handler){
        this.handler = handler;
        criticalUIs = new ArrayList<>();
    }

    public void initCriticalGameUI(Player player){
        createJoystick();
        createSkillButtons();
        enemyInfoPanel = new EnemyInfoPanel(player.getTracker());
        missionPanel = new MissionPanel(player.getMissionManager());
        addUIObject(enemyInfoPanel);
        addUIObject(missionPanel);
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        for(UIObject o: criticalUIs)
            o.onTouchEvent(event);
    }

    @Override
    public void update() {
        for(UIObject o: criticalUIs)
            o.update();
    }

    @Override
    public void draw(Canvas canvas) {
        for(UIObject o: criticalUIs)
            o.draw(canvas);
    }

    private void createJoystick(){
        movement = new Joystick(handler, 128, Constants.SCREEN_HEIGHT-300-128, 150);
        attack = new Joystick(handler, Constants.SCREEN_WIDTH-300-128, Constants.SCREEN_HEIGHT-300-128, 150);
        attack.setDeadZone(0.3f);
        addUIObject(movement);
        addUIObject(attack);
    }

    public void createSkillButtons(){
        skillButtons = new SkillButton[3];
        skillButtons[0] = new SkillButton(Constants.SCREEN_WIDTH-300-64, Constants.SCREEN_HEIGHT-568, 128);
        skillButtons[1] = new SkillButton(Constants.SCREEN_WIDTH-300-256, Constants.SCREEN_HEIGHT-440, 128);
        skillButtons[2] = new SkillButton(Constants.SCREEN_WIDTH-300-256, Constants.SCREEN_HEIGHT-216, 128);
        addUIObject(skillButtons);
    }

    public void addUIObject(UIObject o){
        criticalUIs.add(o);
    }

    public void addUIObject(UIObject[] objects){
        criticalUIs.addAll(Arrays.asList(objects));
    }

    public void removeUIObject(UIObject o){
        criticalUIs.remove(o);
    }

    public Joystick getMovementJoystick() {
        return movement;
    }

    public Joystick getAttackJoystick() {
        return attack;
    }

    public SkillButton[] getSkillButtons() {
        return skillButtons;
    }

    public EnemyInfoPanel getEnemyInfoPanel() {
        return enemyInfoPanel;
    }

    public MissionPanel getMissionPanel() {
        return missionPanel;
    }
}
