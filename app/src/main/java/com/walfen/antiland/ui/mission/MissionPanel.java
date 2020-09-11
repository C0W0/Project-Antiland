package com.walfen.antiland.ui.mission;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.mission.MissionManager;
import com.walfen.antiland.ui.buttons.UIImageButton;

public class MissionPanel extends UIImageButton {

    private MissionManager missionManager;
    private Rect rcBox;
    private Bitmap extend, collapse;
    private boolean collapsed = true, motionInProgress = false;
    private int xChange;

    public MissionPanel(MissionManager missionManager) {

        //extend: x = Constants.SCREEN_WIDTH-556
        //collapse: x = Constants.SCREEN_WIDTH
        super(Constants.SCREEN_WIDTH, 288, 556, 160, Assets.NULL, () -> {});
        images[0] = ImageEditor.scaleBitmapForced(Assets.grey_transparent, width, height);
        images[1] = images[0];
        this.missionManager = missionManager;
        extend = ImageEditor.scaleBitmapForced(Assets.extend, 128, 160);
        collapse = ImageEditor.scaleBitmapForced(Assets.collapse, 128, 160);
        rcBox = new Rect(Constants.SCREEN_WIDTH-128, (int)y, Constants.SCREEN_WIDTH, (int)y+160);
        xChange = 0;
    }

    @Override
    public void update() {
        super.update();
        if(xChange != 0){
            if(!collapsed){
                if(x - Constants.SCREEN_WIDTH + 556 > -xChange){
                    move(xChange, 0);
                } else {
                    move((int)(Constants.SCREEN_WIDTH-556-x), 0);
                    xChange = 0;
                    motionInProgress = false;
                }
            }else {
                if(Constants.SCREEN_WIDTH - x > xChange){
                    move(xChange, 0);
                }else {
                    move((int)(Constants.SCREEN_WIDTH-x), 0);
                    xChange = 0;
                    motionInProgress = false;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(collapsed)
            canvas.drawBitmap(extend, null, new Rect((int)x-128, (int)y, (int)x, (int)y+160), Constants.getRenderPaint());
        else
            canvas.drawBitmap(collapse, null, new Rect((int)x-128, (int)y, (int)x, (int)y+160), Constants.getRenderPaint());
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(motionInProgress)
            return;
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN ||
                event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            float touchX = event.getX(pointerIndex);
            float touchY = event.getY(pointerIndex);
            if(rcBox.contains((int)touchX, (int)touchY))
                changePosition(collapsed?-25:25);
        }
        super.onTouchEvent(event);
    }

    public void changePosition(int speedDelta){
        collapsed = !collapsed;
        motionInProgress = true;
        xChange = speedDelta;
    }

    private void move(int dx, int dy){
        x += dx;
        y += dy;
        bounds.offset(dx, dy);
        rcBox.offset(dx, dy);
    }
}
