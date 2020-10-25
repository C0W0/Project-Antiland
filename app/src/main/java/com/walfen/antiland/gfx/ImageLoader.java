package com.walfen.antiland.gfx;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.walfen.antiland.Constants;

import java.lang.reflect.Field;

public class ImageLoader {

    public static Bitmap loadImage(int fileName){
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            Field f = options.getClass().getField("inScaled");
            f.set(options, Boolean.FALSE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return BitmapFactory.decodeResource(Constants.RES, fileName, options);
    }

    public static Bitmap loadSpriteSheet(int filename){
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            Field f = options.getClass().getField("inScaled");
            f.set(options, Boolean.FALSE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return BitmapFactory.decodeResource(Constants.RES, filename, options);
    }
}
