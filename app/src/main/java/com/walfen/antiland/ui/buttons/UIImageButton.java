package com.walfen.antiland.ui.buttons;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.widget.Switch;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.ClickListener;
import com.walfen.antiland.ui.UIObject;
import com.walfen.antiland.untils.Utils;

public class UIImageButton extends UIObject {

    protected Bitmap[] images;
    protected ClickListener clicker;
    protected int index = 0;

    public UIImageButton(float x, float y, int width, int height, Bitmap[] images, ClickListener clicker) {
        super(x, y, width, height);
        this.clicker = clicker;
        this.images = new Bitmap[2];
        this.images[0] = ImageEditor.scaleBitmapForced(images[0], width, height);
        this.images[1] = ImageEditor.scaleBitmapForced(images[1], width, height);
    }

    public UIImageButton(float x, float y, int width, int height, Bitmap image, ClickListener clicker) {
        super(x, y, width, height);
        this.clicker = clicker;
        this.images = new Bitmap[2];
        images[0] = ImageEditor.scaleBitmapForced(image, width, height);
        images[1] = ImageEditor.scaleBitmapForced(image, width, height);
    }

    @Override
    public void update() {
        if(!active){
            return;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(!active){
            return;
        }

        canvas.drawBitmap(images[index], null, new Rect((int)x, (int)y, (int)x+width, (int)y+height), Constants.getRenderPaint());
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(!active)
            return;
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN ||
                event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            for(int i = 0; i < event.getPointerCount(); i++)
                if (new Rect(bounds).contains((int) event.getX(i), (int) event.getY(i))) {
                    onClick();
                    index = 1;
                    return;
                }
        }
        index = 0;
    }

    public void onClick() {
        clicker.onClick();
    }
}
