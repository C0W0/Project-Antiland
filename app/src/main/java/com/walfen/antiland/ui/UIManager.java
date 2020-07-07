package com.walfen.antiland.ui;


import android.graphics.Canvas;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.ui.conversation.ConversationBox;
import com.walfen.antiland.ui.joystick.Joystick;
import com.walfen.antiland.ui.keyIO.KeyIOManager;

import java.util.ArrayList;

public class UIManager implements TouchEventListener{

    private Handler handler;
    private ArrayList<UIObject> uiObjects;
    private Joystick movement;
    private Joystick attack;
    private boolean hide;
    private ConversationBox convBox;
    private KeyIOManager keyIOManager;

    public UIManager(Handler handler){
        this.handler = handler;
        uiObjects = new ArrayList<>();
        hide = false;
        convBox = new ConversationBox(this);
        keyIOManager = new KeyIOManager(handler);
    }

    @Override
    public void update(){
        if(convBox.active)
            convBox.update();
        if(hide)
            return;
        keyIOManager.update();
        for(UIObject o: uiObjects)
            o.update();
    }

    @Override
    public void draw(Canvas canvas){
        if(convBox.active)
            convBox.draw(canvas);
        if(hide)
            return;
        keyIOManager.draw(canvas);
        for(UIObject o: uiObjects)
            o.draw(canvas);
    }

    @Override
    public void onTouchEvent(MotionEvent event){
        if(convBox.active)
            convBox.onTouchEvent(event);
        if(hide)
            return;
        keyIOManager.onTouchEvent(event);
        for(UIObject o: uiObjects)
            o.onTouchEvent(event);
        handler.getWorld().getPlayer().getInventory().onTouchEvent(event);
        handler.getWorld().getPlayer().getFabricator().onTouchEvent(event);
        handler.getWorld().getPlayer().getMissionManager().onTouchEvent(event);
    }

    public void addUIObject(UIObject o){
        uiObjects.add(o);
    }

    public void removeUIObject(UIObject o){
        uiObjects.remove(o);
    }

    public void createJoystick(){
        movement = new Joystick(128, Constants.SCREEN_HEIGHT-300-128, 150);
        attack = new Joystick(Constants.SCREEN_WIDTH-300-128, Constants.SCREEN_HEIGHT-300-128, 150);
        attack.setDeadZone(0.3f);
        addUIObject(movement);
        addUIObject(attack);
    }

    public void removeJoystick(){
        removeUIObject(movement);
        removeUIObject(attack);
    }

    public void hideUI(){
        hide = true;
    }

    public void showUI(){
        hide = false;
    }

    //getters and setters
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ArrayList<UIObject> getUiObjects() {
        return uiObjects;
    }

    public Joystick getMovementJoystick() {
        return movement;
    }

    public Joystick getAttackJoystick() {
        return attack;
    }

    public ConversationBox getConvBox() {
        return convBox;
    }

    public KeyIOManager getKeyIOManager() {
        return keyIOManager;
    }
}
