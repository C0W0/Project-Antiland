package com.walfen.antiland.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;

public class GlobalMap extends Map {

    public GlobalMap(Handler handler) {
        super(handler, Assets.worldMap_0);
    }

    @Override
    protected void postTouchEvent(MotionEvent event) {

    }

    @Override
    public void update() {

    }

    @Override
    protected void postDraw(Canvas canvas) {

    }
}
