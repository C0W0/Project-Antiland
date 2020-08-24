package com.walfen.antiland.ui.decorative;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.R;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.gfx.ImageLoader;
import com.walfen.antiland.mission.killing.KillTracker;
import com.walfen.antiland.ui.UIObject;
import com.walfen.antiland.ui.bars.BarA;

public class EnemyInfoPanel extends UIObject {

    private KillTracker tracker;
    private Bitmap image, currEntityTexture;
    private int currEntityID;
    private String currEntityName;
    private BarA entityHP;

    public EnemyInfoPanel(KillTracker tracker) {
        super(Constants.SCREEN_WIDTH/2.f-150, 32, 800, 192);
        this.tracker = tracker;
        image = ImageLoader.loadImage(R.drawable.enemy_panel);
        entityHP = new BarA(x+496-250, y+96, 500, 64, Assets.hp_bar, () -> 1, () -> 1); // numbers are placeholder
    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }

    @Override
    public void update() {
        active = tracker.getTopEntity() != null && tracker.getTopEntity().getHealth() > 0;
        if(!active)
            return;
        if(currEntityID != tracker.getTopEntity().getId()){
            currEntityID = tracker.getTopEntity().getId();
            currEntityTexture = tracker.getTopEntity().getTexture(128, 128);
            currEntityName = tracker.getTopEntity().getName();
        }
        entityHP.setTotalValue(tracker.getTopEntity().getMaxHp());
        entityHP.setCurrentValue(tracker.getTopEntity().getHealth());
    }

    @Override
    public void draw(Canvas canvas) {
        if(!active)
            return;
        canvas.drawBitmap(image, null, new Rect((int)x, (int)y, (int)x+width, (int)y+height), Constants.getRenderPaint());
        canvas.drawBitmap(currEntityTexture, null, new Rect((int)x+32, (int)y+32, (int)x+32+128, (int)y+32+128), Constants.getRenderPaint());
        Rect r = new Rect();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(35);
        paint.setFakeBoldText(true);
        paint.getTextBounds(currEntityName, 0, currEntityName.length(), r);
        canvas.drawText(currEntityName, x+496-r.width()/2.f, y+48+r.height()/2.f, paint);
        entityHP.draw(canvas);
    }
}
