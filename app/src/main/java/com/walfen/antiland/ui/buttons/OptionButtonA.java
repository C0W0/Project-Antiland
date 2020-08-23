package com.walfen.antiland.ui.buttons;

import android.graphics.Bitmap;

import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.ClickListener;

public class OptionButtonA extends TextImageButton {
    public OptionButtonA(float centreX, float centreY, int textSize, String text, Bitmap image, ClickListener clicker, int width) {
        super(centreX, centreY, textSize, text, image, clicker);
        bounds.left = (int)(centreX - width/2);
        bounds.right = (int)(centreX + width/2);
        this.image = ImageEditor.scaleBitmapForced(image, bounds.width(), bounds.height());
    }

    public OptionButtonA(float centreX, float centreY, int textSize, String text, int colour, Bitmap image, ClickListener clicker, int width) {
        super(centreX, centreY, textSize, text, colour, image, clicker);
        bounds.left = (int)(centreX - width/2);
        bounds.right = (int)(centreX + width/2);
        this.image = ImageEditor.scaleBitmapForced(image, bounds.width(), bounds.height());
    }
}
