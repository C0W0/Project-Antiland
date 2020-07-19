package com.walfen.antiland;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;

import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;

public class Constants {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static int UI_SCREEN_WIDTH;
    public static int UI_SCREEN_HEIGHT;
    public static int UI_CLOSE_SIZE;

    public static int iconSize;
    public static Resources RES;

    public static final String GAME_VERSION = "A1050";
    public static final String GAME_VERSION_DISPLAY = "Alpha - build 1050";

    public static final int DEFAULT_SIZE = 128;

    public static final int TEXT_COLOUR = Color.argb(255, 175, 238, 238);

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
        UI_CLOSE_SIZE = (int)(54.f/384*UI_SCREEN_HEIGHT);
        iconSize = (int)(32.f/512* ImageEditor.scaleBitmap(Assets.craftingScreen,
                UI_SCREEN_WIDTH, UI_SCREEN_HEIGHT).getWidth());
    }

}
