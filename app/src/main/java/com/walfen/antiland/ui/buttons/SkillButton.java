package com.walfen.antiland.ui.buttons;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;

public class SkillButton extends UIImageButton{

    private ActiveSkill skill;
    private Bitmap cover;

    public SkillButton(float x, float y, int sideLength) {
        super(x, y, sideLength, sideLength, new Bitmap[]{Assets.joystick_pad, Assets.joystick_pad}, () -> {});
        cover = ImageEditor.scaleBitmapForced(Assets.joystick_pad, sideLength); //TODO: something different
    }

    public void setSkill(ActiveSkill skill){
        this.skill = skill;
        images = new Bitmap[]{skill.getIcon(), skill.getIcon()};
        clicker = skill::triggerTest;
    }

    @Override
    public void update() {
        super.update();
        if(skill != null)
            skill.update();
    }

    @Override
    public void draw(Canvas canvas) {
        if(!active){
            return;
        }
        canvas.drawBitmap(images[index], null, new Rect((int)x, (int)y, (int)x+width, (int)y+height), Constants.getRenderPaint());
        if(skill == null || skill.getCooldownSecond() == 0)
            return;
        canvas.drawBitmap(cover, null, new Rect((int)x, (int)y, (int)x+width, (int)y+height), Constants.getRenderPaint());
        Rect r = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(45);
        String sec = Integer.toString((int)(skill.getCooldownSecond()+0.5));
        paint.getTextBounds(sec, 0, sec.length(), r);
        canvas.drawText(sec, x+width/2.f-r.width()/2.f, y+height/2.f+r.height()/2.f, paint);
    }

    public ActiveSkill getSkill() {
        return skill;
    }

    public void removeSkill(){
        skill = null;
        images = new Bitmap[]{Assets.joystick_pad, Assets.joystick_pad};
        clicker = () -> {};
    }
}