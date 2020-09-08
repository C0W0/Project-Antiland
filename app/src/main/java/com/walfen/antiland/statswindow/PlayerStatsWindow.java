package com.walfen.antiland.statswindow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.effect.StatusEffect;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.bars.BarB;
import com.walfen.antiland.ui.TouchEventListener;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.ui.decorative.UIDecoration;

import java.util.ArrayList;

public class PlayerStatsWindow implements TouchEventListener {

    private Handler handler;

    private boolean active = false, buttonJustPressed = false;
    private int statsHeight, statsWidth;
    private int xDispute, yDispute;
    private int levelTextX, levelNumX, levelY, hpTextX, hpTextY, mpTextX, mpTextY;
    private int attackTextX, attackTextY, defenceTextX, defenceTextY;
    private int physTextX, physNumX, magTextX, magNumX, attackY, defenceY;
    private BarB xpBar, hpBar, mpBar;

    private UIImageButton switchButton, closeButton;

    private int statusIconBaseX, statusIconBaseY, statusIconXCount;
    private ArrayList<StatusIcon> icons;

    private final Bitmap statsScreen;

    public PlayerStatsWindow(Handler handler){
        this.handler = handler;
        statsScreen = ImageEditor.scaleBitmap(Assets.statsScreen, Constants.UI_SCREEN_WIDTH, Constants.UI_SCREEN_HEIGHT);
        statsWidth = statsScreen.getWidth();
        statsHeight = statsScreen.getHeight();
        float xRatio = statsWidth/512.f;
        float yRatio = statsHeight/384.f;
        xDispute = Constants.SCREEN_WIDTH/2 - statsWidth/2;
        yDispute = Constants.SCREEN_HEIGHT/2 - statsHeight/2;
        levelTextX = (int)(50*xRatio+xDispute);
        levelY = (int)(115*yRatio+yDispute);
        levelNumX = (int)(87*xRatio+xDispute);
        hpTextX = (int)(68*xRatio+xDispute);
        hpTextY = (int)(159*yRatio+yDispute);
        mpTextX = (int)(64*xRatio+xDispute);
        mpTextY = (int)(203*yRatio+yDispute);
        attackTextX = (int)(71*xRatio+xDispute);
        attackTextY = (int)(245*yRatio+yDispute);
        defenceTextX = (int)(76*xRatio+xDispute);
        defenceTextY = (int)(309*yRatio+yDispute);
        physTextX = (int)(49*xRatio+xDispute);
        magTextX = (int)(141*xRatio+xDispute);
        attackY = (int)(268*yRatio+yDispute);
        defenceY = (int)(330*yRatio+yDispute);
        physNumX = (int)(92*xRatio+xDispute);
        magNumX = (int)(184*xRatio+xDispute);
        statusIconXCount = (int)(188*xRatio/70);
        statusIconBaseX = (int)(290*xRatio+xDispute);
        statusIconBaseY = (int)(109*yRatio+yDispute);
        int xpBarX, xpBarY, hpBarX, hpBarY, mpBarX, mpBarY;
        int xpBarWidth, hpBarWidth, mpBarWidth, barHeight;
        xpBarX = (int)(113*xRatio+xDispute);
        xpBarY = (int)(108*yRatio+yDispute);
        xpBarWidth = (int)(110*xRatio);
        hpBarX = (int)(113*xRatio+xDispute);
        hpBarY = (int)(152*yRatio+yDispute);
        hpBarWidth = (int)(73*xRatio);
        mpBarX = (int)(104*xRatio+xDispute);
        mpBarY = (int)(196*yRatio+yDispute);
        mpBarWidth = (int)(73*xRatio);
        barHeight = (int)(16*yRatio);
        xpBar = new BarB(xpBarX, xpBarY, xpBarWidth, barHeight, Assets.dark_blue_bar,
                () -> handler.getPlayer().getCurrLevelMaxXp(), () -> handler.getPlayer().getCurrLevelXp());
        hpBar = new BarB(hpBarX, hpBarY, hpBarWidth, barHeight, Assets.hp_bar,
                () -> handler.getPlayer().getMaxHp(), () -> handler.getPlayer().getHealth());
        mpBar = new BarB(mpBarX, mpBarY, mpBarWidth, barHeight, Assets.mp_bar,
                () -> handler.getPlayer().getMaxMp(), () -> handler.getPlayer().getMp());
        closeButton = new UIImageButton(xDispute + statsWidth - Constants.UI_CLOSE_SIZE, yDispute,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                Assets.close, () -> setActive(false));
        switchButton = new UIImageButton(xDispute, yDispute + statsHeight - Constants.UI_CLOSE_SIZE,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                Assets.switchFlip, () -> handler.getPlayer().getSkillsManager().setActive());
        icons = new ArrayList<>();
    }


    @Override
    public void onTouchEvent(MotionEvent event) {
        if(!active)
            return;
        if(buttonJustPressed){
            buttonJustPressed = false;
            return;
        }
        xpBar.onTouchEvent(event);
        hpBar.onTouchEvent(event);
        mpBar.onTouchEvent(event);
        closeButton.onTouchEvent(event);
        switchButton.onTouchEvent(event);
    }

    @Override
    public void update() {
        if(!active)
            return;
        for(int i = 0; i < icons.size(); i++){
            if(!icons.get(i).getEffect().isValid()){
                icons.remove(i);
                i --;
            }
        }
        xpBar.update();
        hpBar.update();
        mpBar.update();
    }

    @Override
    public void draw(Canvas canvas) {
        if(!active)
            return;
        canvas.drawBitmap(statsScreen, null, new Rect(xDispute, yDispute, xDispute+statsWidth, yDispute+statsHeight),
                Constants.getRenderPaint());

        //subtitles
        Paint paint = new Paint();
        paint.setFakeBoldText(true);
        paint.setTextSize(40);
        Rect r = new Rect();
        String text = "Lv.";
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, levelTextX-r.width()/2.f, levelY+r.height()/2.f, paint);
        text = "Health";
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, hpTextX-r.width()/2.f, hpTextY+r.height()/2.f, paint);
        text = "Mana";
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, mpTextX-r.width()/2.f, mpTextY+r.height()/2.f, paint);
        text = "Attacks";
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, attackTextX-r.width()/2.f, attackTextY+r.height()/2.f, paint);
        text = "Defences";
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, defenceTextX-r.width()/2.f, defenceTextY+r.height()/2.f, paint);

        //numbers and desc
        paint.setTextSize(34);
        paint.setFakeBoldText(false);
        text = "Phys.";
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, physTextX-r.width()/2.f, attackY+r.height()/2.f, paint);
        canvas.drawText(text, physTextX-r.width()/2.f, defenceY+r.height()/2.f, paint);
        text = "Mag.";
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, magTextX-r.width()/2.f, attackY+r.height()/2.f, paint);
        canvas.drawText(text, magTextX-r.width()/2.f, defenceY+r.height()/2.f, paint);

        text = Integer.toString(handler.getPlayer().getLevel());
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, levelNumX-r.width()/2.f, levelY+2+r.height()/2.f, paint);
        text = Integer.toString(handler.getPlayer().getPhysicalDamage());
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, physNumX-r.width()/2.f, attackY+2+r.height()/2.f, paint);
        text = Integer.toString(handler.getPlayer().getMagicalDamage());
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, magNumX-r.width()/2.f, attackY+2+r.height()/2.f, paint);
        text = Integer.toString(handler.getPlayer().getDefence());
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, physNumX-r.width()/2.f, defenceY+2+r.height()/2.f, paint);
        text = Integer.toString(handler.getPlayer().getMagicalDefence());
        paint.getTextBounds(text, 0, text.length(), r);
        canvas.drawText(text, magNumX-r.width()/2.f, defenceY+2+r.height()/2.f, paint);

        for(StatusIcon si: icons)
            si.draw(canvas);

        xpBar.draw(canvas);
        hpBar.draw(canvas);
        mpBar.draw(canvas);
        closeButton.draw(canvas);
        switchButton.draw(canvas);
    }

    public void setActive(){
        buttonJustPressed = true;
        active = !active;
    }

    public void setActive(boolean active) {
        buttonJustPressed = true;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void addStatusIcon(StatusEffect effect){
        StatusIcon icon = new StatusIcon((icons.size()%statusIconXCount)*70+statusIconBaseX, 70*(icons.size()/statusIconXCount)+statusIconBaseY, effect);
        icons.add(icon);
    }
}
