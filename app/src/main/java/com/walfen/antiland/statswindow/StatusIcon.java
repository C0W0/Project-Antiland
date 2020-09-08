package com.walfen.antiland.statswindow;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.entities.properties.effect.StatusEffect;
import com.walfen.antiland.ui.decorative.UIDecoration;

public class StatusIcon extends UIDecoration {

    private StatusEffect effect;

    public StatusIcon(float x, float y, StatusEffect effect) {
        super(x, y, 128, 128, effect.getTexture());
        this.effect = effect;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        String time = effect.getRemainingDuration();
        Rect r = new Rect();
        paint.setTextSize(30);
        paint.getTextBounds(time, 0, time.length(), r);
        canvas.drawText(time, x+width-r.width()-5, y+height-5, paint);
    }

    public StatusEffect getEffect() {
        return effect;
    }
}
