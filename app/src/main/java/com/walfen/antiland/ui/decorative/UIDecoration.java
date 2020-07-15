package com.walfen.antiland.ui.decorative;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.UIObject;

public class UIDecoration extends UIObject {
    private final Bitmap texture;

    public UIDecoration(float x, float y, int width, int height, Bitmap texture) {
        super(x, y, width, height);
        this.texture = ImageEditor.scaleBitmapForced(texture, width, height);
    }

    @Override
    public void onTouchEvent(MotionEvent event) { }

    @Override
    public void update() { }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(texture, null, new Rect((int)x, (int)y, (int)(x+width), (int)(y+height)), Constants.getRenderPaint());
    }
}
