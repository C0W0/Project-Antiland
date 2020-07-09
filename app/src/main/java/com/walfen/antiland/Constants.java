package com.walfen.antiland;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Paint;

import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;

public class Constants {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static int UI_SCREEN_WIDTH;
    public static int UI_SCREEN_HEIGHT;

    public static int iconSize;
    public static Resources RES;

    public static final int DEFAULT_SIZE = 128;

    public static AssetManager WORLD_FILES;

    public static String DIR;


    public static Paint getRenderPaint(){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        return paint;
    }

    public static void init(){
        UI_SCREEN_WIDTH = SCREEN_WIDTH - 200;
        UI_SCREEN_HEIGHT = SCREEN_HEIGHT - 100;
        iconSize = (int)(32.f/512* ImageEditor.scaleBitmap(Assets.craftingScreen,
                UI_SCREEN_WIDTH, UI_SCREEN_HEIGHT).getWidth());
    }

}
