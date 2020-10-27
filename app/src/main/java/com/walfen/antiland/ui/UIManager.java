package com.walfen.antiland.ui;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.buttons.OptionButtonA;
import com.walfen.antiland.ui.buttons.SkillButton;
import com.walfen.antiland.ui.buttons.TextImageButton;
import com.walfen.antiland.ui.conversation.ConversationBox;
import com.walfen.antiland.ui.joystick.Joystick;
import com.walfen.antiland.ui.keyIO.KeyIOManager;
import com.walfen.antiland.ui.overlay.Tutorial;
import com.walfen.antiland.untils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class UIManager implements TouchEventListener{

    private Handler handler;
    private ArrayList<UIObject> uiObjects;
    private CriticalGameUI cGUI;
    private boolean hide;
    private ConversationBox convBox;
    private PopUp popUp;
    private OptionPopUp optionPopUp;
    private Tutorial tutorial;
    private KeyIOManager keyIOManager;

    public UIManager(Handler handler){
        this.handler = handler;
        uiObjects = new ArrayList<>();
        hide = false;
        cGUI = new CriticalGameUI(handler);
        convBox = new ConversationBox(this);
        popUp = new PopUp(800, 600);
        optionPopUp = new OptionPopUp(800, 800);
        keyIOManager = new KeyIOManager(handler);
        tutorial = new Tutorial();
    }

    @Override
    public void update(){
        if(convBox.active)
            convBox.update();
        if(tutorial.isActive())
            tutorial.update();
        keyIOManager.update();
        cGUI.update();
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
        cGUI.draw(canvas);
        for(UIObject o: uiObjects)
            o.draw(canvas);
    }

    public void postDraw(Canvas canvas){
        if(popUp.active)
            popUp.draw(canvas);
        if(optionPopUp.active)
            optionPopUp.draw(canvas);
        if(tutorial.isActive())
            tutorial.draw(canvas);
    }

    @Override
    public void onTouchEvent(MotionEvent event){
//        System.out.println(event.getX()+" "+event.getY());
        if(popUp.active){
            popUp.onTouchEvent(event);
            return;
        }
        if(optionPopUp.active){
            optionPopUp.onTouchEvent(event);
            return;
        }
        if(convBox.active)
            convBox.onTouchEvent(event);
        if(tutorial.isActive())
            if(!tutorial.onTouchEvent(event))
                return;
        if(hide)
            return;
        keyIOManager.onTouchEvent(event);
        cGUI.onTouchEvent(event);
        for(UIObject o: uiObjects)
            o.onTouchEvent(event);
        try {
            handler.getPlayer().onTouchEvent(event);
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

    public void hideUI(){
        hide = true;
        cGUI.onTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
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

    public void popUpOptions(String message, String[] options, ClickListener[] effects, boolean raw){
        optionPopUp.activatePopup(message, options, effects, raw);
    }

    public void popUpAction(String message, String buttonText, ClickListener action){
        optionPopUp.activatePopup(message, new String[]{buttonText}, new ClickListener[]{action}, false);
    }

    public void activeTutorial(String message, Rect target){
        popUpAction(message, "OK", () -> {tutorial.setTarget(target); tutorial.setActive(true);});
    }

    public void activeTutorial(String message, Rect target, ChangeEvent action){
        popUpAction(message, "OK", () -> {tutorial.setTarget(target); tutorial.setActive(true, action);});
    }

    private static class PopUp extends UIObject{

        private String message;
        private ClickListener effect;
        private TextImageButton proceedButton, cancelButton, okButton;
        private Bitmap image;
        private boolean buttonJustPressed = false;

        private boolean hasEffect;

        private PopUp(int width, int height) {
            super(Constants.SCREEN_WIDTH/2.f-width/2.f, Constants.SCREEN_HEIGHT/2.f-height/2.f
                    , width, height);
            active = false;
            hasEffect = true;
            cancelButton = new TextImageButton(x+128, y+height-96, 33, "No", Color.BLACK, Assets.popupButton2,  this::removePopup);
            proceedButton = new TextImageButton(x+width-136, y+height-96, 33, "Yes", Color.BLACK, Assets.popupButton2, this::proceed);
            okButton = new TextImageButton(x+width/2.f, y+height-96,33,  "Ok", Color.BLACK, Assets.popupButton2,  this::removePopup);
            image = ImageEditor.scaleBitmapForced(Assets.popup2, width, height);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            if(!active)
                return;
            if(buttonJustPressed){
                buttonJustPressed = false;
                return;
            }
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
            canvas.drawBitmap(image, null, new Rect(bounds), Constants.getRenderPaint());
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
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
            buttonJustPressed = true;
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

    private static class OptionPopUp extends UIObject{

        private String message;
        private Bitmap image;
        private ArrayList<TextImageButton> buttons;
        private int yLocation;
        private boolean buttonJustPressed = false;

        private OptionPopUp(int width, int height) {
            super(Constants.SCREEN_WIDTH/2.f-width/2.f, Constants.SCREEN_HEIGHT/2.f-height/2.f
                    , width, height);
            active = false;
            image = ImageEditor.scaleBitmapForced(Assets.popup1, width, height);
            buttons = new ArrayList<>();
            yLocation = 96;
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            if(!active)
                return;
            if(buttonJustPressed){
                buttonJustPressed = false;
                return;
            }
            for(TextImageButton button: buttons)
                button.onTouchEvent(event);
        }

        @Override
        public void update() {}

        @Override
        public void draw(Canvas canvas) {
            canvas.drawBitmap(image, null, new Rect(bounds), Constants.getRenderPaint());
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            Rect r = new Rect();
            paint.setTextSize(40);
            ArrayList<String> text = Utils.splitString(message, 30);
            paint.getTextBounds(text.get(0), 0, text.get(0).length(), r);
            int h = r.height();
            for(int i = 0; i < text.size(); i++){
                paint.getTextBounds(text.get(i), 0, text.get(i).length(), r);
                canvas.drawText(text.get(i), Constants.SCREEN_WIDTH/2.f-r.width()/2.f, y+130+(h+3)*i, paint);
            }
            for(TextImageButton button: buttons)
                button.draw(canvas);
        }

        private void removePopup(){
            active = false;
            buttons = new ArrayList<>();
            yLocation = 96;
            buttonJustPressed = true;
        }

        private void activatePopup(String message, String[] options, ClickListener[] effects, boolean raw){
            active = true;
            this.message = message;
            if(raw){
                OptionButtonA oba = new OptionButtonA(x+width/2.f+1, y+height-yLocation, 33,
                        "Back", Color.BLACK, Assets.popupButton1, this::removePopup, 690);
                buttons.add(oba);
                yLocation += oba.getBounds().height()+10;
            }
            for(int i = 0; i < options.length; i++){
                int finalI = i;
                OptionButtonA oba = new OptionButtonA(x+width/2.f+1, y+height-yLocation, 33,
                        options[i], Color.BLACK, Assets.popupButton1, () -> {removePopup(); effects[finalI].onClick();}, 690);
                buttons.add(oba);
                yLocation += oba.getBounds().height()+10;
            }
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

    public CriticalGameUI getCGUI() {
        return cGUI;
    }

    public ConversationBox getConvBox() {
        return convBox;
    }

    public KeyIOManager getKeyIOManager() {
        return keyIOManager;
    }
}
