package com.walfen.antiland.ui;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.ui.buttons.TextButton;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.ui.conversation.ConversationBox;
import com.walfen.antiland.ui.joystick.Joystick;
import com.walfen.antiland.ui.keyIO.KeyIOManager;
import com.walfen.antiland.untils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class UIManager implements TouchEventListener{

    private Handler handler;
    private ArrayList<UIObject> uiObjects;
    private Joystick movement, attack;
    private boolean hide;
    private ConversationBox convBox;
    private PopUp popUp;
    private KeyIOManager keyIOManager;

    public UIManager(Handler handler){
        this.handler = handler;
        uiObjects = new ArrayList<>();
        hide = false;
        convBox = new ConversationBox(this);
        popUp = new PopUp(800, 400);
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
        if(popUp.active)
            popUp.draw(canvas);
    }

    @Override
    public void onTouchEvent(MotionEvent event){
//        System.out.println(event.getX()+" "+event.getY());
        if(popUp.active){
            popUp.onTouchEvent(event);
            return;
        }
        if(convBox.active)
            convBox.onTouchEvent(event);
        if(hide)
            return;
        keyIOManager.onTouchEvent(event);
        for(UIObject o: uiObjects)
            o.onTouchEvent(event);
        try {
            handler.getPlayer().getInventory().onTouchEvent(event);
            handler.getPlayer().getFabricator().onTouchEvent(event);
            handler.getPlayer().getMissionManager().onTouchEvent(event);
            handler.getPlayer().getInteractButton().onTouchEvent(event);
        }catch (ClassCastException | NullPointerException ignored){}
    }

    public void addUIObject(UIObject o){
        uiObjects.add(o);
    }

    public void addUIObject(UIObject[] objects){
        uiObjects.addAll(Arrays.asList(objects));
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
        movement.onTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
    }

    public void showUI(){
        hide = false;
    }

    public void popUpMessage(String message, ClickListener effect){
        popUp.activatePopup(message, effect);
    }

    public void popUpMessage(String message){
        popUp.activatePopup(message);
    }

    private static class PopUp extends UIObject{

        private String message;
        private ClickListener effect;
        private TextButton proceedButton, cancelButton, okButton;

        private boolean hasEffect;

        private PopUp(int width, int height) {
            super(Constants.SCREEN_WIDTH/2.f-width/2.f, Constants.SCREEN_HEIGHT/2.f-height/2.f
                    , width, height);
            active = false;
            hasEffect = true;
            proceedButton = new TextButton(x+64, y+height-48, 33, "Yes", this::proceed);
            cancelButton = new TextButton(x+width-64, y+height-48, 33, "No", this::removePopup);
            okButton = new TextButton(x+width/2.f, y+height-48,33,  "Ok", this::removePopup);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            if(!active)
                return;
            if(hasEffect) {
                proceedButton.onTouchEvent(event);
                cancelButton.onTouchEvent(event);
            }else {
                okButton.onTouchEvent(event);
            }
        }

        @Override
        public void update() {}

        @Override
        public void draw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setColor(Color.MAGENTA);
            canvas.drawRect(new Rect(bounds), paint); //TODO: make this an image
            paint.setColor(Color.WHITE);
            Rect r = new Rect();
            paint.setTextSize(40);
            ArrayList<String> text = Utils.splitString(message, 30);
            paint.getTextBounds(text.get(0), 0, text.get(0).length(), r);
            int h = r.height();
            for(int i = 0; i < text.size(); i++){
                paint.getTextBounds(text.get(i), 0, text.get(i).length(), r);
                canvas.drawText(text.get(i), Constants.SCREEN_WIDTH/2.f-r.width()/2.f, y+200+(h+3)*i, paint);
            }
            if(hasEffect) {
                proceedButton.draw(canvas);
                cancelButton.draw(canvas);
            }else {
                okButton.draw(canvas);
            }
        }

        private void proceed(){
            removePopup();
            effect.onClick();
        }

        private void removePopup(){
            active = false;
        }

        private void activatePopup(String message, ClickListener effect){
            active = true;
            hasEffect = true;
            this.message = message;
            this.effect = effect;
        }

        private void activatePopup(String message){
            active = true;
            hasEffect = false;
            this.message = message;
            effect = () -> {};
        }
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
