package com.walfen.antiland.states;


import com.walfen.antiland.Handler;
import com.walfen.antiland.ui.TouchEventListener;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.keyIO.KeyEventListener;

public abstract class State implements TouchEventListener, KeyEventListener {

    private static State currentState = null;

    //States

    protected Handler handler;
    protected UIManager uiManager;

    public State(Handler handler){
        this.handler = handler;
    }

    //getters and setters

    public static void setState(State state){
        currentState = state;
    }

    public static void setStateAndInit(GameState state, String initPath){
        state.init(initPath);
        currentState = state;
    }

    public static State getCurrentState(){
        return currentState;
    }

    public UIManager getUiManager() {
        return uiManager;
    }
}
