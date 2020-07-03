package com.walfen.antiland.states;


import com.walfen.antiland.Handler;
import com.walfen.antiland.ui.TouchEventListener;
import com.walfen.antiland.ui.UIManager;

public abstract class State implements TouchEventListener {

    private static State currentState = null;

    //States

    protected Handler handler;
    protected UIManager uiManager;

    public State(Handler handler){
        this.handler = handler;
    }

    public abstract void init();

    //getters and setters

    public static void setState(State state){
        currentState = state;
    }

    public static State getCurrentState(){
        return currentState;
    }

    public UIManager getUiManager() {
        return uiManager;
    }
}
