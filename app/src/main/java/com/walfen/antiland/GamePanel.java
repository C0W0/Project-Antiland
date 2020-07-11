package com.walfen.antiland;

import android.content.Context;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.GameCamera;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.states.GameState;
import com.walfen.antiland.states.MenuState;
import com.walfen.antiland.states.State;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback, KeyEvent.Callback{

    private MainThread thread;
    private Handler handler;
    private GameCamera gameCamera;
    private State gameState, menuState;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        init();
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    private void init(){
        Assets.init();
        Constants.init();
        handler = new Handler(this);
        gameCamera = new GameCamera(handler, 0, 0);
        gameState = new GameState(handler);
        menuState = new MenuState(handler);
        File main = new File(Constants.DIR+"/main");
        File auto = new File(Constants.DIR+"/auto");
        if(!main.exists())
            main.mkdirs();
        if(!auto.exists())
            auto.mkdirs();

        State.setState(menuState);
//        gameState.init();
        Item.initItems(handler);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry){
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        State.getCurrentState().onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        State.getCurrentState().onKeyDown(keyCode, event);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        State.getCurrentState().onKeyLongPress(keyCode, event);
        return super.onKeyLongPress(keyCode, event);
    }

    public void update(){
        State.getCurrentState().update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        State.getCurrentState().draw(canvas);
    }

    //getters and setters

    public GameCamera getGameCamera() {
        return gameCamera;
    }

    public State getGameState() {
        return gameState;
    }
}
