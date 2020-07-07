package com.walfen.antiland.ui.keyIO;


import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.ui.TouchEventListener;
import com.walfen.antiland.ui.UIObject;
import com.walfen.antiland.ui.conversation.ConversationBox;
import com.walfen.antiland.ui.joystick.Joystick;

import java.util.ArrayList;

public class KeyIOManager implements TouchEventListener, KeyEventListener{

    private Handler handler;
    private ArrayList<InputField> inputObjects;

    public KeyIOManager(Handler handler){
        this.handler = handler;
        inputObjects = new ArrayList<>();
    }

    @Override
    public void update(){
        for(InputField iField: inputObjects)
            iField.update();
    }

    @Override
    public void draw(Canvas canvas){
        for(InputField iField: inputObjects)
            iField.draw(canvas);
    }

    @Override
    public void onTouchEvent(MotionEvent event){
        for(InputField iField: inputObjects)
            iField.onTouchEvent(event);
    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {
        for(InputField iField: inputObjects)
            iField.onKeyDown(keyCode, event);
    }

    @Override
    public void onKeyLongPress(int keyCode, KeyEvent event) {
        for(InputField iField: inputObjects)
            iField.onKeyLongPress(keyCode, event);
    }

    public void addInputObject(InputField iField){
        inputObjects.add(iField);
    }

    public void removeInputObject(InputField iField){
        inputObjects.remove(iField);
    }

    //getters and setters
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ArrayList<InputField> getInputObjects() {
        return inputObjects;
    }

}
