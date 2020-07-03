package com.walfen.antiland.gfx;

import android.graphics.Bitmap;

public class ImageEditor {

    public static Bitmap scaleBitmap(Bitmap original, float size){
        float ratio = Math.min(size/original.getWidth(),
                size/original.getHeight());
        return Bitmap.createScaledBitmap(original, (int)(ratio*original.getWidth()),
                (int)(ratio*original.getHeight()), true);
    }

    public static Bitmap scaleBitmap(Bitmap original, float xSize, float ySize){
        float ratio = Math.min(xSize/original.getWidth(),
                ySize/original.getHeight());
        return Bitmap.createScaledBitmap(original, (int)(ratio*original.getWidth()),
                (int)(ratio*original.getHeight()), true);
    }



}
