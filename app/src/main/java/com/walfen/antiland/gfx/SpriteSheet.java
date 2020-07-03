package com.walfen.antiland.gfx;

import android.graphics.Bitmap;

public class SpriteSheet {

    private Bitmap bitmap;

    public SpriteSheet(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap crop(int x, int y, int width, int height){
        return Bitmap.createBitmap(bitmap, x, y, width, height);
    }

}
