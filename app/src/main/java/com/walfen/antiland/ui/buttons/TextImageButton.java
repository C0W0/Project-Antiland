package com.walfen.antiland.ui.buttons;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.ClickListener;

public class TextImageButton extends TextButton {

    protected Bitmap image;

    public TextImageButton(float centreX, float centreY, int textSize, String text, Bitmap image, ClickListener clicker) {
        super(centreX, centreY, textSize, text, clicker);
        this.image = ImageEditor.scaleBitmapForced(image, bounds.width(), bounds.height());
    }

    public TextImageButton(float centreX, float centreY, int textSize, String text, int colour, Bitmap image, ClickListener clicker) {
        super(centreX, centreY, textSize, text, colour, clicker);
        this.image = ImageEditor.scaleBitmapForced(image, bounds.width(), bounds.height());
    }

    @Override
    public void draw(Canvas canvas) {
        if(!active)
            return;
        int left = (int) (x-(bounds.width()-width)/2);
        int top = (int) (y-(bounds.height()-height)/2);
        canvas.drawBitmap(image, null, new Rect(left, top, left+bounds.width(), top+bounds.height()), Constants.getRenderPaint());
        super.draw(canvas);
    }
}
