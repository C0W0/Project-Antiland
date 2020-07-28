package com.walfen.antiland.statswindow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.TouchEventListener;

public class SkillSelection implements TouchEventListener {

    private Handler handler;

    private boolean active = false, buttonJustPressed = false;
    private int skillHeight, skillWidth;
    private int xDispute, yDispute;
    private final Bitmap skillScreen;

    public SkillSelection(Handler handler){
        this.handler = handler;
        skillScreen = ImageEditor.scaleBitmap(Assets.skillScreen, Constants.UI_SCREEN_WIDTH, Constants.UI_SCREEN_HEIGHT);
        skillHeight = skillScreen.getHeight();
        skillWidth = skillScreen.getWidth();
    }
    @Override
    public void onTouchEvent(MotionEvent event) {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }
}
