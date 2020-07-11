package com.walfen.antiland.states;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.ui.keyIO.SimpleInputField;

public class MenuState extends State {

    private UIManager uiManager;

    public MenuState(Handler handler){
        super(handler);
        init();
    }

    @Override
    public void update(){
        uiManager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT), paint); // placeholder for background
        paint.setColor(Color.WHITE);
        canvas.drawRect(new Rect(Constants.SCREEN_WIDTH/2-300, Constants.SCREEN_HEIGHT/2-400,
                Constants.SCREEN_WIDTH/2+300, Constants.SCREEN_HEIGHT/2+400), paint); // placeholder for loading
        uiManager.draw(canvas);
    }

    @Override
    public void init() {
        uiManager = new UIManager(handler);
        uiManager.addUIObject(new UIImageButton(64, 64, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, this::switchState));
        uiManager.popUpMessage("This will wipe out all existing save files, " +
                        "are you sure you want to continue?", () -> System.out.println("info delivered"));
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(uiManager != null)
            uiManager.onTouchEvent(event);
    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {
        if(uiManager != null)
            uiManager.getKeyIOManager().onKeyDown(keyCode, event);
    }

    @Override
    public void onKeyLongPress(int keyCode, KeyEvent event) {
        if(uiManager != null)
            uiManager.getKeyIOManager().onKeyLongPress(keyCode, event);
    }

    private void switchState(){
        State.setState(handler.getGame().getGameState());
        State.getCurrentState().init();
    }

}
